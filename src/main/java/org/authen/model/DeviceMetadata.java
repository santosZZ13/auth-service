package org.authen.model;

import lombok.Builder;
import lombok.Data;


import java.util.Date;

@Data
@Builder
public class DeviceMetadata {
	private Long id;
	private Long userId;
	private String deviceDetails;
	private String location;
	private Date lastLoggedIn;
}
