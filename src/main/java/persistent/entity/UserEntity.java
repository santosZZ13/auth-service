package persistent.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@Getter
@Setter
public class UserEntity extends UserBaseEntity implements Serializable {
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
	@Column(name = "created_at")
	private Date createdAt;
	@Column(name = "updated_at")
	private Date updatedAt;
}
