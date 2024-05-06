package org.authen.job.service;

import org.authen.exception.apiEx.GenericResponse;
import org.springframework.http.ResponseEntity;

public interface JobService {
	ResponseEntity<GenericResponse> getCreatedJobs();
	Boolean cancelJobWithJobName(String jobName);
}
