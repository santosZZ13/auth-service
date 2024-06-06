package org.authen.configuration.security.filter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.web.DefaultSecurityFilterChain;

import javax.servlet.Filter;
import java.util.List;
import java.util.stream.Collectors;

//@Component
public class SecurityFilterChainPostProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof DefaultSecurityFilterChain) {
			DefaultSecurityFilterChain filterChain = (DefaultSecurityFilterChain) bean;
			List<Filter> filters = filterChain.getFilters().stream()
					.filter(filter -> !(filter instanceof BearerTokenAuthenticationFilter))
					.collect(Collectors.toList());
			return new DefaultSecurityFilterChain(filterChain.getRequestMatcher(), filters);
		}
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
