package com.vanaeken.intuit.popular_on_github.model;

import java.util.List;

public class MultiplePopularityRequest {

	private List<RepositoryId> respositoryIds;

	public List<RepositoryId> getRespositoryIds() {
		return respositoryIds;
	}

	public void setRespositoryIds(List<RepositoryId> respositoryIds) {
		this.respositoryIds = respositoryIds;
	}

}
