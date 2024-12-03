package com.shinhan.myapp.emp;

//java=>JDBC=>Oracle?ù¥ Í∞úÎ∞ú ojdbc8.jar

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.shinhan.myapp.vo.DeptDTO;

import lombok.extern.slf4j.Slf4j;
import net.firstzone.util.DBUtil;
import net.firstzone.util.DateUtil;


@Slf4j
@Repository("empDAO")
public class EmpDAOJDBC implements EmpDAOInterface {

	@Autowired
	@Qualifier("dataSource")
	DataSource ds;
	
	Connection conn;
	
	public List<Map<String, Object>> selectJoin3(String jobid) {
		return null;
	}
	
	public Map<String, Object> selectJoin2(String jobid) {
 
		String sql =  
				" select employee_id, first_name, salary, department_name, city, country_name"+
				" from employees join departments using(department_id)"+
				"                       join locations using(location_id)"+
				"                       join countries USING (country_id)"+
				" where  job_id =  ? " ;

		Map<String, Object> map = new HashMap<>();
		List<EmpDTO> emplist = new ArrayList<>();
		List<DeptDTO> deptlist = new ArrayList<>();
		PreparedStatement st = null;
		ResultSet rs = null;
		 
		try {
			conn = ds.getConnection();
			st = conn.prepareStatement(sql);
			st.setString(1, jobid);
			rs = st.executeQuery();
			while (rs.next()) {
				EmpDTO emp = new EmpDTO();
				emp.setEmployee_id(rs.getInt("Employee_id"));
				emp.setFirst_name(rs.getString("First_name"));
				emp.setSalary(rs.getDouble("salary"));
				emplist.add(emp);

				DeptDTO dept = new DeptDTO();
				dept.setDepartment_name(rs.getString("Department_name"));
				deptlist.add(dept);
			}
			map.put("emp", emplist);
			map.put("dept", deptlist);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return map;

	}

	 
	public List<JobDTO> selectAllJob() {
	 
		String sql = " select * from jobs ";
		List<JobDTO> joblist = new ArrayList<>();
		 
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			conn = ds.getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while (rs.next()) {
				JobDTO emp = JobDTO.builder().job_id(rs.getString("job_id")).job_title(rs.getString("job_title"))
						.min_salary(rs.getInt("min_salary")).max_salary(rs.getInt("max_salary")).build();
				joblist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs); // Connection Î∞òÎÇ©
		}
		return joblist;

	}

	public List<EmpJoinDTO> selectJoin(String jobid) {
		// 1.DTOÎßåÎì†?ã§ 2.MAP?Ç¨?ö©?ïú?ã§.
		String sql = 
				" select employee_id, first_name, salary, department_name, city, country_name"+
				" from employees join departments using(department_id)"+
				"                       join locations using(location_id)"+
				"                       join countries USING (country_id)"+
				" where  job_id = ?";
				
		List<EmpJoinDTO> emplist = new ArrayList<EmpJoinDTO>();
	 
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			conn = ds.getConnection();
			st = conn.prepareStatement(sql);
			st.setString(1, jobid);
			rs = st.executeQuery();
			while (rs.next()) {
				EmpJoinDTO emp = EmpJoinDTO.builder().city(rs.getString("city")).employee_id(rs.getInt("employee_id"))
						.first_name(rs.getString("first_name")).country_name(rs.getString("country_name"))
						.department_name(rs.getString("department_name")).build();
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;

	}

	// 1.?äπ?†ïÎ∂??Ñú?ùò ÏßÅÏõêÏ°∞Ìöå where department_id = ?
	public List<EmpDTO> selectByDept(int dept_id) {
		// Î™®Îì† ÏßÅÏõê?ùÑ Ï°∞Ìöå?ïòÍ∏?
		String sql = "select * from employees where department_id = ?";
		 
		PreparedStatement st = null;
		ResultSet rs = null;
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		try {
			conn = ds.getConnection();
			st = conn.prepareStatement(sql); // SQLÎ¨? Ï§?Îπ?
			st.setInt(1, dept_id); // ??óê Í∞íÏùÑ Ï±ÑÏö∞Í∏?
			rs = st.executeQuery(); // DB?óê Í∞??Ñú ?ã§?ñâ?ïòÍ≥? Í≤∞Í≥ºÎ•? Í∞??†∏?ò®?ã§.
			while (rs.next()) {
				EmpDTO emp = makeEmp2(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	// 2.?äπ?†ïjob_id?ù∏ ÏßÅÏõêÏ°∞Ìöå where job_id = ?
	public List<EmpDTO> selectByJob(String job_id) {
		// Î™®Îì† ÏßÅÏõê?ùÑ Ï°∞Ìöå?ïòÍ∏?
		String sql = "select * from employees where job_id = ?";
	 
		PreparedStatement st = null;
		ResultSet rs = null;
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		try {
			conn = ds.getConnection();
			st = conn.prepareStatement(sql);  
			st.setString(1, job_id);  
			rs = st.executeQuery();  
			while (rs.next()) {  
				EmpDTO emp = makeEmp2(rs);  
				emplist.add(emp); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	// 3.Í∏âÏó¨Í∞? ??ù¥?ÉÅ?ù∏ ÏßÅÏõêÏ°∞Ìöå where salary >= ?
	public List<EmpDTO> selectBySalary(double salary) {
		// Î™®Îì† ÏßÅÏõê?ùÑ Ï°∞Ìöå?ïòÍ∏?
		String sql = "select * from employees where salary >= ?";
	 
		PreparedStatement st = null;
		ResultSet rs = null;
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		try {
			conn = ds.getConnection();
			st = conn.prepareStatement(sql); // SQLÎ¨? Ï§?Îπ?
			st.setDouble(1, salary); // ??óê Í∞íÏùÑ Ï±ÑÏö∞Í∏?
			rs = st.executeQuery(); // DB?óê Í∞??Ñú ?ã§?ñâ?ïòÍ≥? Í≤∞Í≥ºÎ•? Í∞??†∏?ò®?ã§.
			while (rs.next()) { // ?ã§?ùådataÍ∞? ?ûà?äîÏß??
				EmpDTO emp = makeEmp2(rs); // ?ïúÍ±¥ÏùÑ DTOÎßåÎì†?ã§.
				emplist.add(emp); // ?ó¨?ü¨Í±¥Ïù¥ÎØ?Î°? Collection?óê ?ã¥Í∏?
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	// 4.Î∂??Ñú, ÏßÅÏ±Ö, Í∏âÏó¨, ?ûÖ?Ç¨?ùº Ï°∞Í±¥?úºÎ°? Ï°∞Ìöå
	// where department_id = ? and job_id = ? and salary >= ? and hire_date >= ?
	public List<EmpDTO> selectByCondition(Map<String, Object> map) {
		// Î™®Îì† ÏßÅÏõê?ùÑ Ï°∞Ìöå?ïòÍ∏?
		String sql = "select * " + " from employees " 
		+ " where (-1 = ? or department_id = ?) "
		+ " and ('-1' = ? or job_id = ? )" 
		+ " and salary >= ? " + " and  hire_date >= ?";
		 
		PreparedStatement st = null;
		ResultSet rs = null;
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		try {
			conn = ds.getConnection();
			st = conn.prepareStatement(sql); // SQLÎ¨? Ï§?Îπ?			
			String str_deptid = (String)map.get("deptid");
			int deptid = Integer.parseInt(str_deptid);
			String str_sal = (String) map.get("salary");
			String str_hdate = (String) map.get("hdate");
			Date hdate = DateUtil.convertSqlDate(DateUtil.convertDate(str_hdate));			
			st.setInt(1, deptid);  
			st.setInt(2, deptid);  
			st.setString(3, (String) map.get("jobid")); 
			st.setString(4, (String) map.get("jobid"));  
			st.setDouble(5, Double.parseDouble(str_sal));  
			st.setDate(6, hdate);  

			rs = st.executeQuery();  
			while (rs.next()) { 
				EmpDTO emp = makeEmp2(rs);  
				emplist.add(emp);  
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	public List<EmpDTO> selectAll() {
		// Î™®Îì† ÏßÅÏõê?ùÑ Ï°∞Ìöå?ïòÍ∏?
		String sql = "select * from employees order by 1";
	 
		Statement st = null;
		ResultSet rs = null;
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		try {
			conn = ds.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	public EmpDTO selectById(int empid) {
		// ?äπ?†ï ÏßÅÏõê?ùÑ Ï°∞Ìöå?ïòÍ∏?
		String sql = "select  *  from employees where employee_id = " + empid;
	 
		Statement st = null;
		ResultSet rs = null;
		EmpDTO emp = null;
		try {
			conn = ds.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				emp = makeEmp(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emp;
	}

	// DB?óê ?ûÖ?†•
	public int insert(EmpDTO emp) {
		int result = 0;
		String sql = "insert into employees values (?,?,?,?,?,?,?,?,?,?,?)";
		 
		// Statement?äî ?(bindingÎ≥??àò Ïß??õê?ïà?ï®) <------PreparedStatement?äî Ïß??õê
		PreparedStatement st = null;
		try {
			conn = ds.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, emp.getEmployee_id());
			st.setString(2, emp.getFirst_name());
			st.setString(3, emp.getLast_name());
			st.setString(4, emp.getEmail());
			st.setString(5, emp.getPhone_number());
			st.setDate(6, emp.getHire_date());
			st.setString(7, emp.getJob_id());
			st.setDouble(8, emp.getSalary());
			st.setDouble(9, emp.getCommission_pct());
			st.setObject(10, emp.getManager_id() == -1 ? null : emp.getManager_id());
			st.setObject(11, emp.getDepartment_id() == -1 ? null : emp.getDepartment_id());

			result = st.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}

		return result;
	}

	 
	public int update(EmpDTO emp) {
		int result = 0;
		String sql = 
				" update employees set "+
				"		FIRST_NAME=?,"+
				"		LAST_NAME=?,"+
				"		EMAIL=?,"+
				"		PHONE_NUMBER=?,"+
				"		HIRE_DATE=?,"+
				"		JOB_ID=?,"+
				"		SALARY=?,"+
				"		COMMISSION_PCT=?,"+
				"		MANAGER_ID=?,"+
				"		DEPARTMENT_ID=?"+
				" where EMPLOYEE_ID=?";
				
		 
 
		PreparedStatement st = null;
		try {
			conn = ds.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(11, emp.getEmployee_id());
			st.setString(1, emp.getFirst_name());
			st.setString(2, emp.getLast_name());
			st.setString(3, emp.getEmail());
			st.setString(4, emp.getPhone_number());
			st.setDate(5, emp.getHire_date());
			st.setString(6, emp.getJob_id());
			st.setDouble(7, emp.getSalary());
			st.setDouble(8, emp.getCommission_pct());
			st.setObject(9, emp.getManager_id() == -1 ? null : emp.getManager_id());
			st.setObject(10, emp.getDepartment_id() == -1 ? null : emp.getDepartment_id());

			result = st.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}

		return result;
	}

	// ?Ç≠?†ú
	public int delete(int empid) {
		int result = 0;
		String sql = "delete from employees where EMPLOYEE_ID = ? ";
		
		PreparedStatement st = null;
		try {
			conn = ds.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, empid);

			result = st.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}

		return result;
	}

	private static EmpDTO makeEmp2(ResultSet rs) throws SQLException {
		EmpDTO emp = EmpDTO.builder().commission_pct(rs.getDouble("Commission_pct"))
				.department_id(rs.getInt("Department_id")).email(rs.getString("email"))
				.employee_id(rs.getInt("Employee_id")).first_name(rs.getString("first_name"))
				.last_name(rs.getString("Last_name")).hire_date(rs.getDate("Hire_date")).job_id(rs.getString("job_id"))
				.manager_id(rs.getInt("Manager_id")).phone_number(rs.getString("Phone_number"))
				.salary(rs.getDouble("salary")).build();
		return emp;
	}

	//±‚¡∏ JDBC«¡∑Œ±◊∑•¿« ¥‹¡° : SQLπÆ¿Ã ¿⁄πŸøÕ ºØø©¿÷¿Ω, DB¿« table(Object) ƒÆ∑≥∞˙ ¿⁄πŸ¿« VO¿« field∏¶ ¡˜¡¢ Mapping
	private static EmpDTO makeEmp(ResultSet rs) throws SQLException {
		EmpDTO emp = new EmpDTO();
		emp.setCommission_pct(rs.getDouble("Commission_pct"));
		emp.setDepartment_id(rs.getInt("Department_id"));
		emp.setEmail(rs.getString("email"));
		emp.setEmployee_id(rs.getInt("Employee_id"));
		emp.setFirst_name(rs.getString("First_name"));
		emp.setLast_name(rs.getString("Last_name"));
		emp.setHire_date(rs.getDate("Hire_date"));
		emp.setJob_id(rs.getString("job_id"));
		emp.setManager_id(rs.getInt("Manager_id"));
		emp.setPhone_number(rs.getString("Phone_number"));
		emp.setSalary(rs.getDouble("salary"));

		return emp;
	}

}
