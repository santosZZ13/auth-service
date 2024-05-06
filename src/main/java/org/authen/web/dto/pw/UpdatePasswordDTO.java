package org.authen.web.dto.pw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePasswordDTO {

	private UpdatePasswordConfig config;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class UpdatePasswordConfig {
		private String oldPassword;
		private String newPassword;
		private String confirmPassword;
	}
}
