package org.authen.level.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.authen.level.persistent.entity.UserEntity;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

	public UserEntity toUserEntity() {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(this.id);
		userEntity.setUsername(this.username);
		userEntity.setPassword(this.password);
		userEntity.setRole(this.role);
		userEntity.setEnabled(this.enabled);
		userEntity.setEmail(this.email);
		userEntity.setType(this.type);
		userEntity.setLocate(this.locate);
		userEntity.setFirstName(this.firstName);
		userEntity.setLastName(this.lastName);
		userEntity.setCreatedAt(this.createdAt);
		userEntity.setUpdatedAt(this.updatedAt);
		return userEntity;
	}
}
