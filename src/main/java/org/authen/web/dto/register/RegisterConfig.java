package org.authen.web.dto.register;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.*;
import org.authen.level.service.model.UserModel;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterConfig {
	private String username;
	@JsonIgnore
	private String password;
	private String email;
	private String type;
	private String role;
	private String locate;
	@JsonProperty("first_name")
	@JsonPropertyDescription("First name of the user")
	private String firstName;
	@JsonProperty("last_name")
	@JsonPropertyDescription("Last name of the user")
	private String lastName;

	public RegisterConfig(UserModel userModel) {
		this.username = userModel.getUsername();
		this.password = userModel.getPassword();
		this.email = userModel.getEmail();
		this.type = userModel.getType();
		this.role = userModel.getRole();
		this.locate = userModel.getLocate();
		this.firstName = userModel.getFirstName();
		this.lastName = userModel.getLastName();
	}
}
