package com.shinhan.myapp.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.shinhan.myapp.emp.EmpDTO;
import com.shinhan.myapp.emp.EmpService;
import com.shinhan.myapp.model.AccountService;
import com.shinhan.myapp.model.DeptService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/*
 * @Autowired 
 * EmpService empService;
 * @Autowired
 * DeptService deptService;
 * -------------------------------대신---------------
 * @RequiredArgsConstructor //final인 field들에@Autowired  
 * final EmpService empService; //final 생성시할당, 선언시할당,변경불가 
 * final DeptService deptService;
 */

@Slf4j
@Controller
@RequestMapping("/emp")
@RequiredArgsConstructor // final + @Autowired
public class EmpController {

	final EmpService empService;
	final DeptService deptService;
	final AccountService accService;
	
	@ResponseBody	//응답 문서를 만들어서 데이터를 보낼때 사용(즉, page가 없음 == response.getWriter.append(""))
	@GetMapping(value = "/transfer.do", produces = "text/plain;charset=utf-8")
	public String transfer() {
		accService.transferService();
		return "이체 서비스 완료";
	}

	@GetMapping("/listByJobJoin.do")
	public String listByJobJoin(String job, Model model) {
		model.addAttribute("empDatas", empService.selectByJobJoin(job));
		return "emp/empListTable2";
	}
	
	@GetMapping("/listByJobJoin2.do")
	public String listByJobJoin2(String job, Model model) {
		model.addAttribute("empDatas", empService.selectByJobJoin3(job));
		return "emp/empListTable3";
	}
	
	@GetMapping("/listByDept.do")
	public String listByDept(int dept_id, Model model) {
		model.addAttribute("empDatas", empService.selectByDept(dept_id));
		return "emp/empListTable";
	}
	
	@GetMapping("/listByJob.do")
	public String listByJob(String job, Model model) {
		model.addAttribute("empDatas", empService.selectByJob(job));
		return "emp/empListTable";
	}
	
	@GetMapping("/listBySalary.do")
	public String listBySalary(double salary, Model model) {
		model.addAttribute("empDatas", empService.selectBySalary(salary));
		return "emp/empListTable";
	}
	
	@GetMapping("/list.do")
	public String selectAll(Model model, HttpServletRequest request) {
		
		Map<String, ?>  map = RequestContextUtils.getInputFlashMap(request);
		if(map!=null) {
			model.addAttribute("resultMessage", 
					map.get("resultMessage"));
		}
		
		
		model.addAttribute("joblist", empService.selectAllJobService());
		model.addAttribute("deptlist", deptService.selectAllService());
		return "emp/empList"; /// WEB-INF/views/emp/empList.jsp
	}

	@GetMapping("/list2.do")
	public String selectCondition(Model model, @RequestParam Map<String, Object> map) {
		log.info(map.toString());
		String chk = (String) map.get("chk");
		if (chk.equals("true"))
			map.put("hdate", "1900-01-01");
		List<EmpDTO> emplist = empService.selectByCondition(map);
		model.addAttribute("empDatas", emplist);

		return "emp/empListTable";
	}

	@GetMapping("insert.do")
	public String insertGet(Model model) {

		model.addAttribute("joblist", empService.selectAllJobService());
		model.addAttribute("deptlist", deptService.selectAllService());
		model.addAttribute("managerlist", empService.selectAllService());

		return "emp/empInsert";
	}

	@PostMapping("/insert.do")
	public String insertPost(EmpDTO emp, RedirectAttributes attr) {
		int result = empService.insertService(emp);
		attr.addFlashAttribute("resultMessage", result>0?"입력성공":"입력실패");
		//return "redirect:list.do";
		return "redirect:/emp/list.do";
	}

	@GetMapping("/detail.do")
	public String detailGet(int empid, Model model) {
		model.addAttribute("joblist", empService.selectAllJobService());
		model.addAttribute("deptlist", deptService.selectAllService());
		model.addAttribute("managerlist", empService.selectAllService());

		model.addAttribute("empInfo", empService.selectByIdService(empid));
		return "emp/empDetail";
	}

	@PostMapping("/detail.do")
	public String detailPost(EmpDTO emp, RedirectAttributes attr) {
		int result = empService.updateService(emp);
		attr.addFlashAttribute("resultMessage", result>0?"수정성공":"수정실패");
		return "redirect:list.do";
	}
	
	@RequestMapping(value="/delete.do",
			method={RequestMethod.GET,RequestMethod.POST}) 
	public String delete(int empid, RedirectAttributes attr) {
		int result = empService.deleteService(empid);
		attr.addFlashAttribute("resultMessage", result>0?"삭제성공":"삭제실패");
		return "redirect:list.do";
	}
 
}

	
	
	
	
	