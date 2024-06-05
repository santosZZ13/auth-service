package org.authen.jwt;

import io.jsonwebtoken.*;

import io.jsonwebtoken.security.Keys;
import org.authen.enums.AuthConstants;
import org.authen.level.service.model.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.*;

import static java.util.stream.Collectors.joining;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

	@Value("${santos.jwt.access_token_expiration_second}")
	private long accessTokenExpirationSecond;

	@Value("${santos.jwt.refresh_token_expiration_second}")
	private long refreshTokenExpirationSecond;

	@Value("${santos.jwt.secret}")
	private String jwtSecret;

	private final String AUTHORITIES_KEY = "secret";

	@Override
	public Map<String, String> generateTokens(Authentication authentication) {
		String accessToken = createToken(authentication, accessTokenExpirationSecond);
		String refreshToken = createToken(authentication, refreshTokenExpirationSecond);
		assert accessToken != null;
		assert refreshToken != null;
		return Map.of(AuthConstants.ACCESS_TOKEN, accessToken, AuthConstants.REFRESH_TOKEN, refreshToken);
	}

	private String createToken(Authentication authentication, long expirationSecond) {

		final String userName = authentication.getName();
		final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		Claims claims = Jwts.claims()
				.subject(userName)
				.add(Map.of("role", authorities.stream().map(GrantedAuthority::getAuthority).collect(joining(","))))
				.build();

		Date from = Date.from(Instant.now());
		Date validity = new Date(System.currentTimeMillis() + expirationSecond * 1000);

		return Jwts.builder()
				.claims(claims)
				.issuedAt(from)
				.expiration(validity)
				.signWith(getSingingKey())
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
					.setSigningKey(getSingingKey())
					.build()
					.parseClaimsJws(token)
					.getBody();
//			return null;
//			return Jwts.parser()
//					.setSigningKey(getSingingKey())
//					.build();
//					.parseClaimsJws(token)
//					.getBody();
		} catch (SignatureException | ExpiredJwtException e) {
			throw new AccessDeniedException("Access denied: " + e.getMessage());
		}
	}

	@Override
	public String extractUsername(String token) {
		return getTokenBody(token).getSubject();
	}


	@Override
	public boolean validateToken(String token, UserModel userEntity) {
		return extractUsername(token).equals(userEntity.getEmail()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return getTokenBody(token).getExpiration().before(new Date());
	}

	private Key getSingingKey() {
		byte[] keyBytes =  jwtSecret.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
		// In the case where the secret key is not Base64 encoded, we can invoke the getByte() method on the plain string:
		// byte[] keyBytes = this.jwtSecret.getBytes(StandardCharsets.UTF_8);
		// return Keys.hmacShaKeyFor(keyBytes);
	}

}
