package org.authen.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.authen.level.service.model.UserModel;
import org.authen.web.dto.ApiErrorResponse;
import org.authen.service.user.UserService;
import org.authen.jwt.JwtTokenService;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.util.Pair;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final UserService userService;
	private final JwtTokenService jwtTokenService;
	private final ObjectMapper objectMapper;
	@Override
	protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
									@NotNull FilterChain filterChain) throws ServletException, IOException {
		try {

			if (isByPassToken(request)) {
				filterChain.doFilter(request, response);
				return;
			}

			final String authHeader = request.getHeader("Authorization");
			String token = null;
			String userName = null;


			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			// Check if the Authorization header is not null and starts with "Bearer "
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				token = authHeader.substring(7);
				userName = jwtTokenService.extractUsername(token);
			}

			if (Objects.isNull(token)) {
				filterChain.doFilter(request, response);
				return;
			}

			if (Objects.nonNull(userName) && authentication == null) {
				UserModel byUsername = userService.getUserModelByUsername(userName);
				UserDetails userDetails = userService.toUserDetails(byUsername);

				if (jwtTokenService.validateToken(token, byUsername)) {
					UsernamePasswordAuthenticationToken authenticationToken  = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}

			filterChain.doFilter(request, response);

		} catch (AccessDeniedException e) {
			ApiErrorResponse errorResponse = new ApiErrorResponse(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.getWriter().write(toJson(errorResponse));
		}
	}


	//TODO: Implement the isByPassToken method to check if the request is a by pass token
	private boolean isByPassToken(HttpServletRequest request) {
		final List<Pair<String, String>> byPassTokens = List.of(
				Pair.of("POST", "/api/auth/login"),
				Pair.of("POST", "/api/auth/registration")
				// Swagger
				// Pair.of("GET", "/v2/api-docs"),
				// Pair.of("GET", "/swagger-resources"),
				// Pair.of("GET", "/swagger-resources/**"),
				// Pair.of("GET", "/configuration/ui"),
				// Pair.of("GET", "/configuration/security"),
				// Pair.of("GET", "/swagger-ui.html"),
				// Pair.of("GET", "/webjars/**"),
				// Pair.of("GET", "/swagger-ui/**")

		);
//		if (byPassTokens.stream().anyMatch(pair -> pair.getFirst().equals(request.getMethod()) && pair.getSecond().equals(request.getRequestURI()))) {
//			return true;
//		}

		//TODO:
		// https://www.youtube.com/watch?v=--V-CZnD6TA#:~:text=properties%20ho%E1%BA%B7c%20application.,ho%E1%BA%B7c%20quy%E1%BB%81n%20c%E1%BB%A7a%20ng%C6%B0%E1%BB%9Di%20d%C3%B9ng.
		// 4:39
		return false;
	}

	private String toJson(ApiErrorResponse errorResponse) {
		try {
			return objectMapper.writeValueAsString(errorResponse);
		} catch (Exception e) {
			return "";
		}
	}
}
