package kr.or.kosa.service.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dao.MemberDao;

public class BoardModifyService implements Action{
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		try {
			String userid = request.getParameter("reply_userid");
			String idx = request.getParameter("idx");
			String msg="";
		    String url="";
			MemberDao dao = new MemberDao();
			int idx_fk = Integer.parseInt(idx);
			
			request.setAttribute("board_msg", msg);
			request.setAttribute("board_url", url);
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/WEB-INF/views/common/redirect.jsp");
			
		} catch (Exception e) {
			e.getStackTrace();
		}
		


		return forward;
	}
}
