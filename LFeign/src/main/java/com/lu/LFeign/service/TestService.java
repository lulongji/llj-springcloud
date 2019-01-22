package com.lu.LFeign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lu.LFeign.service.hystric.TestServiceHystric;

@FeignClient(value = "eureke-client", fallback = TestServiceHystric.class)
public interface TestService {

	@RequestMapping(value = "/test/client", method = RequestMethod.GET)
	String getTestClient(@RequestParam(value = "name") String name);

}
