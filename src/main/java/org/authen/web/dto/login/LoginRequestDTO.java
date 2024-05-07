package org.authen.web.dto.login;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDTO {

	@JsonPropertyDescription("Login form")
	private LoginForm loginForm;


	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class LoginForm {
		private String username;
		private String password;
	}
}
