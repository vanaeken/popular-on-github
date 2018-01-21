package com.vanaeken.intuit.popular_on_github.service;

import java.io.IOException;

import org.junit.Test;

import com.vanaeken.intuit.popular_on_github.model.RepositoryId;

public class GitHubPopularityCalculatorTests extends PopularityCalculatorTests {

	public GitHubPopularityCalculatorTests() throws IOException {
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

	@Test
	public void testMultipleInRightOrder() {
		RepositoryId[] ids = { new RepositoryId("exercism", "python"), new RepositoryId("bitcoin", "bitcoin") };
		ExpectedResult[] expectedResults = { new ExpectedResult("python", 286.0),
				new ExpectedResult("bitcoin", 25996.0) };

		testMultiple(ids, expectedResults);
	}

	@Test
	public void testMultipleInWrongOrder() {
		RepositoryId[] ids = { new RepositoryId("bitcoin", "bitcoin"), new RepositoryId("exercism", "python") };
		ExpectedResult[] expectedResults = { new ExpectedResult("python", 286.0),
				new ExpectedResult("bitcoin", 25996.0) };

		testMultiple(ids, expectedResults);
	}

}
