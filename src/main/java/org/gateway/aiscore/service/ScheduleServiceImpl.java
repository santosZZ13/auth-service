package org.gateway.aiscore.service;

import org.gateway.aiscore.model.AiScoreData;
import org.gateway.aiscore.repository.AiScoreRepository;
import org.gateway.common.configs.schedule.CustomTaskScheduler;
import org.gateway.common.util.JsonConverter;
import org.gateway.common.model.job.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	private final CustomTaskScheduler taskScheduler;
//	private final ScheduledAiScoreData scheduledAiScoreData;
	private final AiScoreRepository aiScoreRepository;
	@Autowired
	public ScheduleServiceImpl(CustomTaskScheduler taskScheduler, AiScoreRepository aiScoreRepository) {
		this.taskScheduler = taskScheduler;
//		this.scheduledAiScoreData = scheduledAiScoreData;
		this.aiScoreRepository = aiScoreRepository;
	}


	@Override
	public void getH2Hs(Job job) {
		CronTrigger cronTrigger = new CronTrigger(job.getCron(), TimeZone.getTimeZone(TimeZone.getDefault().toZoneId()));
		taskScheduler.scheduleWithParams(() -> {
			try {

//				Map<String, List<H2H>> h2HFromAiScore = scheduledAiScoreData.getH2HFromAiScore();
				List<AiScoreData> aiScoreData = JsonConverter.fromJsonToList("./src/main/resources/data.json", AiScoreData.class);
//				List<AiScoreData> aiScoreDataList = new ArrayList<>();

				aiScoreRepository.saveAll(aiScoreData);
//				for (Map.Entry<String, List<H2H>> entry : h2HFromAiScore.entrySet()) {
//					AiScoreData aiScoreData = AiScoreData.builder()
//							.link(entry.getKey())
//							.h2hHistory(entry.getValue())
//							.build();
//					aiScoreDataList.add(aiScoreData);
//				}

//				JsonConverter.convertToJsonAndSave(aiScoreDataList);
//				aiScoreRepository.saveAll(aiScoreDataList);

				System.out.println();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}, cronTrigger, job);
	}

//	private List<MatchToday> convertToHistoryModel(List<AiScoreData> aiScoreDataList) {
//		final String path = "./src/main/resources/data.json";
//		List<AiScoreData> aiScoreData = JsonConverter.fromJsonToList(path, AiScoreData.class);
//		List<MatchToday> matchTodayList = new ArrayList<>();
//
//		for (AiScoreData scoreData : aiScoreDataList) {
//			MatchToday matchToday = new MatchToday();
//			matchToday.setLeague(scoreData.getLeague());
//			matchToday.setDate(scoreData.getDate());
//			matchToday.setHome(scoreData.getHome());
//			matchToday.setAway(scoreData.getAway());
//			matchToday.setLink(scoreData.getLink());
//			matchTodayList.add(matchToday);
//		}
//
//		return matchTodayList;
//	}


//	private HistoryH2HModel getH2HHistoryModel(AiScoreData aiScoreData) {
//
//	}
}
