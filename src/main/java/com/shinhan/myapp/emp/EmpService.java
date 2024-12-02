package com.shinhan.myapp.emp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EmpService {

	@Autowired
	@Qualifier("empMybatis")
	EmpDAOInterface empDAO;

	public List<JobDTO> selectAllJobService() {
		return empDAO.selectAllJob();
	}

	//
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

	// ?ž…? ¥?„œë¹„ìŠ¤
	public int insertService(EmpDTO emp) {
		return empDAO.insert(emp);

	}

	// ?ˆ˜? •?„œë¹„ìŠ¤
	public int updateService(EmpDTO emp) {
		return empDAO.update(emp);
	}

	// ?‚­? œ?„œë¹„ìŠ¤
	public int deleteService(int empid) {
		return empDAO.delete(empid);
	}
}
