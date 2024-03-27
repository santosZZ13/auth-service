
####################CREATE A STAGE FOR RESOLVING AND DOWNLOADING DEPENDENCIES.##########################################
FROM eclipse-temurin:17-jdk-jammy as deps
WORKDIR /build

# Copy the mvnw wrapper with executable permissions.

COPY --chmod=0755 mvnw mvnw
COPY .mvn/ .mvn/

# Download dependencies as a separate step to take advantage of Docker's caching.
# Leverage a cache mount to /root/.m2 so that subsequent builds don't have to
# re-download packages.
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline -DskipTests


################### CREATE A STAGE FOR BUILDING THE APPLICATION BASED ON THE STAGE WITH DOWNLOADED DEPENDENCIES.########
FROM deps as package

WORKDIR /build

COPY ./src src/
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 \
    ./mvnw package -DskipTests && \
    mv target/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar

################## CREATE A STAGE FOR EXTRACTING THE APPLICATION INTO SEPARATE LAYERS.##################################
FROM package as extract

WORKDIR /build

RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted


#FROM extract as development
#WORKDIR /build
#RUN cp -r /build/target/extracted/dependencies/. ./
#RUN cp -r /build/target/extracted/spring-boot-loader/. ./
#RUN cp -r /build/target/extracted/snapshot-dependencies/. ./
#RUN cp -r /build/target/extracted/application/. ./
#CMD [ "java", "-Dspring.profiles.active=postgres", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000'", "org.springframework.boot.loader.launch.JarLauncher" ]

################### Create a new stage for running the application that contains the minimal############################

FROM eclipse-temurin:17-jre-jammy AS final

# Create a non-privileged user that the app will run under.
# See https://docs.docker.com/go/dockerfile-user-best-practices/
ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser
USER appuser

# Copy the executable from the "package" stage.
COPY --from=extract build/target/extracted/dependencies/ ./
COPY --from=extract build/target/extracted/spring-boot-loader/ ./
COPY --from=extract build/target/extracted/snapshot-dependencies/ ./
COPY --from=extract build/target/extracted/application/ ./

EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]