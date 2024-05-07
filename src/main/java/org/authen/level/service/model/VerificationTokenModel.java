package org.authen.level.service.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class VerificationTokenModel {
	private Long id;
	private String token;
	private UserModel userModel;
	private Date expiryDate;
}
