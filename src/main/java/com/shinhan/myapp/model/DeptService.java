package com.shinhan.myapp.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.myapp.vo.DeptDTO;

@Service 
public class DeptService {
    
	@Autowired   
	DeptDAO deptDao ;

	// 1.Î™®ÎëêÏ°∞Ìöå
	public List<DeptDTO> selectAllService() {
		return deptDao.selectAll();
	}

	// 2.?ÉÅ?Ñ∏Î≥¥Í∏∞
	public DeptDTO selectByIdService(int deptid) {
		return deptDao.selectById(deptid);
	}

	// 3.?ûÖ?†•
	public int insertService(DeptDTO dept) {
		
		return deptDao.insert(dept);
	}

	// 4.?àò?†ï
	public int updateService(DeptDTO dept) {
		return deptDao.update(dept);
	}

	// 5.?Ç≠?†ú
	public int deleteService(int deptid) {
		return deptDao.delete(deptid);
	}
}






