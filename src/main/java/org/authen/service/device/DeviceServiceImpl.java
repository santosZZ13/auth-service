package org.authen.service.device;

import lombok.AllArgsConstructor;
import org.authen.wapper.model.RequestWrapper;
import org.authen.level.service.device.DeviceLogicService;
import org.authen.level.service.model.DeviceMetaDataModel;
import org.authen.level.service.model.UserModel;
import org.authen.util.device.DeviceMetaDataUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.authen.level.service.user.UserLogicService;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@AllArgsConstructor
public class DeviceServiceImpl implements DeviceService {

	private static final String UNKNOWN = "UNKNOWN";
	private final DeviceMetaDataUtils deviceMetaDataUtils;
	private final UserLogicService userLogicService;
	private final DeviceLogicService deviceLogicService;



}
