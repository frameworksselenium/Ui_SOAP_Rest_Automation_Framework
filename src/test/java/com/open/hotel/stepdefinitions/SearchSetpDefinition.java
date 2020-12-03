package com.open.hotel.stepdefinitions;

import com.open.abddf.context.TestContext;
import com.open.abddf.utilities.Data;
import com.open.hotel.pages.Search;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import java.util.HashMap;

public class SearchSetpDefinition {
	
	public Search search;
	public Data data;
	private TestContext context;

	public SearchSetpDefinition(TestContext context){
		this.context = context;
		search = new Search(this.context);
		data = new Data(context);
	}
	@And("user enters the required information in search hotel page")
	public void user_enters_the_required_information_in_search_hotel_page(DataTable dt) throws Throwable {
		HashMap<String, String> val = this.data.convertDataTableValuesToList(dt);
		search.enterRoomSearchInfo(val);
	}

	@And("user clicks the search button")
	public void user_clicks_the_search_button() throws Throwable {
		search.clickOnSearch();
	}
	
}