package org.gateway.aiscore.model.match;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gateway.aiscore.model.h2h.HistoryH2HModel;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchToday {
	private String date;
	private String league;
	private String home;
	private String away;
	private String link;
	private List<HistoryH2HModel> historyBefore;
}
