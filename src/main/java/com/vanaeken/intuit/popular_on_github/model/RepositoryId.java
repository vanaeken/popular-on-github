package com.vanaeken.intuit.popular_on_github.model;

public class RepositoryId {

	private String owner;
	private String name;

	public RepositoryId() {
	}

	public RepositoryId(String owner, String name) {
		super();
		this.owner = owner;
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
