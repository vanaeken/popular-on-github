package com.vanaeken.intuit.popular_on_github;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanaeken.intuit.popular_on_github.model.MultiplePopularityRequest;
import com.vanaeken.intuit.popular_on_github.model.RepositoryId;
import com.vanaeken.intuit.popular_on_github.model.SinglePopularityRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTestsUsingMock {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void testSingleReport() throws Exception {
		RepositoryId id = new RepositoryId("bitcoin", "bitcoin");

		SinglePopularityRequest request = new SinglePopularityRequest();
		request.setRespositoryId(id);
		String json = mapper.writeValueAsString(request);

		mockMvc.perform(post("/singlePopularityReports").contentType(MediaType.APPLICATION_JSON).content(json)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.popularityRecord.repositoryId.name", equalTo("bitcoin")))
				.andExpect(jsonPath("$.popularityRecord.popularity", closeTo(26_000.0, 2_600.0)));
	}

	@Test
	public void testMultipleReportInWrongOrder() throws Exception {
		List<RepositoryId> ids = new ArrayList<>();
		RepositoryId id0 = new RepositoryId("bitcoin", "bitcoin");
		ids.add(id0);
		RepositoryId id1 = new RepositoryId("exercism", "python");
		ids.add(id1);

		MultiplePopularityRequest request = new MultiplePopularityRequest();
		request.setRespositoryIds(ids);

		String json = mapper.writeValueAsString(request);

		mockMvc.perform(post("/multiplePopularityReports").contentType(MediaType.APPLICATION_JSON).content(json)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.popularityRecords[0].repositoryId.name", equalTo("python")))
				.andExpect(jsonPath("$.popularityRecords[0].popularity", closeTo(290.0, 30.0)))
				.andExpect(jsonPath("$.popularityRecords[1].repositoryId.name", equalTo("bitcoin")))
				.andExpect(jsonPath("$.popularityRecords[1].popularity", closeTo(26_000.0, 2_600.0)));
	}

	@Test
	public void testSingleReportForUnknownRepo() throws Exception {
		RepositoryId id = new RepositoryId("unknown_owner", "unknown_name");

		SinglePopularityRequest request = new SinglePopularityRequest();
		request.setRespositoryId(id);
		String json = mapper.writeValueAsString(request);

		mockMvc.perform(post("/singlePopularityReports").contentType(MediaType.APPLICATION_JSON).content(json)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	public void testMultipleReportForUnknownRepo() throws Exception {
		List<RepositoryId> ids = new ArrayList<>();
		RepositoryId id0 = new RepositoryId("unknown_owner", "unknown_name");
		ids.add(id0);
		RepositoryId id1 = new RepositoryId("even_more_unknown_owner", "even_more_unknown_name");
		ids.add(id1);

		MultiplePopularityRequest request = new MultiplePopularityRequest();
		request.setRespositoryIds(ids);

		String json = mapper.writeValueAsString(request);

		mockMvc.perform(post("/multiplePopularityReports").contentType(MediaType.APPLICATION_JSON).content(json)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isInternalServerError());
	}

}
