package com.lu.LFeign.service.hystric;

import org.springframework.stereotype.Component;

import com.lu.LFeign.service.TestService;

@Component
public class TestServiceHystric implements TestService {

	@Override
	public String getTestClient(String name) {
		return "sorry " + name;
	}

}
