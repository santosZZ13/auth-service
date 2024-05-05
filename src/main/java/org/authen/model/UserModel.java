package org.authen.model;

import lombok.Builder;
import lombok.Data;
import org.authen.persistence.model.UserEntity;

import javax.persistence.Column;
import java.util.Date;

@Data
@Builder
public class UserModel {
	private Long id;
	private String username;
	private String password;
	private String role;
	private boolean enabled;
	private String email;
	private String type;
	private String locate;
	private String firstName;
	private String lastName;
	private Date createdAt;
	private Date updatedAt;

	public UserModel(UserEntity userEntity) {
		this.id = userEntity.getId();
		this.username = userEntity.getUsername();
		this.password = userEntity.getPassword();
		this.role = userEntity.getRole();
		this.enabled = userEntity.isEnabled();
		this.email = userEntity.getEmail();
		this.type = userEntity.getType();
		this.locate = userEntity.getLocate();
		this.firstName = userEntity.getFirstName();
		this.lastName = userEntity.getLastName();
		this.createdAt = userEntity.getCreatedAt();
		this.updatedAt = userEntity.getUpdatedAt();
	}
}
