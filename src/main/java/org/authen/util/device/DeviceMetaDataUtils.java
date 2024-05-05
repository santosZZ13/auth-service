package org.authen.util.device;

import com.google.common.base.Strings;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Component
public class DeviceMetaDataUtils {

	private static final String UNKNOWN = "UNKNOWN";
	private final DatabaseReader databaseReader;
	private final Parser parser;

	public DeviceMetaDataUtils(@Qualifier("GeoIPCity") DatabaseReader  databaseReader, Parser parser) {
		this.databaseReader = databaseReader;
		this.parser = parser;
	}


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

	private String parseXForwardedHeader(String header) {
		return header.split(" *, *")[0];
	}

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
}
