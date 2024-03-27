package org.gateway.aiscore.model.h2h;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryH2HModel {
	private Date date;
	//		private String home;
//		private String away;
//		private List<String> htCorners;
//		private List<String> ftCorners;
//		private List<String> htYellowCards;
//		private List<String> ftYellowCards;
//		private List<String> htRedCards;
//		private List<String> ftRedCards;
	private List<String> htGoals;
	private List<String> ftGoals;
	private String score;
	private String link;
}
