package org.authen.level.persistent.repository;

import org.authen.level.persistent.entity.DeviceMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceMetadataRepository extends JpaRepository<DeviceMetadataEntity, Long> {
	List<DeviceMetadataEntity> findByUserId(Long userId);
}
