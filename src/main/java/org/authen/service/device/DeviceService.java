package org.authen.service.device;

import org.authen.model.RequestWrapper;
import org.springframework.security.core.Authentication;

public interface DeviceService {
	void verifyDevice(Authentication authentication, RequestWrapper requestWrapper);
}
