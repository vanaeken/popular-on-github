package com.vanaeken.intuit.popular_on_github.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.vanaeken.intuit.popular_on_github.model.MultiplePopularityReport;
import com.vanaeken.intuit.popular_on_github.model.MultiplePopularityRequest;
import com.vanaeken.intuit.popular_on_github.model.RepositoryId;
import com.vanaeken.intuit.popular_on_github.model.SinglePopularityReport;
import com.vanaeken.intuit.popular_on_github.model.SinglePopularityRequest;

public class GitHubPopularityCalculatorIntegrationTests {

	private GitHubPopularityCalculator calculator;

	public GitHubPopularityCalculatorIntegrationTests() throws IOException {
		this.calculator = new GitHubPopularityCalculator();
	}

	@Test
	public void testSingle1() {
		testSingle("exercism", "python", 286.0);
	}

	@Test
	public void testSingle2() {
		testSingle("bitcoin", "bitcoin", 26000.0);
	}

	private void testSingle(String owner, String name, double expectedPopularity) {
		RepositoryId id = new RepositoryId();
		id.setRepositoryOwner(owner);
		id.setRepositoryName(name);

		SinglePopularityRequest request = new SinglePopularityRequest();
		request.setRespositoryId(id);

		SinglePopularityReport report = this.calculator.calculatePopularity(request);
		assertEquals(name, report.getPopularityRecord().getRepositoryId().getRepositoryName());
		assertEquals(expectedPopularity, report.getPopularityRecord().getPopularity(), expectedPopularity / 10.0);
	}

	@Test
	public void testMultiple() {
		List<RepositoryId> ids = new ArrayList<>();
		RepositoryId id0 = new RepositoryId();
		id0.setRepositoryOwner("exercism");
		id0.setRepositoryName("python");
		ids.add(id0);
		RepositoryId id1 = new RepositoryId();
		id1.setRepositoryOwner("bitcoin");
		id1.setRepositoryName("bitcoin");
		ids.add(id1);

		MultiplePopularityRequest request = new MultiplePopularityRequest();
		request.setRespositoryIds(ids);

		MultiplePopularityReport report = this.calculator.calculatePopularity(request);
		assertEquals("python", report.getPopularityRecords().get(0).getRepositoryId().getRepositoryName());
		assertEquals(286.0, report.getPopularityRecords().get(0).getPopularity(), 286.0 / 10.0);
		assertEquals("bitcoin", report.getPopularityRecords().get(1).getRepositoryId().getRepositoryName());
		assertEquals(25996.0, report.getPopularityRecords().get(1).getPopularity(), 286.0 / 10.0);
	}

	@Test
	public void testMultipleInWrongOrder() {
		List<RepositoryId> ids = new ArrayList<>();
		RepositoryId id0 = new RepositoryId();
		id0.setRepositoryOwner("bitcoin");
		id0.setRepositoryName("bitcoin");
		ids.add(id0);
		RepositoryId id1 = new RepositoryId();
		id1.setRepositoryOwner("exercism");
		id1.setRepositoryName("python");
		ids.add(id1);

		MultiplePopularityRequest request = new MultiplePopularityRequest();
		request.setRespositoryIds(ids);

		MultiplePopularityReport report = this.calculator.calculatePopularity(request);
		assertEquals("python", report.getPopularityRecords().get(0).getRepositoryId().getRepositoryName());
		assertEquals(286.0, report.getPopularityRecords().get(0).getPopularity(), 286.0 / 10.0);
		assertEquals("bitcoin", report.getPopularityRecords().get(1).getRepositoryId().getRepositoryName());
		assertEquals(25996.0, report.getPopularityRecords().get(1).getPopularity(), 286.0 / 10.0);
	}
}
