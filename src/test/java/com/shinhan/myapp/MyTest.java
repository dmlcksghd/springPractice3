package com.shinhan.myapp;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.shinhan.myapp.emp.EmpDTO;
import com.shinhan.myapp.emp.EmpService;
import com.shinhan.myapp.model.DeptService;
import com.shinhan.myapp.vo.DeptDTO;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/appServlet/*.xml",
		"file:src/main/webapp/WEB-INF/spring/*.xml" 
		})

@Slf4j
public class MyTest {
	
	@Autowired
	EmpService empService;
	
	@Autowired
	DeptService deptService;
	
	//@Test
	public void f4() {
		deptService.selectAllService().forEach(dept-> {
			System.out.println(dept);
		});
	}
	
	//@Test
	public void f3() {
		IntStream.range(1, 5).forEach(i->{
			DeptDTO dept = DeptDTO.builder()
					.department_id(200+i)
					.department_name("TFÆÀ"+i)
					.location_id(1800)
					.manager_id(100)
					.build();
			deptService.insertService(dept);
		});
	}
	
	// @Test
	public void f2() {
		List<EmpDTO> emplist = empService.selectAllService();
		log.info(emplist.size() + "°Ç");
		assertTrue(emplist.size() == 109);
	}

	//@Test
	public void f1() {
		int a=10;
		assertTrue(5+5 == a);
	}
}