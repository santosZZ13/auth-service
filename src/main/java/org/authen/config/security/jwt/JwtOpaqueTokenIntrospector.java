package org.authen.config.security.jwt;

import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;

import java.util.HashMap;
import java.util.Map;

public class JwtOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

	private final String INTROSPECTION_URI = "http://localhost:8081/oauth2/introspect";
	private final String CLIENT_ID = "demo-client";
	private final String CLIENT_SECRET = "demo-secret";
	private final SpringOpaqueTokenIntrospector springOpaqueTokenIntrospector = new SpringOpaqueTokenIntrospector(
			INTROSPECTION_URI, CLIENT_ID, CLIENT_SECRET);


	@Override
	public OAuth2AuthenticatedPrincipal introspect(String token) {
		OAuth2AuthenticatedPrincipal introspect;
		try {
			Map<String, Object> claims = new HashMap<>();
			introspect = convertClaimsSet(claims);
			return introspect;
		} catch (Exception e) {

		}
		introspect = this.springOpaqueTokenIntrospector.introspect(token);
		return introspect;
	}

	public OAuth2AuthenticatedPrincipal convertClaimsSet(Map<String, Object> claims) {
//		return new OAuth2IntrospectionAuthenticatedPrincipal(claims, authorities);
		return null;
	}
}
