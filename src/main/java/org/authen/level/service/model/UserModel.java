package org.authen.level.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.authen.level.persistent.entity.UserEntity;
import org.authen.level.persistent.enums.Provider;

import java.time.LocalDateTime;
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
	// givenName
	private String firstName;
	// familyName
	private String lastName;
	private Provider provider;
	private String imageUrl;
	private Boolean emailVerified;
	private String providerId;

	private String createdBy;
	private String modifiedBy;

	private LocalDateTime createDate;
	private LocalDateTime modifiedDate;

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
		this.provider = userEntity.getProvider();
		this.lastName = userEntity.getLastName();
		this.createdBy = userEntity.getCreatedBy();
		this.modifiedBy = userEntity.getModifiedBy();
		this.createDate = userEntity.getCreatedDate();
		this.modifiedDate = userEntity.getModifiedDate();
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
		userEntity.setProvider(this.provider);
		userEntity.setImageUrl(this.imageUrl);
		userEntity.setEmailVerified(this.emailVerified);
//		userEntity.setCreatedBy(this.createdBy);
//		userEntity.setModifiedBy(this.modifiedBy);
//		userEntity.setCreatedDate(this.createDate);
//		userEntity.setModifiedDate(this.modifiedDate);
		return userEntity;
	}
}
