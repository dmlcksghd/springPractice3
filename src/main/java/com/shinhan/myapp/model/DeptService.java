package com.shinhan.myapp.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.shinhan.myapp.vo.DeptDTO;

@Service 
public class DeptService {
    
	//@Autowired: type이 같으면 자동으로 Injection해준다.
	//같은 타입이 여러개 있으면 오류
	//@Qualifier: 이름으로 Injection한다.
	@Autowired
	@Qualifier("deptMybatis")
	DeptDAOInterface deptDao ;

	// 1.紐⑤몢議고쉶
	public List<DeptDTO> selectAllService() {
		return deptDao.selectAll();
	}

	// 2.?긽?꽭蹂닿린
	public DeptDTO selectByIdService(int deptid) {
		return deptDao.selectById(deptid);
	}

	// 3.?엯?젰
	public int insertService(DeptDTO dept) {
		
		return deptDao.insert(dept);
	}

	// 4.?닔?젙
	public int updateService(DeptDTO dept) {
		return deptDao.update(dept);
	}

	// 5.?궘?젣
	public int deleteService(int deptid) {
		return deptDao.delete(deptid);
	}
}






