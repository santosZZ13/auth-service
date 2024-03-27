package org.gateway.aiscore.selenium;

import org.gateway.aiscore.model.h2h.H2H;

import java.util.List;
import java.util.Map;

public interface ScheduledAiScoreData {
	Map<String, List<H2H>> getH2HFromAiScore() throws InterruptedException;
}
