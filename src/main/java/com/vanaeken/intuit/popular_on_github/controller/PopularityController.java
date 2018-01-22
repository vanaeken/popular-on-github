package com.vanaeken.intuit.popular_on_github.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vanaeken.intuit.popular_on_github.model.ExceptionResponse;
import com.vanaeken.intuit.popular_on_github.model.MultiplePopularityReport;
import com.vanaeken.intuit.popular_on_github.model.MultiplePopularityRequest;
import com.vanaeken.intuit.popular_on_github.model.SinglePopularityReport;
import com.vanaeken.intuit.popular_on_github.model.SinglePopularityRequest;
import com.vanaeken.intuit.popular_on_github.service.PopularityCalculator;
import com.vanaeken.intuit.popular_on_github.service.PopularityCalculatorException;

import io.swagger.annotations.ApiOperation;

@Controller
public class PopularityController {

	@Autowired
	private PopularityCalculator consolidatingPopularityCalculator;

	@ApiOperation(value = "Returns the popularity of a GitHub repository")
	@RequestMapping(value = "/singlePopularityReports", method = RequestMethod.POST)
	public @ResponseBody SinglePopularityReport calculate(@RequestBody SinglePopularityRequest request) {

		SinglePopularityReport report = consolidatingPopularityCalculator.calculatePopularity(request);

		return report;
	}

	@ApiOperation(value = "Returns the popularity of a list GitHub repositories", notes = "The returned list is ordered from less popular to more popular")
	@RequestMapping(value = "/multiplePopularityReports", method = RequestMethod.POST)
	public @ResponseBody MultiplePopularityReport calculate(@RequestBody MultiplePopularityRequest request) {

		MultiplePopularityReport report = consolidatingPopularityCalculator.calculatePopularity(request);

		return report;
	}

	@ExceptionHandler(PopularityCalculatorException.class)
	public ResponseEntity<ExceptionResponse> somethingWentWrong(PopularityCalculatorException ex) {
		ExceptionResponse response = new ExceptionResponse(ex.getMessage());

		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
