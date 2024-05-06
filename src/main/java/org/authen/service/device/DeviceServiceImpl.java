package org.authen.service.device;

import org.authen.model.RequestWrapper;
import service.model.UserModel;
import persistent.repository.DeviceMetadataRepository;
import persistent.entity.DeviceMetadata;
import org.authen.service.user.UserService;
import org.authen.util.device.DeviceMetaDataUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class DeviceServiceImpl implements DeviceService {

	private static final String UNKNOWN = "UNKNOWN";
	private final DeviceMetadataRepository deviceMetadataRepository;
	private final DeviceMetaDataUtils deviceMetaDataUtils;
	private final UserService userService;


	public DeviceServiceImpl(DeviceMetadataRepository deviceMetadataRepository, DeviceMetaDataUtils deviceMetaDataUtils, UserService userService) {
		this.deviceMetadataRepository = deviceMetadataRepository;
		this.deviceMetaDataUtils = deviceMetaDataUtils;
		this.userService = userService;
	}

	private String parseXForwardedHeader(String header) {
		return header.split(" *, *")[0];
	}

	@Override
	public void verifyDevice(Authentication authentication, RequestWrapper requestWrapper) {

		User user = (User) authentication.getPrincipal();
		UserModel userModelByUsername = userService.getUserModelByUsername(user.getUsername());

		if (Objects.nonNull(userModelByUsername)) {
			try {
				final String ip = deviceMetaDataUtils.extractIpFromRequest(requestWrapper.getRequest());
				final String location = deviceMetaDataUtils.getIpLocation(ip);
				final String deviceDetails = deviceMetaDataUtils.getDeviceDetails(requestWrapper.getRequest().getHeader("user-agent"));
				final Locale locate = requestWrapper.getRequest().getLocale();

				DeviceMetadata existingDevice = findExistingDevice(userModelByUsername.getId(), deviceDetails, location);
				final Long userId = userModelByUsername.getId();
				final String email = userModelByUsername.getEmail();

				if (Objects.isNull(existingDevice)) {
					unknownDeviceNotification(deviceDetails, location, ip, email, locate);
					DeviceMetadata deviceMetadata = DeviceMetadata.builder()
							.userId(userId)
							.deviceDetails(deviceDetails)
							.location(deviceDetails)
							.lastLoggedIn(new Date())
							.build();
					deviceMetadataRepository.save(deviceMetadata);
				} else {
					existingDevice.setLastLoggedIn(new Date());
					deviceMetadataRepository.save(existingDevice);
				}
			} catch (Exception e) {
				throw new RuntimeException("Error while verifying device");
			}
		}
	}

	private void unknownDeviceNotification(String deviceDetails, String location, String ip, String email, Locale locale) {

	}

	private DeviceMetadata findExistingDevice(Long userId, String deviceDetails, String location) {
		List<DeviceMetadata> knownDevices = deviceMetadataRepository.findByUserId(userId);

		for (DeviceMetadata existingDevice : knownDevices) {
			if (existingDevice.getDeviceDetails().equals(deviceDetails) &&
					existingDevice.getLocation().equals(location)) {
				return existingDevice;
			}
		}
		return null;
	}
}
