package org.authen.service.device;

import com.google.common.base.Strings;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.authen.model.Request;
import org.authen.model.UserModel;
import org.authen.persistence.dao.DeviceMetadataRepository;
import org.authen.persistence.model.DeviceMetadata;
import org.authen.persistence.model.UserEntity;
import org.authen.util.device.DeviceMetaDataUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Service
public class DeviceServiceImpl implements DeviceService {

	private static final String UNKNOWN = "UNKNOWN";
	private final DeviceMetadataRepository deviceMetadataRepository;
	private DeviceMetaDataUtils deviceMetaDataUtils;



	public DeviceServiceImpl(DeviceMetadataRepository deviceMetadataRepository) {
		this.deviceMetadataRepository = deviceMetadataRepository;
	}

	private String parseXForwardedHeader(String header) {
		return header.split(" *, *")[0];
	}

	@Override
	public void verifyDevice(UserModel userModel, Request request) {

		try {
			final String ip = deviceMetaDataUtils.extractIpFromRequest(request.getRequest());
			final String location = deviceMetaDataUtils.getIpLocation(ip);
			final String deviceDetails = deviceMetaDataUtils.getDeviceDetails(request.getRequest().getHeader("user-agent"));
			final Locale locate = request.getRequest().getLocale();

			DeviceMetadata existingDevice = findExistingDevice(userModel.getId(), deviceDetails, location);
			final Long userId = userModel.getId();
			final String email = userModel.getEmail();

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
