package org.gateway.auth.filter;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@ConfigurationProperties(prefix = "api-gateway")
@Component
public class GatewayProperties {
	private final Map<String, String> mappings = new HashMap<>();
}
