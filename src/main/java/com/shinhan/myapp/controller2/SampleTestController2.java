package com.shinhan.myapp.controller2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//Spring3버젼 : @Controller + @ResponseBody
@RestController
@RequestMapping("/rest")
public class SampleTestController2 {

	@GetMapping(value = "/test2.do", produces = "text/plain;charset=utf-8")
	public String f1() {
		
		
		
		return "rest방식 연습2(@RestController)";
	}
	// {"": {"":[]}}
}
