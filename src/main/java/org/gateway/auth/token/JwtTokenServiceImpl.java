package org.gateway.auth.token;

import io.jsonwebtoken.*;
import org.gateway.auth.enums.AuthConstants;
import org.gateway.auth.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.Instant;
import java.util.*;

import static java.util.stream.Collectors.joining;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

	@Value("${jwt.access_token_expiration_second}")
	private long accessTokenExpirationSecond;

	@Value("${jwt.refresh_token_expiration_second}")
	private long refreshTokenExpirationSecond;

	@Value("${jwt.secret}")
	private String secret;
	private final String AUTHORITIES_KEY = "secret";
	@Override
	public Map<String, String> generateTokens(Authentication authentication, UserEntity userEntity) {
		String accessToken = createToken(authentication, userEntity, accessTokenExpirationSecond);
		String refreshToken = createToken(authentication, userEntity, refreshTokenExpirationSecond);
		assert accessToken != null;
		assert refreshToken != null;
		return Map.of(AuthConstants.ACCESS_TOKEN, accessToken, AuthConstants.REFRESH_TOKEN, refreshToken);
	}

	private String createToken(Authentication authentication, UserEntity userEntity, long expirationSecond) {

		final String userName = authentication.getName();
		final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		Claims claims = Jwts.claims().setSubject(userName).setId(userEntity.getId());
		claims.put(AUTHORITIES_KEY, authorities.stream().map(GrantedAuthority::getAuthority).collect(joining(",")));

		Date from = Date.from(Instant.now());
		Date validity = new Date(System.currentTimeMillis() + expirationSecond * 1000);

		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(from)
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	@Override
	public String getTokenFromRequest(HttpServletRequest request) {
		Enumeration<String> authorizationHeaders = request.getHeaders("Authorization");
		if (authorizationHeaders != null && authorizationHeaders.hasMoreElements()) {
			String authorizationHeader = authorizationHeaders.nextElement();
			if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
				return authorizationHeader.substring(7);
			}
		}
//		if (authorizationHeaders != null && !authorizationHeaders.isEmpty()) {
//			String authorizationHeader = authorizationHeaders.get(0);
//			if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
//				return authorizationHeader.substring(7);
//			}
//		}
		return null;
	}


	@Override
	public Claims getTokenBody(String token) {
		try {
			return Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token)
					.getBody();
		} catch (SignatureException | ExpiredJwtException e) {
			throw new AccessDeniedException("Access denied: " + e.getMessage());
		}
	}

	@Override
	public String extractUsername(String token) {
		return getTokenBody(token).getSubject();
	}


	@Override
	public boolean validateToken(String token, UserEntity userEntity) {
		return extractUsername(token).equals(userEntity.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return getTokenBody(token).getExpiration().before(new Date());
	}
}
