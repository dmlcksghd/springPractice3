package com.shinhan.myapp.controller2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.myapp.emp.EmpDTO;
import com.shinhan.myapp.emp.EmpService;

//Spring3버젼 : @Controller + @ResponseBody
@RestController
@RequestMapping("/rest")
public class EmpRestController1 {

	@Autowired
	EmpService empService;
	
	// 직원조회
	//{"empList":[{}, {}, {}]}
	//Jackson라이브러리가 data를 JASOM으로 변경해서 return한다.
	@GetMapping(value = "/empList.do", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EmpDTO> f11() {
		return empService.selectAllService();
	}
	
	
}
