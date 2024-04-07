package org.authen.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.authen.model.dto.ApiErrorResponse;
import org.authen.model.entity.UserEntity;
import org.authen.service.user.UserService;
import org.authen.jwt.JwtTokenService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

			final String authHeader = request.getHeader("Authorization");
			String token = null;
			String userName = null;

			// Check if the Authorization header is not null and starts with "Bearer "
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				token = authHeader.substring(7);
				userName = jwtTokenService.extractUsername(token);
			}

			if (Objects.isNull(token)) {
				filterChain.doFilter(request, response);
				return;
			}

			if (Objects.nonNull(userName) && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserEntity byUsername = userService.findByUsername(userName);
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

	private String toJson(ApiErrorResponse errorResponse) {
		try {
			return objectMapper.writeValueAsString(errorResponse);
		} catch (Exception e) {
			return "";
		}
	}
}
