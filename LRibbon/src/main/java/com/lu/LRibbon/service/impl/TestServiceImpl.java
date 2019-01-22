package com.lu.LRibbon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lu.LRibbon.service.TestService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "error")
	@Override
	public String getTest(String name) throws Exception {
		return restTemplate.getForObject("http://localhost:2222/EurekeClient/test/client?name=" + name, String.class);
	}

	public String error(String name) {
		return "hi," + name + ",sorry,error!";
	}
}
