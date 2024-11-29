package com.shinhan.myapp.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthOptionPaneUI;

import com.shinhan.myapp.vo.MemberDTO;

/**
 * @WebFilter : Servlet3��������  �����Ѵ�. 
 */
@WebFilter("*.do")
public class LoginCheckFilter implements Filter {

    
    public LoginCheckFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//��û�����ϱ��� 
		HttpServletRequest req = (HttpServletRequest)request;
		//��û�� �ּҸ� ����
		String contextPath = req.getServletContext().getContextPath();
		String uri = req.getRequestURI();
		uri = uri.substring(contextPath.length());//    /firstzone/dept/list.do
		System.out.println("contextPath:" + contextPath);
		System.out.println("��û�� �ּҸ� ����:" + uri);
		
		//��û�ּҰ� �α����̸� ��û��� �����ϰ� �α����� �ƴϸ� �α����Ѱ��� üũ  
		if(!uri.equals("/auth/login.do")) {
			HttpSession session =  req.getSession();
			MemberDTO member = (MemberDTO)session.getAttribute("loginMember");
			if(member == null) {
				System.out.println("�α��ξ���");
				HttpServletResponse res = (HttpServletResponse)response;
				res.sendRedirect(contextPath + "/auth/login.do");
				return;
			}
		}
		chain.doFilter(request, response);
		//��û������...������
	}


	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
