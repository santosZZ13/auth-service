package org.authen.config.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;
import org.authen.enums.AuthConstants;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;


@Component
@Log4j2
public class JwtUtils {

	@Value("${santos.jwt.access_token_expiration_second}")
	private long accessTokenExpirationSecond;

	@Value("${santos.jwt.refresh_token_expiration_second}")
	private long refreshTokenExpirationSecond;

	@Value("${santos.jwt.secret}")
	private String jwtSecret;


	public Map<String, String> generateTokens(Authentication authentication) {

		try {
			log.info("#generateTokens ::: user: {}", authentication.getName());
			final String accessToken = this.createToken(authentication, accessTokenExpirationSecond);
			final String refreshToken = this.createToken(authentication, refreshTokenExpirationSecond);
			return Map.of(
					AuthConstants.ACCESS_TOKEN, accessToken,
					AuthConstants.REFRESH_TOKEN, refreshToken);

		} catch (Exception e) {
			log.error("#generateTokens ::: user: {} ::: Error Occurred: {}", authentication.getName(), e.getMessage());
//			throw new RuntimeException(e.getMessage());
//			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * {
	 *     "active": true,
	 *     "sub": "demo-client",
	 *     "aud": [
	 *         "demo-client"
	 *     ],
	 *     "nbf": 1717645685,
	 *     "iss": "http://localhost:8081",
	 *     "exp": 1717663685,
	 *     "iat": 1717645685,
	 *     "jti": "364841bc-41ee-42cc-9ecd-1e2cfe548836",
	 *     "authorities": [
	 *         "ARTICLE_WRITE",
	 *         "ARTICLE_READ"
	 *     ],
	 *     "username": "admin",
	 *     "client_id": "demo-client",
	 *     "token_type": "Bearer"
	 * }
	 *
	 *
	 * // //		{
	 * //			"sub": "quangnam1305@gmail.com",
	 * //				"authorities": "ROLE_USER",
	 * //				"iat": 1717575691,
	 * //				"exp": 1717577491
	 * //		}
	 * @param authentication
	 * @param expirationSecond
	 * @return
	 */
	@Nonnull
	private String createToken(Authentication authentication, long expirationSecond) {
		final String userName = authentication.getName();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		Map<String, Object> claimsProperties = Map.of(
				"active", Boolean.TRUE,
//				"aud", List.of("demo-client"),
//				"nbf", Date.from(Instant.now()).getTime(),
				"iss", "http://localhost:8080",
//				"exp", Date.from(Instant.now()).getTime() + expirationSecond * 1000,
//				"iat", Date.from(Instant.now()).getTime(),
//				"jti", UUID.randomUUID().toString(),
				"username", userName,
//				"client_id", "demo-client",
				"token_type", "Bearer",
				"authorities", authorities.stream().map(GrantedAuthority::getAuthority).collect(joining(","))
		);

		Claims claims = Jwts.claims()
//				.subject(userName)
				.add(claimsProperties)
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

	private SecretKey getSingingKey() {
		byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
		// In the case where the secret key is not Base64 encoded, we can invoke the getByte() method on the plain string:
		// byte[] keyBytes = this.jwtSecret.getBytes(StandardCharsets.UTF_8);
		// return Keys.hmacShaKeyFor(keyBytes);
	}

	public Boolean validateJwtToken(final String token) {
		try {
			Jws<Claims> claimsJws = Jwts.parser()
					.verifyWith(getSingingKey())
					.build()
					.parseSignedClaims(token);
			return true;
		} catch (SignatureException ex) {
//			log.error("#validateJwtToken ::: Error Occurred: {}", exception.getMessage());
			//Invalid JWT signature: {}
		} catch (MalformedJwtException ex) {
//			log.error("#validateJwtToken ::: Error Occurred: {}", exception.getMessage());
			// Invalid JWT token
		} catch (ExpiredJwtException exception) {
//			log.error("#validateJwtToken ::: Error Occurred: {}", exception.getMessage());
			// JWT token is expired: {}
		} catch (UnsupportedJwtException exception) {
			//JWT token is unsupported: {}
		}
		return false;
	}

	private static final Pattern JWT_PATTERN = Pattern.compile("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_.+/=]*$");

	public static boolean isJwt(String token) {
		return JWT_PATTERN.matcher(token).matches();
	}

	public String extractUsername(String token) {
		return null;
	}

	public Map<String, Object> getClaimsFromToken(final @NotNull String token) {
		return Jwts.parser()
				.setSigningKey(getSingingKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
