package org.authen.web.dto.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.authen.level.service.model.UserModel;
import org.authen.validation.ValidField;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class RegisterDTORequest {

	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String EMAIL = "email";
	public static final String TYPE = "type";
	public static final String ROLE = "role";
	public static final String LOCATE = "locate";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";

	@Valid
	private RegisterForm registerForm;

	public RegisterDTORequest() {
		this.registerForm = new RegisterForm();
	}

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Valid
	public static class RegisterForm {

		@ValidField(message = "Mandatory field {%s} is not specified", code = "C1010003")
		private String username;
		@ValidField(message = "Mandatory field {%s} is not specified", code = "C1010003")
		private String password;
		//	private String passwordConfirm;
		@ValidField(message = "Mandatory field {%s} is not specified", code = "C1010003")
		private String email;
		@ValidField(message = "Mandatory field {%s} is not specified", code = "C1010003")
		private String type;
		@ValidField(message = "Mandatory field {%s} is not specified", code = "C1010003")
		private String role;
		@ValidField(message = "Mandatory field {%s} is not specified", code = "C1010003")
		private String locate;
		@ValidField(message = "Mandatory field {%s} is not specified", code = "C1010003")
		private String firstName;
		@ValidField(message = "Mandatory field {%s} is not specified", code = "C1010003")
		private String lastName;
//		@JsonIgnore
//		public String getPassword() {
//			return password;
//		}
	}

	public Map<String, String> toMapFromRegisterForm() {
		Map<String, String> map = new LinkedHashMap<>();
		map.put(USERNAME, this.registerForm.getUsername());
		map.put(PASSWORD, this.registerForm.getPassword());
		map.put(EMAIL, this.registerForm.getEmail());
		map.put(TYPE, this.registerForm.getType());
		map.put(ROLE, this.registerForm.getRole());
		map.put(LOCATE, this.registerForm.getLocate());
		map.put(FIRST_NAME, this.registerForm.getFirstName());
		map.put(LAST_NAME, this.registerForm.getLastName());
		return map;
	}

	public UserModel toUserEntityWithHashPasswordToSaveInDB(final String hashedPassword) {
		return UserModel.builder()
				.username(this.registerForm.getUsername())
				.password(hashedPassword)
				.email(this.registerForm.getEmail())
				.type(this.registerForm.getType())
				.role(this.registerForm.getRole())
				.locate(this.registerForm.getLocate())
				.firstName(this.registerForm.getFirstName())
				.lastName(this.registerForm.getLastName())
				.build();
	}
}
