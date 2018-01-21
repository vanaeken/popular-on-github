package com.vanaeken.intuit.popular_on_github.model;

import java.util.List;

public class MultiplePopularityReport {

	private List<PopularityRecord> popularityRecords;

	public List<PopularityRecord> getPopularityRecords() {
		return popularityRecords;
	}

	public void setPopularityRecords(List<PopularityRecord> popularityRecords) {
		this.popularityRecords = popularityRecords;
	}

}
