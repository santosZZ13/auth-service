package org.authen.level.service.device;

import org.authen.level.service.model.DeviceMetaDataModel;

import java.util.List;

public interface DeviceLogicService {
	void saveDevice(DeviceMetaDataModel deviceMetadataModel);
	List<DeviceMetaDataModel> findByUSerId(Long userId);
}
