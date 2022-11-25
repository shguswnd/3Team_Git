package kr.or.kosa.service.user;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dao.MemberDao;
import kr.or.kosa.dto.Bookmarks;

public class BookMarksListService implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		try {
			MemberDao memberdao = new MemberDao();
			Bookmarks bookmarks = new Bookmarks();
			HttpSession session = request.getSession();
			
			int idx = Integer.parseInt(request.getParameter("idx")); //board_content.jsp의 name을 getParameter로 가져온다
			String userid = request.getParameter("userid");
			String title = request.getParameter("title");
			String writedate = request.getParameter("writedate");			
			bookmarks.setIdx(idx);
			bookmarks.setUserid(userid);
			bookmarks.setTitle(title);
			
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/WEB-INF/views/web/bookmarkList.jsp");
			

		} catch (Exception e) {
			e.getStackTrace();
		}
		
		
		return forward;
	}

}
