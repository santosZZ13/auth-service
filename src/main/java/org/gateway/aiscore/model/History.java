package org.gateway.aiscore.model;

import java.util.Date;

class History {
	private Date date;
	private String League;
	private HomeDetail homeDetail;
	private AwayDetail awayDetail;


	static class HomeDetail {
		private String home;
		private FirstHalf firstHalf;
		private SecondHalf secondHalf;
	}


	static class AwayDetail {
		private String away;
		private FirstHalf firstHalf;
		private SecondHalf secondHalf;
	}

	static class FirstHalf {
		private int corners;
		private int yellowCards;
		private int redCards;
		private int[] goals;
	}

	static class SecondHalf {
		private int corners;
		private int yellowCards;
		private int redCards;
		private int[] goals;
	}
}
