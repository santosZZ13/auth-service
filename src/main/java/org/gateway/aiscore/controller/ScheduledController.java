package org.gateway.aiscore.controller;

import org.gateway.aiscore.service.ScheduleService;
import org.gateway.common.model.job.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ScheduledController {
	private final ScheduleService scheduleService;

	@Autowired
	public ScheduledController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@PostMapping("/schedules")
	public void getSchedules(@RequestBody Job job)  {
		scheduleService.getH2Hs(job);
	}
}
