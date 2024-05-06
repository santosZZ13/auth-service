package persistent.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@Data
@SuperBuilder
@AllArgsConstructor
@MappedSuperclass
public class UserBaseEntity {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String username;

	private String password;

	private String role;

	@Column(name = "enabled")
	private boolean enabled;

	public UserBaseEntity() {
		this.enabled = false;
	}
}
