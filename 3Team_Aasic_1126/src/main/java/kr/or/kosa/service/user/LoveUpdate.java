package kr.or.kosa.service.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dao.MemberDao;
import kr.or.kosa.dto.Love;

public class LoveUpdate implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = null;

		try {
			MemberDao member = new MemberDao();
			Love love = new Love();
			
			HttpSession session = request.getSession();
			String userid = (String)session.getAttribute("userid");
			int idx = Integer.parseInt(request.getParameter("idx"));
			
			love.setIdx(idx);
			love.setUserid(userid);
			
			
			int resultrow = member.loveCheck(love);
			
			
			if (resultrow == 0 || resultrow==1) {
				// 추천하기
				member.loveUpdate(love);
				member.loveCount(idx);
				int lovecount = member.loveCount(idx);
				request.setAttribute("lovecount", lovecount);
				
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setPath("/WEB-INF/views/web/LoveAjax.jsp");
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return forward;

	}
}
