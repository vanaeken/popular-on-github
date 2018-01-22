package com.vanaeken.intuit.popular_on_github.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanaeken.intuit.popular_on_github.model.MultiplePopularityReport;
import com.vanaeken.intuit.popular_on_github.model.MultiplePopularityRequest;
import com.vanaeken.intuit.popular_on_github.model.SinglePopularityReport;
import com.vanaeken.intuit.popular_on_github.model.SinglePopularityRequest;

@Service("consolidatingPopularityCalculator")
public class ConsolidatingPopularityCalculator implements PopularityCalculator {

	@Autowired
	private PopularityCalculator primaryPopularityCalculator;

	@Autowired
	private PopularityCalculator secondaryPopularityCalculator;

	public ConsolidatingPopularityCalculator() {
	}

	public ConsolidatingPopularityCalculator(PopularityCalculator primaryPopularityCalculator,
			PopularityCalculator secondaryPopularityCalculator) {
		this.primaryPopularityCalculator = primaryPopularityCalculator;
		this.secondaryPopularityCalculator = secondaryPopularityCalculator;
	}

	@Override
	public SinglePopularityReport calculatePopularity(SinglePopularityRequest request) {
		SinglePopularityReport report = primaryPopularityCalculator.calculatePopularity(request);

		try {
			SinglePopularityReport secondaryReport = secondaryPopularityCalculator.calculatePopularity(request);
			report.consolidate(secondaryReport);
		} catch (Exception ex) {
			// report this
		}

		return report;
	}

	@Override
	public MultiplePopularityReport calculatePopularity(MultiplePopularityRequest request) {
		MultiplePopularityReport report = primaryPopularityCalculator.calculatePopularity(request);

		try {
			MultiplePopularityReport secondaryReport = secondaryPopularityCalculator.calculatePopularity(request);
			report.consolidate(secondaryReport);
		} catch (Exception ex) {
			// report this
		}

		return report;
	}

}
