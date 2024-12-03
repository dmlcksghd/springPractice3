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

//Spring3���� : @Controller + @ResponseBody
@Controller
@ResponseBody
@RequestMapping("/rest")
public class EmpRestController {

	@Autowired
	EmpService empService;

	@GetMapping(value = "/test1.do", produces = "text/plain;charset=utf-8")
	public String f10() {

		return "rest��� ����1";
	}
	
	//7.����
	@DeleteMapping(value = "/empdelete.do/{empid}",
			produces = "text/plain;charset=utf-8"
			)
	public String delete(@PathVariable("empid") int empid) {
		int result = empService.deleteService(empid);
		return result>0?"delete ����":"delete ����";
	}
	
	
	//6.����(put)������ data�� ����, ��û������ body�� �´�.
	@PutMapping(value = "/empupdate.do",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = "text/plain;charset=utf-8"
			)
	public String update(@RequestBody EmpDTO emp) {
		int result = empService.updateService(emp);
		return result>0?"update ����":"update ����";
	}
	
	
	//5.�Է�(Post), ������ data�� ����, ��û������ body�� �´�.(����: @RequestParam�ƴ�)
	@PostMapping(value = "/empinsert.do",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = "text/plain;charset=utf-8"
			)
	
	public String insert(@RequestBody EmpDTO emp) {
		int result = empService.insertService(emp);
		return result>0?"insert ����":"insert ����";
	}
	
	//4.��� ������ ��ȸ�ϰ� return�� Map<������ȣ, ����DTO>
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
	
	//HTTP TUI�� ���� resource�� ǥ���Ѵ�.
	//3. URL�� ���ؼ� ���� data�� ����, return data
	//Ư�� ���� ��ȸ
	@GetMapping(value = "/empdetail.do/{empid}", produces = "application/json")
	public EmpDTO f3(@PathVariable("empid") int empid) {
		return empService.selectByIdService(empid);
	}

	// ������ȸ
	// {"empList":[{}, {}, {}]}
	// 2. Jackson���̺귯���� data�� JASOM���� �����ؼ� return�Ѵ�.
	@GetMapping(value = "/emplist.do", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EmpDTO> f2() {
		return empService.selectAllService();
	}

	// 1.���� data�� ����, return�� data�� �ܼ� ����
	@GetMapping(value = "/test2.do", produces = "text/plain;charset=utf-8")
	public String f1() {
		EmpDTO emp = empService.selectByIdService(100);
		return "rest��� ����2(@RestController)" + emp.getFirst_name();
	}

}
