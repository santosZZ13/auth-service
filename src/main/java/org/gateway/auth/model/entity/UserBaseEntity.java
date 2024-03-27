package org.gateway.auth.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class UserBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	private String username;

	private String password;

	private String role;

}
