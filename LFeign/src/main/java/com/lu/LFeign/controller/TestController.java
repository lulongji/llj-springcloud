package com.lu.LFeign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lu.LFeign.service.TestService;

@RestController
public class TestController {

	@Autowired
	TestService testService;

	@GetMapping(value = "/hello")
	public String sayHi(@RequestParam String name) {
		return testService.getTestClient(name);
	}
}
