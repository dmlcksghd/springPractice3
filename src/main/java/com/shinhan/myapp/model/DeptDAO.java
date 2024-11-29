package com.shinhan.myapp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shinhan.myapp.vo.DeptDTO;

import net.firstzone.util.DBUtil;

/*
 * <bean id="deptDAO2" class="DeptDAO"/>
 */
@Repository("deptDAO2") 
public class DeptDAO {
 
	//type이 같으면 자동으로 Injection된다. (IOC, DI)
	@Autowired
	DataSource ds;
	
	
	String sql_selectAll = "select * from departments order by 1";
	String sql_selectById = "select * from departments where department_id = ?";
	String sql_insert = "insert into departments values(?,?,?,?) ";
	String sql_update = "update departments set "+
			" department_name=?," +
			" manager_id=?,"+ 
			" location_id=?" + 
			" where department_id=?";

	String sql_delete = "delete from departments where department_id = ?";

	Connection conn;
	PreparedStatement st;
	ResultSet rs;
	int result;

	// 1.紐⑤몢議고쉶
	public List<DeptDTO> selectAll() {
		List<DeptDTO> deptlist = new ArrayList<>();
		try {
			conn = ds.getConnection();
			st = conn.prepareStatement(sql_selectAll);
			rs = st.executeQuery();
			while (rs.next()) {
				DeptDTO dept = new DeptDTO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
				deptlist.add(dept);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}

		return deptlist;
	}

	// 2.?긽?꽭蹂닿린
	public DeptDTO selectById(int deptid) {
		DeptDTO dept = null;
		 
		try {
			conn = ds.getConnection();
			st = conn.prepareStatement(sql_selectById);
			st.setInt(1, deptid);
			rs = st.executeQuery();
			while (rs.next()) {
				dept = new DeptDTO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return dept;

	}

	// 3.?엯?젰
	public int insert(DeptDTO dept) {
		 
		try {
			conn = ds.getConnection();
			st = conn.prepareStatement(sql_insert);
			st.setInt(1, dept.getDepartment_id());
			st.setString(2, dept.getDepartment_name());
			st.setInt(3, dept.getManager_id());
			st.setInt(4, dept.getLocation_id());
			result = st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return result;
	}

	// 4.?닔?젙
	public int update(DeptDTO dept) {
		 
		try {
			conn = ds.getConnection();
			st = conn.prepareStatement(sql_update);
			st.setInt(4, dept.getDepartment_id());
			st.setString(1, dept.getDepartment_name());
			st.setInt(2, dept.getManager_id());
			st.setInt(3, dept.getLocation_id());
			result = st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return result;
	}

	// 5.?궘?젣
	public int delete(int deptid) {
		 
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			st = conn.prepareStatement(sql_delete);
			st.setInt(1, deptid);
			result = st.executeUpdate();

			conn.commit(); // DB?뿉 諛섏쁺?븳?떎.

		} catch (SQLException e) {
			try {
				conn.rollback(); // DB?뿉 ?옉?뾽?븳 ?궡?슜痍⑥냼
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return result;
	}

	public int deleteArray(Integer[] deptid) {
		int result = 0;
		String sql = " delete from departments where department_id in (?)";
		 
		PreparedStatement st = null;
		try {
			conn = ds.getConnection();
			st = conn.prepareStatement(sql);
			st.setArray(1, conn.createArrayOf("number", deptid));

			result = st.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}

		return result;
	}

}
