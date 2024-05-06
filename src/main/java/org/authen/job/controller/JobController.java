package org.authen.job.controller;

import org.authen.exception.apiEx.GenericResponse;
import org.authen.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// TODO: CRUD operations for Job
@RestController
public class JobController {

	private final JobService jobService;
	private final static String VERSION_1 = "/v1";
	private final static String JOB = "/jobs";

	@Autowired
	public JobController(JobService jobService) {
		this.jobService = jobService;
	}

	@GetMapping(VERSION_1 + JOB)
	public ResponseEntity<GenericResponse> getCreatedJobs() {
		return jobService.getCreatedJobs();
	}

	@GetMapping(VERSION_1 + JOB + "/{jobName}")
	public Boolean cancelJobWithJobName(@PathVariable String jobName) {
		return jobService.cancelJobWithJobName(jobName);
	}
}
