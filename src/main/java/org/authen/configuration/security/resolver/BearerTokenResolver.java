package org.authen.configuration.security.resolver;

import javax.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface BearerTokenResolver {
	String resolve(HttpServletRequest request);
}
