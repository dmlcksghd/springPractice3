package com.shinhan.myapp.emp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;


//Data Source(DB정보를 이용해서 DB연결) => SqlSession Factory(DB, Mapper를 알고 있음) => SqlSession생성
// => DAO => Service => Controller
@Slf4j
@Repository("empMybatis")
public class EmpDAOMybatis implements EmpDAOInterface {

	@Autowired
	SqlSession sqlSession;
	
	String namespace = "com.shinhan.emp.";
	
	//return값 = sqlSession.selectOne("namespace와 mapper의 id", 파라메터)
	//return값 = sqlSession.selectList("")
	//return값 = sqlSession.selectMap("")
	
	public Map<String, Object> selectJoin2(String jobid) {
		Map<String, Object> emplist = sqlSession.selectMap(namespace + "selectAllJob", jobid);
		log.info("mybatis이용(Map): " + emplist.size() + "건");
		return emplist;
	}
	 
	public List<JobDTO> selectAllJob() {
		List<JobDTO> joblist = sqlSession.selectList(namespace + "selectAllJob");
		log.info("mybatis이용(Job: " + joblist.size() + "건");
		return joblist;
	}

	public List<EmpJoinDTO> selectJoin(String jobid) {
		List<EmpJoinDTO> emplist = sqlSession.selectList(namespace + "selectJoin", jobid);
		log.info("mybatis이용: " + emplist.size() + "건");
		return emplist;
	}

	public List<EmpDTO> selectByDept(int dept_id) {	
		List<EmpDTO> emplist = sqlSession.selectList(namespace + "selectByDept", dept_id);
		log.info("mybatis이용: " + emplist.size() + "건");
		return emplist;
	}

	public List<EmpDTO> selectByJob(String job_id) {	
		List<EmpDTO> emplist = sqlSession.selectList(namespace + "selectByJob", job_id);
		log.info("mybatis이용: " + emplist.size() + "건");
		return emplist;
	}

	public List<EmpDTO> selectBySalary(double salary) {
		List<EmpDTO> emplist = sqlSession.selectList(namespace + "selectBySalary", salary);
		log.info("mybatis이용: " + emplist.size() + "건");
		return emplist;
	}

	public List<EmpDTO> selectByCondition(Map<String, Object> map) {
		List<EmpDTO> emplist = sqlSession.selectList(namespace + "selectByCondition", map);
		log.info("mybatis이용: " + emplist.size() + "건");
		return emplist;
	}

	public List<EmpDTO> selectAll() {
		List<EmpDTO> emplist = sqlSession.selectList(namespace + "selectAll");
		log.info("mybatis이용: " + emplist.size() + "건");
		return emplist;
	}

	public EmpDTO selectById(int empid) {
		EmpDTO emp = sqlSession.selectOne(namespace + "selectById", empid);
		log.info("mybatis이용: " + emp.toString());
		return emp;
	}

	public int insert(EmpDTO emp) {
		int result = sqlSession.insert(namespace + "insert", emp);
		log.info("mybatis이용: " + result + "건 입력");
		return result;
	}
	 
	public int update(EmpDTO emp) {
		int result = sqlSession.update(namespace + "update", emp);
		log.info("mybatis이용: " + result + "건 수정");
		return result;
	}

	public int delete(int empid) {
		int result = sqlSession.delete(namespace + "delete", empid);
		log.info("mybatis이용: " + result + "건 삭제");
		return result;
	}
}