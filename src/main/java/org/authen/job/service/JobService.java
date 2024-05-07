package org.authen.job.service;

import org.authen.wapper.model.GenericResponseWrapper;
import org.springframework.http.ResponseEntity;

public interface JobService {
	ResponseEntity<GenericResponseWrapper> getCreatedJobs();
	Boolean cancelJobWithJobName(String jobName);
}
