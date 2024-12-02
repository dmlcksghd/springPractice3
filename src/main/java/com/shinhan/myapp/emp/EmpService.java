package com.shinhan.myapp.emp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EmpService {

	//@Autowired: type이 같으면 injection, 같은 타입이 여러개 있으면 이름이 같은애 injection
	//@Qualifier: 이름으로 injection
	@Autowired
	@Qualifier("empMybatis")
	EmpDAOInterface empDAO;

	//직원(employees), 부서(departments), 지역(locations), 나라(countries)
	public List<EmpJoinDTO> selectByJobJoin(String job_id) {
		return empDAO.selectJoin(job_id);
	}
	
	public Map<String, Object> selectByJobJoin2(String job_id) {
		return empDAO.selectJoin2(job_id);
	}
	
	public List<Map<String, Object>> selectByJobJoin3(String job_id) {
		return empDAO.selectJoin3(job_id);
	}
	
	public List<JobDTO> selectAllJobService() {
		return empDAO.selectAllJob();
	}

	public List<EmpDTO> selectByDept(int dept_id) {
		return empDAO.selectByDept(dept_id);
	}

	public List<EmpDTO> selectByJob(String job_id) {
		return empDAO.selectByJob(job_id);
	}

	public List<EmpDTO> selectBySalary(double salary) {
		return empDAO.selectBySalary(salary);
	}

	public List<EmpDTO> selectByCondition(Map<String, Object> map) {
		return empDAO.selectByCondition(map);
	}

	//
	public List<EmpDTO> selectAllService() {
		return empDAO.selectAll();
	}

	public EmpDTO selectByIdService(int empid) {
		// TODO Auto-generated method stub
		return empDAO.selectById(empid);
	}

	// ?엯?젰?꽌鍮꾩뒪
	public int insertService(EmpDTO emp) {
		return empDAO.insert(emp);

	}

	// ?닔?젙?꽌鍮꾩뒪
	public int updateService(EmpDTO emp) {
		return empDAO.update(emp);
	}

	// ?궘?젣?꽌鍮꾩뒪
	public int deleteService(int empid) {
		return empDAO.delete(empid);
	}
}
