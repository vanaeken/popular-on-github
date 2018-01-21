package com.vanaeken.intuit.popular_on_github.service;

public class ExpectedResult {

	private String name;
	private double popularity;

	public ExpectedResult() {
	}

	public ExpectedResult(String name, double popularity) {
		this.name = name;
		this.popularity = popularity;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPopularity() {
		return this.popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

}
