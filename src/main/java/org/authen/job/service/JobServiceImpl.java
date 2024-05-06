package org.authen.job.service;

import org.authen.config.CustomTaskScheduler;
import org.authen.exception.apiEx.GenericResponse;
import org.authen.job.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;

@Service
public class JobServiceImpl implements JobService {

	private final CustomTaskScheduler taskScheduler;

	@Autowired
	public JobServiceImpl(CustomTaskScheduler taskScheduler) {
		this.taskScheduler = taskScheduler;
	}

	@Override
	public ResponseEntity<GenericResponse> getCreatedJobs() {
		Map<Job, ScheduledFuture<?>> scheduledTasks = this.taskScheduler.getScheduledTasks();
		Set<Job> jobs = scheduledTasks.keySet();
		return ResponseEntity
				.ok(GenericResponse.builder()
						.success(Boolean.TRUE)
						.data(jobs).build()
				);
	}

	@Override
	public Boolean cancelJobWithJobName(String jobName) {
		return taskScheduler.cancelJob(jobName);
	}
}
