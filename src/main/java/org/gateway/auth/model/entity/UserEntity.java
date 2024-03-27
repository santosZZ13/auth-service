package org.gateway.auth.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;
import java.util.Objects;

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
