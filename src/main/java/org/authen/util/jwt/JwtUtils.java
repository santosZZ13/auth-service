package org.authen.util.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;
import org.authen.enums.AuthConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

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


	@Nonnull
	private String createToken(Authentication authentication, long expirationSecond) {
		final String userName = authentication.getName();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		Claims claims = Jwts.claims()
				.subject(userName)
				.add(Map.of("authorities", authorities.stream().map(GrantedAuthority::getAuthority).collect(joining(","))))
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
}
