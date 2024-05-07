//package org.gateway.auth.repository;
//
//import org.bson.types.ObjectId;
//import org.gateway.auth.model.entity.UserEntity;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//public interface UserMongoRepository extends MongoRepository<UserEntity, ObjectId> {
////	@NotNull <T extends UserEntity> UserEntity save(@NotNull UserEntity userEntity);
//
//	@Override
//	<S extends UserEntity> @NotNull S save(@NotNull S entity);
//
//	Optional<UserEntity> findByUsername(@NotNull String username);
//}
