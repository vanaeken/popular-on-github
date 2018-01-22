package com.vanaeken.intuit.popular_on_github.service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import com.vanaeken.intuit.popular_on_github.model.MultiplePopularityReport;
import com.vanaeken.intuit.popular_on_github.model.MultiplePopularityRequest;
import com.vanaeken.intuit.popular_on_github.model.RepositoryId;
import com.vanaeken.intuit.popular_on_github.model.SinglePopularityReport;
import com.vanaeken.intuit.popular_on_github.model.SinglePopularityRequest;

public abstract class PopularityCalculatorTests {

	private static final double TOLERANCE = 100000.0;

	protected PopularityCalculator calculator;

	protected void testSingle(String owner, String name, double expectedPopularity) {
		RepositoryId id = new RepositoryId();
		id.setOwner(owner);
		id.setName(name);

		SinglePopularityRequest request = new SinglePopularityRequest();
		request.setRespositoryId(id);

		SinglePopularityReport report = this.calculator.calculatePopularity(request);
		assertEquals(name, report.getPopularityRecord().getRepositoryId().getName());
		assertEquals(expectedPopularity, report.getPopularityRecord().getPopularity(), TOLERANCE);
	}

	protected void testMultiple(RepositoryId[] ids, ExpectedResult[] expectedResults) {
		MultiplePopularityRequest request = new MultiplePopularityRequest();
		request.setRespositoryIds(Arrays.asList(ids));

		MultiplePopularityReport report = this.calculator.calculatePopularity(request);

		for (int iRepo = 0; iRepo < expectedResults.length; iRepo++) {
			assertEquals(expectedResults[iRepo].getName(),
					report.getPopularityRecords().get(iRepo).getRepositoryId().getName());
			assertEquals(expectedResults[iRepo].getPopularity(),
					report.getPopularityRecords().get(iRepo).getPopularity(), TOLERANCE);
		}
	}
}
