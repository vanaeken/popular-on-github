package com.vanaeken.intuit.popular_on_github.service;

import java.util.List;

import com.vanaeken.intuit.popular_on_github.model.RepositoryId;

// experiment on: https://developer.github.com/v4/explorer/

public class GraphQLQuery {

	private String query;

	public GraphQLQuery() {
	}

	public GraphQLQuery(String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public static GraphQLQuery getQueryForStarGazersCount(RepositoryId repositoryId) {
		String query = String.format(
				"query { repository(owner: \"%s\", name: \"%s\") { owner name stargazers { totalCount } } }",
				repositoryId.getRepositoryOwner(), repositoryId.getRepositoryName());
		return new GraphQLQuery(query);
	}

	public static GraphQLQuery getQueryForStarGazersCount(List<RepositoryId> repositoryIds) {
		String fragment = "fragment repository on Repository { name stargazers { totalCount } } ";

		String subQueries = "";

		int iRepo = 0;
		for (RepositoryId repositoryId : repositoryIds) {
			String subQuery = String.format("r%d: repository(owner: \"%s\", name: \"%s\") { ...repository } ", iRepo,
					repositoryId.getRepositoryOwner(), repositoryId.getRepositoryName());
			subQueries += subQuery;
			iRepo++;
		}

		String query = fragment + String.format("query { %s }", subQueries);

		return new GraphQLQuery(query);
	}

}
