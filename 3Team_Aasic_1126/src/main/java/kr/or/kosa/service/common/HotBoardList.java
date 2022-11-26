package kr.or.kosa.service.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dao.CommonDao;
import kr.or.kosa.dto.Board;

public class HotBoardList implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		try {
			
			CommonDao commonDao = new CommonDao();
			String boardname = request.getParameter("boardname");
			String title = request.getParameter("title");
			int idx = Integer.parseInt(request.getParameter("idx"));
			
			System.out.println("boardname: " + boardname);
			System.out.println("title: " + title);
			
			
			List<Board> hotList = commonDao.hotBoardList(boardname, title);
			System.out.println("dao돌고옴");
			
			request.setAttribute("hotList", hotList);
			
			System.out.println();
		
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/WEB-INF/index.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forward;
	}

}
