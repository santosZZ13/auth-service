package org.gateway.auth.token;

import io.jsonwebtoken.Claims;
import org.gateway.auth.model.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public interface JwtTokenService {
	Map<String, String> generateTokens(Authentication authentication, UserEntity userEntity);
	Claims getTokenBody(String token);
	default String getTokenFromRequest(HttpServletRequest request) {
		return "";
	};
	default String extractUsername(String token) {
		return "";
	};
	boolean validateToken(String token, UserEntity userEntity);
}

