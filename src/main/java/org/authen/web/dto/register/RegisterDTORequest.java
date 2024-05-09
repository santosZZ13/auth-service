package org.authen.web.dto.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.authen.level.service.model.UserModel;
import org.authen.validation.*;

import javax.validation.Valid;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RegisterDTORequest {

	@Valid
	private RegisterForm registerForm;

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Valid
	public static class RegisterForm {

		@ValidField(message = "Mandatory field {%s} is not specified", code = "C1010003")
		@ValidUserName(message = "Invalid value specified for parameter {%s}.", code = "C1010004")
		private String username;

		@ValidField(message = "Mandatory field {%s} is not specified", code = "C1010003")
		private String password;
		//	private String passwordConfirm;

		@ValidField(message = "Mandatory field {%s} is not specified", code = "C1010003")
		@ValidEmail(message = "Invalid value specified for parameter {%s}.", code = "C1010004")
		private String email;

		@ValidField(message = "Mandatory field {%s} is not specified", code = "C1010003")
		private String type;

		@ValidField(message = "Mandatory field {%s} is not specified", code = "C1010003")
		@ValidRole(message = "Invalid value specified for parameter {%s}.", code = "C1010004")
		private String role;

		@ValidField(message = "Mandatory field {%s} is not specified", code = "C1010003")
		private String locate;

		@ValidField(message = "Mandatory field {%s} is not specified", code = "C1010003")
		@ValidNormalField(message = "Invalid value specified for parameter {%s}.", code = "C1010004")
		private String firstName;

		@ValidField(message = "Mandatory field {%s} is not specified", code = "C1010003")
		@ValidNormalField(message = "Invalid value specified for parameter {%s}.", code = "C1010004")
		private String lastName;
//		@JsonIgnore
//		public String getPassword() {
//			return password;
//		}
	}


	public UserModel toUserModelWithHashPasswordToSaveInDB(final String hashedPassword) {
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
