package com.lu.EurekeClient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	@Value("${server.port}")
	String port;

	@RequestMapping("/client")
	public String home(@RequestParam(value = "name", defaultValue = "Nick") String name) {
		return "hello world,name=" + name + ",port=" + port;
	}
}
