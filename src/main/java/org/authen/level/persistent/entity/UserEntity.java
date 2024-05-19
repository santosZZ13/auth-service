package org.authen.level.persistent.entity;

import lombok.*;
import org.authen.level.persistent.enums.Provider;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@Entity(name = "user")
@Getter
@Setter

//@Table(name = "users", uniqueConstraints = {
//		@UniqueConstraint(columnNames = "email")
//})
public class UserEntity extends BaseEntity {
	//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "role")
	private String role;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "email")
	private String email;

	@Column(name = "type")
	private String type;

	@Column(name = "locate")
	private String locate;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "provider")
	@Enumerated(EnumType.STRING)
	private Provider provider;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "email_verified")
	private Boolean emailVerified;

	@Column(name = "provider_id")
	private String providerId;

	public UserEntity() {
		super();
		this.enabled = Boolean.FALSE;
		this.provider = Provider.LOCAL;
	}

//	public UserEntity() {
//		this.enabled = false;
//	}

}
