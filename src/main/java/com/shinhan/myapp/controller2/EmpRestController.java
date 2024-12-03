package com.shinhan.myapp.controller2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinhan.myapp.emp.EmpDTO;
import com.shinhan.myapp.emp.EmpService;

import oracle.jdbc.proxy.annotation.Post;

//Spring3버젼 : @Controller + @ResponseBody
@Controller
@ResponseBody
@RequestMapping("/rest")
public class EmpRestController {

	@Autowired
	EmpService empService;

	@GetMapping(value = "/test1.do", produces = "text/plain;charset=utf-8")
	public String f10() {

		return "rest방식 연습1";
	}
	
	//7.삭제
	@DeleteMapping(value = "/empdelete.do/{empid}",
			produces = "text/plain;charset=utf-8"
			)
	public String delete(@PathVariable("empid") int empid) {
		int result = empService.deleteService(empid);
		return result>0?"delete 성공":"delete 실패";
	}
	
	
	//6.수정(put)들어오는 data가 있음, 요청문서의 body로 온다.
	@PutMapping(value = "/empupdate.do",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = "text/plain;charset=utf-8"
			)
	public String update(@RequestBody EmpDTO emp) {
		int result = empService.updateService(emp);
		return result>0?"update 성공":"update 실패";
	}
	
	
	//5.입력(Post), 들어오는 data가 있음, 요청문서의 body로 온다.(주의: @RequestParam아님)
	@PostMapping(value = "/empinsert.do",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = "text/plain;charset=utf-8"
			)
	
	public String insert(@RequestBody EmpDTO emp) {
		int result = empService.insertService(emp);
		return result>0?"insert 성공":"insert 실패";
	}
	
	//4.모든 직원을 조회하고 return은 Map<직원번호, 직원DTO>
	//{100:{}, 101:{}, 102:{} }
	@GetMapping(value = "/empmap.do", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<Integer, EmpDTO> f4() {
		Map<Integer, EmpDTO> map = new HashMap<>();
		List<EmpDTO> emplist = empService.selectAllService();
		emplist.forEach(emp -> {
			map.put(emp.getEmployee_id(), emp);
		});
		return map;
	}
	
	//HTTP TUI를 통해 resource를 표현한다.
	//3. URL을 통해서 들어온 data는 있음, return data
	//특정 직원 조회
	@GetMapping(value = "/empdetail.do/{empid}", produces = "application/json")
	public EmpDTO f3(@PathVariable("empid") int empid) {
		return empService.selectByIdService(empid);
	}

	// 직원조회
	// {"empList":[{}, {}, {}]}
	// 2. Jackson라이브러리가 data를 JASOM으로 변경해서 return한다.
	@GetMapping(value = "/emplist.do", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EmpDTO> f2() {
		return empService.selectAllService();
	}

	// 1.들어온 data는 없음, return된 data는 단순 문자
	@GetMapping(value = "/test2.do", produces = "text/plain;charset=utf-8")
	public String f1() {
		EmpDTO emp = empService.selectByIdService(100);
		return "rest방식 연습2(@RestController)" + emp.getFirst_name();
	}

}
