package com.shinhan.myapp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shinhan.myapp.vo.MemberDTO;

import net.firstzone.util.DBUtil;

@Repository //@Component + DAO±â´É == Bean°´Ã¼ 
public class MemberDAO {
	
   @Autowired
   DataSource ds;
	
   Connection conn;
   PreparedStatement st;
   ResultSet rs;
   String select_login = "select * from members where member_id=?";
   
   public MemberDTO login(String mid, String mpass){
	   MemberDTO member = null;
	    
	   try {
		conn = ds.getConnection();
		st = conn.prepareStatement(select_login);
		st.setString(1, mid);
		rs = st.executeQuery();
		if(rs.next()) {
			String getPass = rs.getString("member_pass");
			if(mpass.equals(getPass)) {
				member = MemberDTO.builder()
						.member_id(mid)
						.member_pass(mpass)
						.member_name(rs.getString("member_name"))
						.member_email(rs.getString("member_email"))
						.build(); 
			}else {
			    member = MemberDTO.builder().member_id("-1").build();	
			}
		} 
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
	   return member;
   }
   
}












