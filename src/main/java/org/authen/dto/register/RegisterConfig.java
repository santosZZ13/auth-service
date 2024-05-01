package org.authen.dto.register;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
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
}
