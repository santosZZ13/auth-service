package org.authen.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
public class CustomHeaderValidatorFilter  extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		log.info("##START CustomHeaderValidatorFilter");

		/**
		 * We have a list of endpoint to other service
		 * ENPOINTS_SERVICE = {"http://localhost:8081/api/service", "http://localhost:8082/api/v1/service2", ...}
		 * -> If the request is in the list, use RestTemplate to call the service and get the response to return to the client
		 * -> If the request is not in the list, continue the filter chain
		 * -> If the request is in the list but the response is not 200, return the error to the client
		 */
		Boolean isExistInConfigMapping = Boolean.TRUE;

		if (isExistInConfigMapping) {
			// Call the service
		}


		filterChain.doFilter(request, response);
	}
}
