package org.gateway.aiscore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gateway.aiscore.model.h2h.H2H;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AiScoreData {
	private String league;
	private String date;
	private String home;
	private String away;
	private String link;
	private List<H2H> h2hHistory;
}
