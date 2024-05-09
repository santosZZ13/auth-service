package org.authen.level.persistent.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@Entity(name = "user")
@Getter
@Setter
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

	public UserEntity() {
		super();
		this.enabled = false;
	}

//	public UserEntity() {
//		this.enabled = false;
//	}

}
