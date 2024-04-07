package org.authen.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
public class UserEntity extends UserBaseEntity {
	@Column(name = "created_at")
	private Date createdAt;
	@Column(name = "updated_at")
	private Date updatedAt;
}
