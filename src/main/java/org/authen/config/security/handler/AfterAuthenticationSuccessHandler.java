package org.authen.config.security.handler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.authen.level.service.device.DeviceLogicService;
import org.authen.level.service.model.DeviceMetaDataModel;
import org.authen.level.service.model.UserModel;
import org.authen.service.user.UserService;
import org.authen.util.device.DeviceMetaDataUtils;
import org.authen.wapper.model.RequestWrapper;
import org.authen.web.exception.VerifyException;
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

	private final UserService userService;
	private final DeviceMetaDataUtils deviceMetaDataUtils;
	private final DeviceLogicService deviceLogicService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
		AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest servletRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		RequestWrapper requestWrapper = RequestWrapper.builder().request(servletRequest).build();
		User user = (User) authentication.getPrincipal();
		String username = user.getUsername();
		UserModel userModelByUsername = userService.getUserModelByUsername(username);
		if (Objects.nonNull(userModelByUsername)) {

			try {
				final String ip = deviceMetaDataUtils.extractIpFromRequest(requestWrapper.getRequest());
				final String location = deviceMetaDataUtils.getIpLocation(ip);
				final String deviceDetails = deviceMetaDataUtils.getDeviceDetails(requestWrapper.getRequest().getHeader("user-agent"));
				final Locale locate = requestWrapper.getRequest().getLocale();

				DeviceMetaDataModel existingDevice = findExistingDevice(userModelByUsername.getId(), deviceDetails, location);
				final Long userId = userModelByUsername.getId();
				final String email = userModelByUsername.getEmail();
				if (Objects.isNull(existingDevice)) {
					unknownDeviceNotification(deviceDetails, location, ip, email, locate);
					DeviceMetaDataModel deviceMetadataEntity = DeviceMetaDataModel.builder()
							.userId(userId)
							.deviceDetails(deviceDetails)
							.location(deviceDetails)
							.lastLoggedIn(new Date())
							.build();
					deviceLogicService.saveDevice(deviceMetadataEntity);
				} else {
					existingDevice.setLastLoggedIn(new Date());
					deviceLogicService.saveDevice(existingDevice);
				}
			} catch (Exception e) {
				log.error("#Authen onAuthenticationSuccess :: {} ::  Error Occurred ::  {} ", e, e.getMessage());
				throw new VerifyException("Error occurred when verifying the device", "XXX", "User: " + username);
			}
		}


	}


	public void unknownDeviceNotification(String deviceDetails, String location, String ip, String email, Locale locale) {
		log.info("Unknown device detected: {} from {} with IP {}", deviceDetails, location, ip);
	}

	private DeviceMetaDataModel findExistingDevice(Long userId, String deviceDetails, String location) {
		List<DeviceMetaDataModel> knownDevices = deviceLogicService.findByUSerId(userId);

		for (DeviceMetaDataModel existingDevice : knownDevices) {
			if (existingDevice.getDeviceDetails().equals(deviceDetails) &&
					existingDevice.getLocation().equals(location)) {
				return existingDevice;
			}
		}
		return null;
	}
}
