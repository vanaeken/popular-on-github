package com.vanaeken.intuit.popular_on_github.model;

public class SinglePopularityReport {

	private PopularityRecord popularityRecord;

	public PopularityRecord getPopularityRecord() {
		return popularityRecord;
	}

	public void setPopularityRecord(PopularityRecord popularityRecord) {
		this.popularityRecord = popularityRecord;
	}

	public void consolidate(SinglePopularityReport otherReport) {
		this.popularityRecord.consolidate(otherReport.getPopularityRecord());

	}

}
