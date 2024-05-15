package org.authen.config.security;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.authen.filter.*;
//import org.authen.service.user.OAuth2UserServiceImpl;
import org.authen.service.user.UserServiceImpl;
import org.authen.errors.security.UserAuthenticationErrorHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Log4j2
@AllArgsConstructor
@EnableConfigurationProperties(BasicAuthProperties.class)
public class SecurityConfig {

	private final AfterAuthenticationSuccessHandler afterAuthenticationSuccessHandler;
	private final AfterOauth2SuccessHandler afterOauth2SuccessHandler;
	private final UserServiceImpl userServiceImpl;
	private final JwtAuthFilter jwtAuthFilter;
	//	private final GatewayServletFilter gatewayServletFilter;
//	private final CustomHeaderValidatorFilter customHeaderValidatorFilter;
//	private final OAuth2UserServiceImpl oAuth2UserService;


	public static final String[] USER_WHITELIST = {"/api/user/**"};
	public static final String[] TEST_WHITELIST = {"/api/test/**"};
	public static final String[] ADMIN_WHITELIST = {"/api/admin/**"};
	public static final String[] PUBLIC_WHITELIST = {"/api/public/**"};
	public static final String[] PRIVATE_WHITELIST = {"/api/private/**"};
	public static final String[] AUTH_WHITELIST = {"/api/auth/**"};
	public static final String[] O_AUTH = {"/oauth/**"};
	public static final String USER_ROLE_NAME = "USER";
	public static final String ADMIN_ROLE_NAME = "ADMIN";


	/**
	 * Configuring the authentication manager
	 *
	 * @param authenticationConfiguration the authentication configuration
	 * @return the authentication manager
	 * @throws Exception
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	/**
	 * Configuring the authentication provider
	 *
	 * @return the authentication provider
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setUserDetailsService(this.userServiceImpl);
		return authenticationProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public SecurityFilterChain securityWebFilterChain(@NotNull HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				/**
				 * Why does setting sessioncreation policy to stateless break my oauth2 app
				 * https://stackoverflow.com/questions/67377634/why-does-setting-sessioncreation-policy-to-stateless-break-my-oauth2-app
				 */
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(request -> {
					request.antMatchers(null, PUBLIC_WHITELIST).permitAll()
							.antMatchers(null, AUTH_WHITELIST).permitAll()
							.antMatchers(null, PRIVATE_WHITELIST).authenticated()
							.antMatchers(null, USER_WHITELIST).hasAnyRole(USER_ROLE_NAME)
							.antMatchers(null, ADMIN_WHITELIST).hasAnyRole(ADMIN_ROLE_NAME)
							.antMatchers(null, TEST_WHITELIST).authenticated()
							.anyRequest().authenticated();
				})
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
//				.oauth2Login(
//						oauth -> oauth
//								.userInfoEndpoint()
//								.userService(oAuth2UserService)
//								.and()
//								.successHandler(afterOauth2SuccessHandler)
//				);
//				.formLogin(httpSecurityFormLoginConfigurer ->
//						httpSecurityFormLoginConfigurer.loginPage("/api/auth/login")
//								.successHandler(afterAuthenticationSuccessHandler)
//								.permitAll())
//				.httpBasic()
//				.and();
		http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		http.addFilterAfter(new GenericResponseFilter(), FilterSecurityInterceptor.class);
		//
//
//		http.addFilterAfter(afterAuthenticationSuccessHandlers, JwtAuthFilter.class);
		return http.build();
	}

//	@Bean
//	@Order(1)
//	public SecurityFilterChain testFiletChain(HttpSecurity http) throws Exception {
//		http
////				.addFilterAfter(new GatewayServletFilter(gatewayProperties), UsernamePasswordAuthenticationFilter.class)
//				.csrf().disable()
//				.sessionManagement(session -> session
//						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.antMatcher("/api/test/**")
//				.authorizeRequests()
//				.antMatchers(HttpMethod.GET, "/api/test/**").hasRole("USER")
//				.anyRequest().authenticated()
//				.and()
//				.httpBasic()
//				.and()
//				.exceptionHandling(exception -> exception
//						.authenticationEntryPoint(userAuthenticationErrorHandler())
//						.accessDeniedHandler(new UserForbiddenErrorHandler()));
//		http.addFilterBefore(gatewayServletFilter, UsernamePasswordAuthenticationFilter.class);
//		http.addFilterBefore(customHeaderValidatorFilter, UsernamePasswordAuthenticationFilter.class);
//
//		return http.build();
//	}

//	@Bean
//	public WebSecurityCustomizer webSecurityCustomizer() {
//		return (web) -> web.ignoring().antMatchers("/api/public/**");
//	}

	@Bean
	public AuthenticationEntryPoint userAuthenticationErrorHandler() {
		UserAuthenticationErrorHandler userAuthenticationErrorHandler = new UserAuthenticationErrorHandler();
		userAuthenticationErrorHandler.setRealmName("Basic Authentication");
		return userAuthenticationErrorHandler;
	}
}
