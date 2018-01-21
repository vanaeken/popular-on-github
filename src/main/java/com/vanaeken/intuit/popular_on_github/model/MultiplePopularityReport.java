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

	public void consolidate(MultiplePopularityReport otherReport) {
		for (int iRecord = 0; iRecord < this.popularityRecords.size(); iRecord++) {
			this.popularityRecords.get(iRecord).consolidate(otherReport.getPopularityRecords().get(iRecord));
		}
	}

}
