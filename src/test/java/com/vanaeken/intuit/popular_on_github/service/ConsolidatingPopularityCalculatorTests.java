package com.vanaeken.intuit.popular_on_github.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.vanaeken.intuit.popular_on_github.model.MultiplePopularityRequest;
import com.vanaeken.intuit.popular_on_github.model.RepositoryId;
import com.vanaeken.intuit.popular_on_github.model.SinglePopularityRequest;

public class ConsolidatingPopularityCalculatorTests extends PopularityCalculatorTests {

	@Before
	public void initialize() throws IOException {
		PopularityCalculator primaryPopularityCalculator = new GitHubPopularityCalculator();
		PopularityCalculator secondaryPopularityCalculator = new GooglePopularityCalculator();
		this.calculator = new ConsolidatingPopularityCalculator(primaryPopularityCalculator,
				secondaryPopularityCalculator);
	}

	@Test
	public void testSingle1() {
		testSingle("exercism", "python", 0.0);
	}

	@Test
	public void testSingle2() {
		testSingle("bitcoin", "bitcoin", 9.0);
	}

	@Test
	public void testMultipleInRightOrder() {
		RepositoryId[] ids = { new RepositoryId("exercism", "python"), new RepositoryId("bitcoin", "bitcoin") };
		ExpectedResult[] expectedResults = { new ExpectedResult("python", 0.0), new ExpectedResult("bitcoin", 9.0) };

		testMultiple(ids, expectedResults);
	}

	@Test
	public void testMultipleInWrongOrder() {
		RepositoryId[] ids = { new RepositoryId("bitcoin", "bitcoin"), new RepositoryId("exercism", "python") };
		ExpectedResult[] expectedResults = { new ExpectedResult("python", 0.0), new ExpectedResult("bitcoin", 9.0) };

		testMultiple(ids, expectedResults);
	}

	@Test
	public void testSingleWithSecondaryFailure() throws IOException {
		PopularityCalculator primaryPopularityCalculator = new GitHubPopularityCalculator();

		PopularityCalculator secondaryPopularityCalculator = mock(GooglePopularityCalculator.class);
		when(secondaryPopularityCalculator.calculatePopularity(any(SinglePopularityRequest.class)))
				.thenThrow(new PopularityCalculatorException("ouch!"));

		this.calculator = new ConsolidatingPopularityCalculator(primaryPopularityCalculator,
				secondaryPopularityCalculator);

		testSingle("exercism", "python", 0.0);
	}

	@Test
	public void testMultipleWithSecondaryFailure() throws IOException {
		PopularityCalculator primaryPopularityCalculator = new GitHubPopularityCalculator();

		PopularityCalculator secondaryPopularityCalculator = mock(GooglePopularityCalculator.class);
		when(secondaryPopularityCalculator.calculatePopularity(any(MultiplePopularityRequest.class)))
				.thenThrow(new PopularityCalculatorException("ouch!"));

		this.calculator = new ConsolidatingPopularityCalculator(primaryPopularityCalculator,
				secondaryPopularityCalculator);

		RepositoryId[] ids = { new RepositoryId("bitcoin", "bitcoin"), new RepositoryId("exercism", "python") };
		ExpectedResult[] expectedResults = { new ExpectedResult("python", 0.0), new ExpectedResult("bitcoin", 9.0) };

		testMultiple(ids, expectedResults);
	}

}
