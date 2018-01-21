package com.vanaeken.intuit.popular_on_github;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.vanaeken.intuit.popular_on_github.model.PopularityRecord;
import com.vanaeken.intuit.popular_on_github.model.RepositoryId;
import com.vanaeken.intuit.popular_on_github.model.SinglePopularityReport;
import com.vanaeken.intuit.popular_on_github.model.SinglePopularityRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Test
	public void shouldGetReportBack() throws Exception {
		String url = "http://localhost:" + port + "/singlePopularityReports";

		RepositoryId id = new RepositoryId();
		id.setOwner("bitcoin");
		id.setName("bitcoin");

		SinglePopularityRequest request = new SinglePopularityRequest();
		request.setRespositoryId(id);

		SinglePopularityReport report = restTemplate.postForObject(url, request, SinglePopularityReport.class);

		PopularityRecord record = report.getPopularityRecord();

		assertNotNull(record);
	}
}
