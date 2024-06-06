package org.authen.config.security.jwt;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;

import java.util.*;

@AllArgsConstructor
public class JwtOpaqueTokenIntrospector implements OpaqueTokenIntrospector {
	private static final String AUTHORITY_PREFIX = "SCOPE_";
	private final String INTROSPECTION_URI = "http://localhost:8081/oauth2/introspect";
	private final String CLIENT_ID = "demo-client";
	private final String CLIENT_SECRET = "demo-secret";
	private final SpringOpaqueTokenIntrospector springOpaqueTokenIntrospector = new SpringOpaqueTokenIntrospector(
			INTROSPECTION_URI, CLIENT_ID, CLIENT_SECRET);

	private final JwtUtils jwtUtils;

	@Override
	public OAuth2AuthenticatedPrincipal introspect(String token) {
		OAuth2AuthenticatedPrincipal introspect;
		try {
			Map<String, Object> claims = jwtUtils.getClaimsFromToken(token);
			introspect = convertClaimsSet(claims);
			return introspect;
		} catch (Exception e) {

		}
		introspect = this.springOpaqueTokenIntrospector.introspect(token);
		return introspect;
	}

	public OAuth2AuthenticatedPrincipal convertClaimsSet(Map<String, Object> claims) {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		claims.computeIfPresent(OAuth2TokenIntrospectionClaimNames.SCOPE, (k, v) -> {
			if (v instanceof String) {
				Collection<String> scopes = Arrays.asList(((String) v).split(" "));
				for (String scope : scopes) {
					authorities.add(new SimpleGrantedAuthority(AUTHORITY_PREFIX + scope));
				}
				return scopes;
			}
			return v;
		});
		return new OAuth2IntrospectionAuthenticatedPrincipal(claims, authorities);
	}
}
