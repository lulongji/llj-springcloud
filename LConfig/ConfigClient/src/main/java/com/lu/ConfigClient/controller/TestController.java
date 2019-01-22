package com.lu.ConfigClient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Value("${test}")
	String test;

	@RequestMapping(value = "/hello")
	public String hello() {
		return test;
	}

}
