package org.authen.service.device;

import com.google.common.base.Strings;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.authen.persistence.dao.DeviceMetadataRepository;
import org.authen.persistence.model.DeviceMetadata;
import org.authen.persistence.model.UserEntity;
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
	private final DatabaseReader databaseReader;
	private final Parser parser;
	private final DeviceMetadataRepository deviceMetadataRepository;

	public DeviceServiceImpl(@Qualifier("GeoIPCity") DatabaseReader databaseReader, Parser parser, DeviceMetadataRepository deviceMetadataRepository) {
		this.databaseReader = databaseReader;
		this.parser = parser;
		this.deviceMetadataRepository = deviceMetadataRepository;
	}

	@Override
	public String extractIpFromRequest(@NotNull HttpServletRequest request) {
		final String clientIp;
		String clientXForwardedForIp = request.getHeader("x-forwarded-for");
		if (nonNull(clientXForwardedForIp)) {
			clientIp = parseXForwardedHeader(clientXForwardedForIp);
		} else {
			clientIp = request.getRemoteAddr();
		}
		return clientIp;
	}

	@Override
	public String getIpLocation(String ip) {
		String location = null;

		try {
			InetAddress ipAddress = InetAddress.getByName(ip);
			CityResponse cityResponse = databaseReader.city(ipAddress);

			if (nonNull(cityResponse) &&
					nonNull(cityResponse.getCity()) &&
					!Strings.isNullOrEmpty(cityResponse.getCity().getName())) {

				location = cityResponse.getCity().getName();
				return location;
			}

		} catch (IOException | GeoIp2Exception exception) {
			location = UNKNOWN;
//			throw new UnableToLocateIpException("Unable to locate IP address: " + ip, exception);
		}
		return location;
	}

	@Override
	public String getDeviceDetails(String userAgent) {
		String deviceDetails = UNKNOWN;
		Client client = parser.parse(userAgent);

		if (Objects.nonNull(client)) {
			deviceDetails = client.userAgent.family
					+ " " + client.userAgent.major + "."
					+ client.userAgent.minor + " - "
					+ client.os.family + " " + client.os.major
					+ "." + client.os.minor;
		}
		return deviceDetails;
	}

	private String parseXForwardedHeader(String header) {
		return header.split(" *, *")[0];
	}

	public void verifyDevice(UserEntity user, HttpServletRequest request) {
		String ip = extractIpFromRequest(request);
		String location = getIpLocation(ip);
		String deviceDetails = getDeviceDetails(request.getHeader("user-agent"));

		DeviceMetadata existingDevice = findExistingDevice(user.getId(), deviceDetails, location);

		if (Objects.isNull(existingDevice)) {
			unknownDeviceNotification(deviceDetails, location, ip, user.getEmail(), request.getLocale());
			DeviceMetadata deviceMetadata = new DeviceMetadata();
			deviceMetadata.setUserId(user.getId());
			deviceMetadata.setLocation(location);
			deviceMetadata.setDeviceDetails(deviceDetails);
			deviceMetadata.setLastLoggedIn(new Date());
			deviceMetadataRepository.save(deviceMetadata);
		} else {
			existingDevice.setLastLoggedIn(new Date());
			deviceMetadataRepository.save(existingDevice);
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
