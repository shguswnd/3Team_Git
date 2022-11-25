package kr.or.kosa.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.service.admin.UserDeleteOk;
import kr.or.kosa.service.admin.UserDeleteService;
import kr.or.kosa.service.common.CategoryService;
import kr.or.kosa.service.common.LoginCheckService;
import kr.or.kosa.service.common.SessionCheckService;
import kr.or.kosa.service.common.MemberInsertService;


@WebServlet("*.do")
public class CommonController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public CommonController() {
        super();
        
    }
    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {    	
       	String requestURI = request.getRequestURI();
    	String contextPath = request.getContextPath();
    	String urlcommand = requestURI.substring(contextPath.length());
    	Action action=null;
    	ActionForward forward=null;
    	
    	

    	if(urlcommand.equals("/index.do")) {   //메인페이지(홈버튼)
    		forward = new ActionForward();
    		forward.setRedirect(false);
    		forward.setPath("/index.jsp");
    		
    	}else if(urlcommand.equals("/login.do")) { //로그인 페이지
    		forward = new ActionForward();
    		forward.setRedirect(false);
        	forward.setPath("/WEB-INF/views/common/login.jsp");
        	
    	}else if(urlcommand.equals("/logout.do")) { //로그아웃
		   forward = new ActionForward();
	       forward.setRedirect(false);
	       forward.setPath("/WEB-INF/views/common/logout.jsp");
	       
	    }else if(urlcommand.equals("/loginok.do")) { //loginok는 ajax로 뺴려고 생각중.
    	   action = new LoginCheckService();
  		   forward = action.execute(request, response);   
  		   
    	}else if(urlcommand.equals("/session.do")) { //세션처리
    	   action = new SessionCheckService();
    	   forward = action.execute(request, response);   
    	   
    	}else if(urlcommand.equals("/category.do")) { //카테고리처리
     	   action = new CategoryService();
     	   forward = action.execute(request, response);  
     	   
     	}else if(urlcommand.equals("/register.do")) { //회원가입 페이지
	       forward = new ActionForward();
	       forward.setRedirect(false);
	       forward.setPath("/WEB-INF/views/common/register.jsp");
	       
    	}else if(urlcommand.equals("/insert.do")) { // 회원가입된 데이터 삽입
		   action = new MemberInsertService();
 		   forward = action.execute(request, response);     
 		   
	    }else if(urlcommand.equals("/userDelete.do")) { // 회원 삭제
	       forward = new ActionForward();
	       forward.setRedirect(false);
	       forward.setPath("/WEB-INF/views/web/user_delete.jsp");
	 		   
		}else if(urlcommand.equals("/userDeleteOK.do")) { // 회원 삭제 처리
		   action = new UserDeleteOk();
 		   forward = action.execute(request, response);     
	    }
    	
    
    	if(forward != null) {
    		if(forward.isRedirect()) {
    			response.sendRedirect(forward.getPath());
    		}else {
    			RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
    			dis.forward(request, response);
    		}
    	}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
