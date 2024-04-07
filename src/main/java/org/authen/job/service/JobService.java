package org.authen.job.service;

import org.authen.exception.apiEx.ResponseModel;
import org.springframework.http.ResponseEntity;

public interface JobService {
	ResponseEntity<ResponseModel> getCreatedJobs();
	Boolean cancelJobWithJobName(String jobName);
}
