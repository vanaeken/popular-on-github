package com.vanaeken.intuit.popular_on_github.service;

import java.io.IOException;

import org.junit.Test;

import com.vanaeken.intuit.popular_on_github.model.RepositoryId;

public class GooglePopularityCalculatorTests extends PopularityCalculatorTests {

	public GooglePopularityCalculatorTests() throws IOException {
		this.calculator = new GooglePopularityCalculator();
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
}
