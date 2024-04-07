package org.authen.config;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.authen.job.model.Job;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.*;
import java.util.concurrent.ScheduledFuture;

@Getter
@Log4j2
public class CustomTaskScheduler extends ThreadPoolTaskScheduler {

	private final static Integer POOL_SIZE = 10;
	private final static String THREAD_NAME_PREFIX = "CustomTaskScheduler-";
	private final Map<Job, ScheduledFuture<?>> scheduledTasks = new IdentityHashMap<>();

	public CustomTaskScheduler() {
		super();
		this.setPoolSize(POOL_SIZE);
		this.setThreadNamePrefix(THREAD_NAME_PREFIX);
		this.initialize();
	}

	public void scheduleWithParams(Runnable task, Object type, @NotNull Job job) throws Exception {

		Set<String> jobNames = getJobNames();

		if (jobNames.contains(job.getJobName())) {
			throw new Exception("Job with name " + job.getJobName() + " already exists");
		}

		ScheduledFuture<?> schedule = null;
		if (Objects.requireNonNull(type) instanceof Trigger) {
			schedule = super.schedule(task, (Trigger) type);
		}
		this.scheduledTasks.put(job, schedule);
	}

	public @NotNull Boolean cancelJob(String jobName) {
		Set<Job> jobs = this.getJobs();
		for (Job job : jobs) {
			if (Objects.equals(job.getJobName(), jobName)) {
				ScheduledFuture<?> scheduledFuture = this.scheduledTasks.get(job);
				if (scheduledFuture != null) {
					log.info("Cancelled job with name: " + jobName);
					return scheduledFuture.cancel(true);
				}
			}
		}
		return false;
	}


	public Set<Job> getJobs() {
		return this.scheduledTasks.keySet();
	}

	public Set<String> getJobNames() {
		Set<Job> jobs = this.getJobs();
		Set<String> jobNames = new HashSet<>();
		for (Job job : jobs) {
			jobNames.add(job.getJobName());
		}
		return jobNames;
	}
}
