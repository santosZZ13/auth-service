package org.authen.filter;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.authen.model.Request;
import org.authen.model.UserModel;
import org.authen.persistence.dao.DeviceMetadataRepository;
import org.authen.persistence.dao.UserJpaRepository;
import org.authen.persistence.model.DeviceMetadata;
import org.authen.persistence.model.UserEntity;
import org.authen.service.device.DeviceService;
import org.authen.service.user.UserService;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
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
	private final DeviceMetadataRepository deviceMetadataRepository;
	private final UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
		AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest servletRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		User user = (User) authentication.getPrincipal();
		UserModel userModelByUsername = userService.getUserModelByUsername(user.getUsername());
		Request request = Request.builder().request(servletRequest).build();

		if (Objects.nonNull(userModelByUsername)) {
			deviceService.verifyDevice(userModelByUsername, request);
		} else {
			throw new RuntimeException("User not found");
		}

	}

	private @Nullable DeviceMetadata findExistingDevice(Long userId, String deviceDetails, String location) {
		List<DeviceMetadata> knownDevices = deviceMetadataRepository.findByUserId(userId);

		for (DeviceMetadata existingDevice : knownDevices) {
			if (existingDevice.getDeviceDetails().equals(deviceDetails)
					&& existingDevice.getLocation().equals(location)) {
				return existingDevice;
			}
		}
		return null;
	}

	public void unknownDeviceNotification(String deviceDetails, String location, String ip, String email, Locale locale) {
		log.info("Unknown device detected: {} from {} with IP {}", deviceDetails, location, ip);
	}
}
