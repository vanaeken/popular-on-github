package com.vanaeken.intuit.popular_on_github.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.JsonPath;
import com.vanaeken.intuit.popular_on_github.model.MultiplePopularityReport;
import com.vanaeken.intuit.popular_on_github.model.MultiplePopularityRequest;
import com.vanaeken.intuit.popular_on_github.model.PopularityRecord;
import com.vanaeken.intuit.popular_on_github.model.RepositoryId;
import com.vanaeken.intuit.popular_on_github.model.SinglePopularityReport;
import com.vanaeken.intuit.popular_on_github.model.SinglePopularityRequest;

@Service("popularityCalculator")
public class GitHubPopularityCalculator implements PopularityCalculator {

	private static final String GITHUB_GRAPHQL_URL = "https://api.github.com/graphql";

	private RestTemplate restTemplate;

	public GitHubPopularityCalculator() throws IOException {
		Properties properties = new Properties();
		properties.load(GitHubPopularityCalculator.class.getClassLoader().getResourceAsStream("secrets.properties"));
		String gitHubUsername = properties.getProperty("github_username");
		String gitHubToken = properties.getProperty("github_token");

		this.restTemplate = new RestTemplate();
		BasicAuthorizationInterceptor interceptor = new BasicAuthorizationInterceptor(gitHubUsername, gitHubToken);
		this.restTemplate.getInterceptors().add(interceptor);
	}

	@Override
	public SinglePopularityReport calculatePopularity(SinglePopularityRequest request) {
		GraphQLQuery query = GraphQLQuery.getQueryForStarGazersCount(request.getRespositoryId());

		String responseAsText = restTemplate.postForObject(GITHUB_GRAPHQL_URL, query, String.class);
		String name = JsonPath.parse(responseAsText).read("$['data']['repository']['name']");
		if (!name.equals(request.getRespositoryId().getRepositoryName())) {
			throw new RuntimeException("Unexpected repository name returned by GitHub.");
		}

		int stargazersCount = JsonPath.parse(responseAsText)
				.read("$['data']['repository']['stargazers']['totalCount']");

		PopularityRecord record = new PopularityRecord();
		record.setRepositoryId(request.getRespositoryId());
		record.setPopularity((double) stargazersCount);
		SinglePopularityReport report = new SinglePopularityReport();
		report.setPopularityRecord(record);

		return report;
	}

	@Override
	public MultiplePopularityReport calculatePopularity(MultiplePopularityRequest request) {
		GraphQLQuery query = GraphQLQuery.getQueryForStarGazersCount(request.getRespositoryIds());

		String responseAsText = restTemplate.postForObject(GITHUB_GRAPHQL_URL, query, String.class);

		List<Integer> stargazersCounts = JsonPath.parse(responseAsText)
				.read("$['data'][*]['stargazers']['totalCount']");
		Iterator<Integer> counts = stargazersCounts.iterator();

		List<PopularityRecord> records = new ArrayList<>();
		for (RepositoryId id : request.getRespositoryIds()) {
			PopularityRecord record = new PopularityRecord();
			record.setRepositoryId(id);
			record.setPopularity((double) counts.next());
			records.add(record);
		}

		records.sort(null);

		MultiplePopularityReport report = new MultiplePopularityReport();
		report.setPopularityRecords(records);

		return report;
	}

}
