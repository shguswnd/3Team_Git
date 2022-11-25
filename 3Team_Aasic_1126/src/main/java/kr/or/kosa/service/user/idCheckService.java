package kr.or.kosa.service.user;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dao.MemberDao;
import kr.or.kosa.dto.Board;

public class idCheckService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//수정하기
		String newid = request.getParameter("userid");

		MemberDao member;
		ActionForward forward = null;
		String msg="";
	    String url="";
		try {		

			member = new MemberDao();
			int idcheck = member.signIdCheck(newid);  //유저 비밀번호 체크
			
			if(idcheck == 0){  //게시물이 뭔가 잘못 됬다면 오류메시지
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setPath("/WEB-INF/views/web/idOverLapFalse.jsp");
			}else {  //잘 수정 됬다면
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setPath("/WEB-INF/views/web/idOverLap.jsp");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return forward;
	}

}
