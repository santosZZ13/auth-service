package org.authen.testapi.model_test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.*;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(value =  { "password", "passwordConfirm" }, allowSetters = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Data
@RedisHash
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	private String id;
	private String name;
//	@Indexed
	private String email;
	private String password;
	private String passwordConfirm;
	@Reference
	private Set<Role> roles = new HashSet<Role>();

	public void addRole(Role role) {
		roles.add(role);
	}
}