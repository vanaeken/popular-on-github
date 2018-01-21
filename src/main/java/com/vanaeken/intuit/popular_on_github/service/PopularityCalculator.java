package com.vanaeken.intuit.popular_on_github.service;

import com.vanaeken.intuit.popular_on_github.model.MultiplePopularityReport;
import com.vanaeken.intuit.popular_on_github.model.MultiplePopularityRequest;
import com.vanaeken.intuit.popular_on_github.model.SinglePopularityReport;
import com.vanaeken.intuit.popular_on_github.model.SinglePopularityRequest;

public interface PopularityCalculator {

	public SinglePopularityReport calculatePopularity(SinglePopularityRequest request);

	public MultiplePopularityReport calculatePopularity(MultiplePopularityRequest request);
}
