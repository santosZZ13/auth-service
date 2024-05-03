package org.authen.service.device;

import javax.servlet.http.HttpServletRequest;

public interface DeviceService {
	String extractIpFromRequest(HttpServletRequest request);
	String getIpLocation(String ip);
	String getDeviceDetails(String userAgent);
}
