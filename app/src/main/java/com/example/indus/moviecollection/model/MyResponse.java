package com.example.indus.moviecollection.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MyResponse {

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<TheMovie> results;

	@SerializedName("total_results")
	private int totalResults;

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setResults(List<TheMovie> results){
		this.results = results;
	}

	public List<TheMovie> getResults(){
		return results;
	}

	public void setTotalResults(int totalResults){
		this.totalResults = totalResults;
	}

	public int getTotalResults(){
		return totalResults;
	}

	@Override
 	public String toString(){
		return
			"MyResponse{" +
			"page = '" + page + '\'' +
			",total_pages = '" + totalPages + '\'' +
			",results = '" + results + '\'' +
			",total_results = '" + totalResults + '\'' +
			"}";
		}
}