package org.authen.level.persistent.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "password_reset_token")
@Getter
@Setter
public class PasswordResetTokenEntity {
	private static final int EXPIRATION = 60 * 24;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String token;

	@OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private UserEntity user;

	private Date expiryDate;
}
