package com.shinhan.myapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//여러가지 형태의 요청 학습 

@Controller
public class SampleController3 {
	
	Logger logger = LoggerFactory.getLogger(SampleController3.class);
	
	//요청의주소같고 넘어오는 파라메터도 확인(userid라는 파라메터의 값은 꼭 같아야한다. userpw존재,email존재 안함 
	@RequestMapping(value = "/second4.do", 
			params = {"userid=user1","userpw","!email" }  )
	public String f3(String userid,String userpw) {
		logger.info("second4 아이디는 " + userid );
		logger.info("second4 비밀번호는 " + userpw );
	
		return "jsptest/second3";
	}
	
	@RequestMapping(value = { "/second3.do"}, method = RequestMethod.POST)
	public String f2(@RequestParam("userid") String userid2, 
			         @RequestParam("userpw") int userpw) {
		logger.info("아이디는 " + userid2 );
		logger.info("비밀번호는 " + userpw );
		return "jsptest/second3";
	}
	
	
	

	@RequestMapping(value = { "/second1.do", "/second2.do" }, method = RequestMethod.GET)
	public String f1() {
		return "jsptest/first1";
	}

}
