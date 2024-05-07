package org.authen.level.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceMetaDataModel {
	private Long id;
	private Long userId;
	private String deviceDetails;
	private String location;
	private Date lastLoggedIn;
}
