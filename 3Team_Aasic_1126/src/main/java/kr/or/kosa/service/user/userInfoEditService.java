package kr.or.kosa.service.user;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dao.MemberDao;
import kr.or.kosa.dto.Board;

public class userInfoEditService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//수정하기
		HttpSession session = request.getSession();	
		String userid = (String)session.getAttribute("userid");
		String pwd = request.getParameter("pwd");  //수정할 글 번호 가져옴
		String msg="";
	    String url="";
	    
		MemberDao member;
		ActionForward forward = null;
		try {		
			member = new MemberDao();
			boolean pwdcheck = member.passwordCheck(userid, pwd);  //유저 비밀번호 체크
			
			if(pwdcheck == false){  //게시물이 뭔가 잘못 됬다면 오류메시지
				msg ="비밀번호가 틀립니다";
				url ="javascript:history.back()";
				request.setAttribute("board_msg", msg);
				request.setAttribute("board_url", url);
				
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setPath("/WEB-INF/views/web/redirect.jsp");
			}else {  //잘 수정 됬다면
				msg ="비밀번호가 맞습니다. 회원정보 수정페이지로 이동합니다";
				url ="updateView.user";
				
				request.setAttribute("board_msg", msg);
				request.setAttribute("board_url", url);
				
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setPath("/WEB-INF/views/web/redirect.jsp");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return forward;
	}

}
