package org.authen.level.service.device;

import lombok.AllArgsConstructor;
import org.authen.level.persistent.entity.DeviceMetadataEntity;
import org.authen.level.persistent.repository.DeviceMetadataRepository;
import org.springframework.stereotype.Service;
import org.authen.level.service.model.DeviceMetaDataModel;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DeviceLogicServiceImpl implements DeviceLogicService {

	private final DeviceMetadataRepository deviceMetadataRepository;

	@Override
	public void saveDevice(DeviceMetaDataModel deviceMetadataModel) {
		DeviceMetadataEntity deviceMetadataEntity = DeviceMetadataEntity.builder()
				.userId(deviceMetadataModel.getUserId())
				.id(deviceMetadataModel.getId())
				.deviceDetails(deviceMetadataModel.getDeviceDetails())
				.location(deviceMetadataModel.getLocation())
				.lastLoggedIn(deviceMetadataModel.getLastLoggedIn())
				.build();
		deviceMetadataRepository.save(deviceMetadataEntity);
	}

	@Override
	public List<DeviceMetaDataModel> findByUSerId(Long userId) {
		return deviceMetadataRepository.findByUserId(userId)
				.stream()
				.map(deviceMetadataEntity -> DeviceMetaDataModel.builder()
						.userId(deviceMetadataEntity.getUserId())
						.id(deviceMetadataEntity.getId())
						.deviceDetails(deviceMetadataEntity.getDeviceDetails())
						.location(deviceMetadataEntity.getLocation())
						.lastLoggedIn(deviceMetadataEntity.getLastLoggedIn())
						.build())
				.collect(Collectors.toList());
	}
}
