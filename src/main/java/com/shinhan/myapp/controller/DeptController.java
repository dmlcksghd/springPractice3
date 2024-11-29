package com.shinhan.myapp.controller;

//자동import : ctrl+shift+o

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.shinhan.myapp.model.DeptService;
import com.shinhan.myapp.vo.DeptDTO;

//부서의 CRUD작업 : Controller-> Service -> DAO
//사용자 요청->DispatcherServlet->Controller찾기 
// component-scan의 의해서 Bean생성된다. 

//@Controller ==>요청을 받아서 응답페이지를 return한다. 
//@RestController=>요청을 받아서 응답데이터를 return한다. @Controller + @ResponseBody


@Controller
public class DeptController {

	@Autowired
	DeptService dService;

	Logger  logger = LoggerFactory.getLogger(DeptController.class);
	
	@GetMapping("/dept/insert.do")
	public String insertGet() {
		return "dept/deptInsert";
	}
	
	//RedirectAttributes addFlashAttribute: 전달되는 파라메터정보를 숨기고 전달후 소멸됨
	//RedirectAttributes addAttribute: 전달되는 파라메터 보여주고 전달후 소멸 안됨
	@PostMapping("/dept/insert.do")
	public String insertPost(DeptDTO dept, RedirectAttributes attr) {
		int result = dService.insertService(dept);
		String message = "입력건수:" + result;
		logger.info(message);
		attr.addFlashAttribute("resultMessage", message);
 
		return "redirect:/dept/list.do"; //재요청 
	}
	
	@RequestMapping("/dept/list.do")
	public String selectAll(Model model, 
			HttpServletRequest request ) {
 
		Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
		if(map!=null) {
			String message = (String)map.get("resultMessage");
			logger.info("받은메시지:" + message);
			model.addAttribute("result", message);
		}
		
		List<DeptDTO> deptlist = dService.selectAllService();
		model.addAttribute("deptlist", deptlist);
		return "dept/deptList";  //forward, include
		///WEB-INF/views/dept/deptList.jsp
	
	}
	
	
	//삭제?
	@RequestMapping(value = "/dept/delete.do", 
			method = {RequestMethod.GET, RequestMethod.POST} )
	public String delete(int deptid, RedirectAttributes attr) {
		int result = dService.deleteService(deptid);
		String message = "삭제건수:" + result;
		attr.addFlashAttribute("resultMessage", message);
		return "redirect:/dept/list.do";
	}
	
	
	@GetMapping("/dept/detail.do")
	public String detail(int deptid, Model model) {
	    model.addAttribute("deptInfo", dService.selectByIdService(deptid));
	    return "dept/deptDetail";
	}
	/* 상세보기후에 결과를 보여주고 1초후 list로 가기 
	@PostMapping("/dept/detail.do")
	public String detailPost(@ModelAttribute("dept") DeptDTO dept) {
	    logger.info(dept.toString());	    
	    dService.updateService(dept);
	    return "dept/result";
	}*/
	@PostMapping("/dept/detail.do")
	public String detailPost(DeptDTO dept, RedirectAttributes attr) {
	    logger.info(dept.toString());	    
	    int result = dService.updateService(dept);
	    String message = "수정건수:" + result;
	    logger.info(message);
	    attr.addFlashAttribute("resultMessage", message);
	    return "redirect:/dept/list.do";  //재요청하기 response.sendRedirect()
	}
	
	
}





