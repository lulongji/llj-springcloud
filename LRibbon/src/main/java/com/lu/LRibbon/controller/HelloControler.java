package com.lu.LRibbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lu.LRibbon.service.TestService;

@RestController
public class HelloControler {

	@Autowired
	TestService testService;

	@GetMapping(value = "/hello")
	public String hi(@RequestParam String name) {
		String result = null;
		try {
			result = testService.getTest(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
