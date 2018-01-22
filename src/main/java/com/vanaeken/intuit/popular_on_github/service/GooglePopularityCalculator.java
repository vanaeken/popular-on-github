package com.vanaeken.intuit.popular_on_github.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.JsonPath;
import com.vanaeken.intuit.popular_on_github.model.MultiplePopularityReport;
import com.vanaeken.intuit.popular_on_github.model.MultiplePopularityRequest;
import com.vanaeken.intuit.popular_on_github.model.PopularityRecord;
import com.vanaeken.intuit.popular_on_github.model.RepositoryId;
import com.vanaeken.intuit.popular_on_github.model.SinglePopularityReport;
import com.vanaeken.intuit.popular_on_github.model.SinglePopularityRequest;

@Service("secondaryPopularityCalculator")
public class GooglePopularityCalculator implements PopularityCalculator {

	private static final String GOOGLE_CUSTOM_SEARCH_URL = "https://www.googleapis.com/customsearch/v1?key=%s&cx=%s&q=%s&fields=queries/request/totalResults";

	private RestTemplate restTemplate;

	private String url;

	public GooglePopularityCalculator() throws IOException {
		Properties properties = new Properties();
		properties.load(GooglePopularityCalculator.class.getClassLoader().getResourceAsStream("secrets.properties"));
		String googleSearchEngineId = properties.getProperty("google_search_engine_id");
		String googleKey = properties.getProperty("google_key");

		this.url = String.format(GOOGLE_CUSTOM_SEARCH_URL, googleKey, googleSearchEngineId, "%s");
		this.restTemplate = new RestTemplate();
	}

	@Override
	public SinglePopularityReport calculatePopularity(SinglePopularityRequest request) {
		try {
			double totalResults = getTotalResults(request.getRespositoryId());

			PopularityRecord record = new PopularityRecord(request.getRespositoryId(), totalResults);
			SinglePopularityReport report = new SinglePopularityReport();
			report.setPopularityRecord(record);

			return report;
		} catch (Exception ex) {
			throw new PopularityCalculatorException("Something went wrong.");
		}
	}

	@Override
	public MultiplePopularityReport calculatePopularity(MultiplePopularityRequest request) {
		try {
			List<PopularityRecord> records = new ArrayList<>();

			for (RepositoryId id : request.getRespositoryIds()) {
				double totalResults = getTotalResults(id);

				PopularityRecord record = new PopularityRecord(id, totalResults);

				records.add(record);
			}

			records.sort(null);

			MultiplePopularityReport report = new MultiplePopularityReport();
			report.setPopularityRecords(records);

			return report;
		} catch (Exception ex) {
			throw new PopularityCalculatorException("Something went wrong.");
		}
	}

	private double getTotalResults(RepositoryId id) {
		String queryString = "\"git@github.com:" + id.getOwner() + "/" + id.getName() + ".git\"";
		String finalUrl = String.format(this.url, queryString);

		String responseAsText = restTemplate.getForObject(finalUrl, String.class);

		String totalResultsAsText = JsonPath.parse(responseAsText).read("$['queries']['request'][0]['totalResults']");
		double totalResults = (double) Double.parseDouble(totalResultsAsText);
		return totalResults;
	}
}
