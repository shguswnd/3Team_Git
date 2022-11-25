package kr.or.kosa.service.user;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dao.MemberDao;

public class LoveCount implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = null;
		int lovecount = 0;
		
		try {
			MemberDao memberDao;
			memberDao = new MemberDao();
			int idx = Integer.parseInt(request.getParameter("idx"));
			lovecount = memberDao.loveCount(idx);
			request.setAttribute("lovecount", lovecount);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("/WEB-INF/views/board/board_content.jsp");
		
		
		return forward;
	}

}
