package org.gateway.auth.model.dto.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDTO {

	private String username;

	private String password;

	private String role;

//	private String email;

//	private String message;
}
