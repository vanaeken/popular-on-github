package com.vanaeken.intuit.popular_on_github.model;

public class PopularityRecord implements Comparable<PopularityRecord> {

	private RepositoryId repositoryId;
	private double popularity;

	@Override
	public int compareTo(PopularityRecord other) {
		return Double.compare(this.popularity, other.popularity);
	}

	public RepositoryId getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(RepositoryId repositoryId) {
		this.repositoryId = repositoryId;
	}

	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

}
