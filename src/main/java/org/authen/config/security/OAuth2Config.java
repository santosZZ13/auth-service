//package org.authen.config.security;
//
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//
//@Configuration
//@EnableAuthorizationServer
//public class OAuth2Config implements AuthorizationServerConfigurer {
//	@Override
//	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//		security.checkTokenAccess("isAuthenticated()")
//				.tokenKeyAccess("permitAll()");
////				.allowFormAuthenticationForClients();
//	}
//
//	@Override
//	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		clients.inMemory()
//				.withClient("client")
//				.secret("secret")
//				.scopes("read", "write")
//				.authorizedGrantTypes("password", "refresh_token")
//				.accessTokenValiditySeconds(3600)
//				.refreshTokenValiditySeconds(2592000);
//	}
//
//	@Override
//	public void configure(AuthorizationServerEndpointsConfigurer authorizationServerEndpointsConfigurer) throws Exception {
//		// Configure the non-security features of the Authorization Server endpoints,
//		// like token store, token customizations, user approvals and grant types.
//	}
//}
