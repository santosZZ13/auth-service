package org.authen.service.device;

import org.authen.model.Request;
import org.authen.model.UserModel;

import javax.servlet.http.HttpServletRequest;

public interface DeviceService {
	void verifyDevice(UserModel userModel, Request request);
}
