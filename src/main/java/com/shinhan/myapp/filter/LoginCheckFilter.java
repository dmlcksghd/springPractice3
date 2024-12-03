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

import lombok.extern.slf4j.Slf4j;

/**
 * @WebFilter : Servlet3버젼부터  지원한다. 
 */
@Slf4j
@WebFilter("*.do")
public class LoginCheckFilter implements Filter {

    
    public LoginCheckFilter() {
    }

	public void destroy() {
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//요청수행하기전 
		HttpServletRequest req = (HttpServletRequest)request;
		//요청의 주소를 얻어보기
		String contextPath = req.getServletContext().getContextPath();
		String uri = req.getRequestURI();
		uri = uri.substring(contextPath.length());//    /firstzone/dept/list.do
		log.info("contextPath:" + contextPath);
		log.info("요청의 주소를 얻어보기:" + uri);
		
		//요청주소가 로그인이면 요청대로 수행하고 로그인이 아니면 로그인한건지 체크  
		if(!uri.equals("/auth/login.do")) {
			HttpSession session =  req.getSession();
			MemberDTO member = (MemberDTO)session.getAttribute("loginMember");
			if(member == null) {
				log.info("로그인안함");
				HttpServletResponse res = (HttpServletResponse)response;
				res.sendRedirect(contextPath + "/auth/login.do");
				return;
			}
		}
		chain.doFilter(request, response);
		//요청수행후...응답전
	}


	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
