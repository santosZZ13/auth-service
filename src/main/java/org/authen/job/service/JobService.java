package org.authen.job.service;

import org.authen.wapper.model.GenericResponseSuccessWrapper;
import org.springframework.http.ResponseEntity;

public interface JobService {
	ResponseEntity<GenericResponseSuccessWrapper> getCreatedJobs();
	Boolean cancelJobWithJobName(String jobName);
}
