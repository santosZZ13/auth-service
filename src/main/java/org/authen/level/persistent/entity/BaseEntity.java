package org.authen.level.persistent.entity;

import lombok.*;
import lombok.extern.log4j.Log4j2;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@Log4j2
@MappedSuperclass
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public abstract class BaseEntity implements Serializable {

	public static final String CREATOR_NAME = "auth-service";

	@Column(name = "created_by")
	public String createdBy;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	@PrePersist
	void onCreate() {
		log.info("#onCreate - setting created by :: {} and date :: {}", LocalDateTime.now(), CREATOR_NAME);
		this.setCreatedDate(LocalDateTime.now());
		this.setCreatedBy(CREATOR_NAME.toUpperCase());
	}

	@PreUpdate
	void onUpdate() {
		this.setModifiedDate(LocalDateTime.now());
		this.setModifiedBy(CREATOR_NAME.toUpperCase());
	}
}
