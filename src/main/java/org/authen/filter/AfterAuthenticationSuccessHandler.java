package org.authen.filter;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.authen.model.RequestWrapper;
import org.authen.service.device.DeviceService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
@AllArgsConstructor
@Log4j2
public class AfterAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final DeviceService deviceService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
		AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest servletRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		RequestWrapper requestWrapper = RequestWrapper.builder().request(servletRequest).build();
		try {
			deviceService.verifyDevice(authentication, requestWrapper);
		} catch (Exception e) {
			log.error("Error while verifying device", e);
			throw new RuntimeException("Error while verifying device");
		}
	}


	public void unknownDeviceNotification(String deviceDetails, String location, String ip, String email, Locale locale) {
		log.info("Unknown device detected: {} from {} with IP {}", deviceDetails, location, ip);
	}
}
