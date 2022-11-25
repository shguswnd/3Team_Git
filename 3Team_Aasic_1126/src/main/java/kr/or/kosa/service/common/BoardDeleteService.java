package kr.or.kosa.service.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;

public class BoardDeleteService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		String msg="";
	    String url="";
		String idx = request.getParameter("idx"); // current page
		String boardname = request.getParameter("boardname");
		String writerid = request.getParameter("writerid");
		
		ActionForward forward = null;
		
		if(idx == null || idx.trim().equals("")){  //글번호 오류
			msg ="삭제 실패";
			url = "boardContent.user?idx=" + idx;
			request.setAttribute("board_msg", msg);
			request.setAttribute("board_url", url);
			request.setAttribute("writerid", writerid);
			
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/WEB-INF/views/web/redirect.jsp");
			
		} else {  //제대로 처리됬다면
			request.setAttribute("idx", idx);
			request.setAttribute("boardname", boardname);
			request.setAttribute("writerid", writerid);
			
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/WEB-INF/views/web/boardDelete.jsp");  //삭제버튼 누르면 보여주는 페이지
		}
		
		return forward;
	}

}
