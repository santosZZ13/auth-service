//package org.authen.filter;
//
//
//import lombok.extern.log4j.Log4j2;
//import org.authen.jwt.JwtTokenService;
//import org.springframework.http.server.PathContainer;
//import org.springframework.stereotype.Component;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.util.UriComponentsBuilder;
//import org.springframework.web.util.pattern.PathPattern;
//import org.springframework.web.util.pattern.PathPatternParser;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.Set;
//
///**
// * This filter is in charge of intercepting all the requests that come to the gateway and
// * then redirect them to the corresponding microservice.
// * But it's non-reactive, and it's not recommended to use it.
// * It's better to use a WebFilter instead. In the future, this class will be removed and replaced by a ${@link GatewayFilter}.
// */
//
//@Deprecated(since = "1.0.0", forRemoval = true)
//@Log4j2
//@Component
//public class GatewayServletFilter implements Filter {
//
//	private final GatewayProperties gatewayProperties;
//	private final PathPatternParser pathPatternParser = new PathPatternParser();
//	private JwtTokenService jwtTokenService;
//
//	public GatewayServletFilter(GatewayProperties gatewayProperties) {
//		this.gatewayProperties = gatewayProperties;
//	}
//
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		Filter.super.init(filterConfig);
//	}
//
//	@Override
//	public void destroy() {
//		Filter.super.destroy();
//	}
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//		log.info("##START GatewayServletFilter");
//		chain.doFilter(request, response);
//
//	}
//
//	//	@Override
////	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
////
////		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
////		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
////
////
//////		boolean existInConfigMapping = isExistInConfigMapping(httpServletRequest);
//////		boolean isPublicRequest = isPublicRequest(httpServletRequest);
//////		boolean isAuthRequest = isAuthRequest(httpServletRequest);
////
////
////
////		// If the request path exists in the config mapping and (the request is public or not an auth request)
////		if (true) {
////			// Forward the request to the corresponding microservice
//////			final String requestPath = httpServletRequest.getRequestURI();
//////			final String targetUri = buildTargetUri(requestPath);
////			// final String queryString = buildQueryParamString(request.getParameterMap());
////
////			RestTemplate restTemplate = new RestTemplate();
////			HttpHeaders headers = new HttpHeaders();
////			headers.setBearerAuth("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb21wYW55MDAyMkBnbWFpbC5jb20iLCJqdGkiOiI2NTcxNzhlMTZlMzU2MTdkZjRmNzEyYjYiLCJzZWNyZXQiOiJST0xFX0NvbXBhbnkiLCJpYXQiOjE3MTA2ODc4NjEsImV4cCI6MTcxMDY4OTY2MX0.GekdP17DWXZjt6SfXwFlnxHt7ZefyUXJXgONhG968UfF1a6x8v9SRooD641qWhlrBNYZh_XPcHXqdTh5tviapg");
////			HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
////			try {
////				ResponseEntity<String> responseEntity = restTemplate
////						.exchange("http://localhost:8080/api/company/getJobs", HttpMethod.valueOf("POST"), entity, String.class);
////				httpServletResponse
////						.getWriter()
////						.write(Objects.requireNonNull(responseEntity.getBody()));
////			} catch (HttpClientErrorException e) {
////				httpServletResponse.getWriter().write(e.getResponseBodyAsString());
////			}
////		} else {
////			// Continue with the filter chain
////			filterChain.doFilter(httpServletRequest, servletResponse);
////		}
////
////		filterChain.doFilter(servletRequest, servletResponse);
////	}
//
//
//	private String buildTargetUri(String requestPath) {
//		Set<String> apiMappings = gatewayProperties.getMappings().keySet();
//		for (String pattern : apiMappings) {
//			PathPattern pathPattern = pathPatternParser.parse(pattern);
//			PathPattern.PathMatchInfo pathMatchInfo = pathPattern.matchAndExtract(PathContainer.parsePath(requestPath));
//			if (pathMatchInfo != null) {
//				return gatewayProperties.getMappings().get(pattern);
//			}
//		}
////		return StringUtils.EMPTY;
//		return "";
//	}
//
//	private String buildQueryParamString(MultiValueMap<String, String> queryParams) {
//		UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
//		queryParams.forEach((key, values) -> {
//			for (String value : values) {
//				builder.queryParam(key, value);
//			}
//		});
//		return builder.build().encode().toUriString();
//	}
//
//	private boolean isExistInConfigMapping(HttpServletRequest request) {
//		String requestURI = request.getRequestURI();
//		return gatewayProperties
//				.getMappings()
//				.keySet()
//				.stream()
//				.anyMatch(pattern -> pathPatternParser
//						.parse(pattern)
//						.matches(PathContainer.parsePath(requestURI)
//						));
//	}
//
//	private boolean isPublicRequest(HttpServletRequest request) {
//		return true;
//	}
//
//
//	private boolean isAuthRequest(HttpServletRequest request) {
//		return true;
//	}
//}
