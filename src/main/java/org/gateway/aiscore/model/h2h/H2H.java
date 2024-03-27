package org.gateway.aiscore.model.h2h;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class H2H {
	private String time;
	private String league;
	private String home;
	private String away;
	private List<String> ht;
	private List<String> ft;
}
