package org.gateway.aiscore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {
	private Date date;
	private String league;
	private String home;
	private String away;
	private String time;
	private Date createdAt;
	private Date updatedAt;
	private List<History> history;
}
