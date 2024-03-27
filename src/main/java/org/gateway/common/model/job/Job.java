package org.gateway.common.model.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Job {
	private String jobName;
//	private String apiURL;
//	private String baseURL;
	private String cron;
}
