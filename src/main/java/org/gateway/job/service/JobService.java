package org.gateway.job.service;

import org.gateway.common.exception.apiEx.ResponseModel;
import org.springframework.http.ResponseEntity;

public interface JobService {
	ResponseEntity<ResponseModel> getCreatedJobs();
	Boolean cancelJobWithJobName(String jobName);
}
