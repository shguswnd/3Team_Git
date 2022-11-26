package kr.or.kosa.service.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dao.MemberDao;
import kr.or.kosa.dto.Board;
import kr.or.kosa.dto.Reply;



public class BoardContentService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		

		try {
			String idx = request.getParameter("idx");
			String cpage = request.getParameter("cp"); // current page
			String pagesize = request.getParameter("ps"); // pagesize
			String boardname = request.getParameter("boardname");
			boolean isread = false;
			String filename = "";
			
			List<Reply> replyList = new ArrayList<>();
			Board board = new Board();
			MemberDao dao = new MemberDao();
			filename = dao.FileList(idx);
			
			MemberDao memberDao = new MemberDao();
			// 글 번호를 가지고 오지 않았을 경우 예외처리
			if (idx == null || idx.trim().equals("")) {
				System.out.println("여기안옴");
				response.sendRedirect("BoardList.user?boardname="+boardname);
				return null;
			}
			
			idx = idx.trim();

			//List 페이지 처음 호출 ...
			if(cpage == null || cpage.trim().equals("")){
				//default 값 설정
				cpage = "1"; 
			}
		
			if(pagesize == null || pagesize.trim().equals("")){
				//default 값 설정
				pagesize = "5"; 
			}
			
			int idx2 = Integer.parseInt(idx);

			isread = memberDao.getReadNum(idx);
			
			if(isread) {
				board = memberDao.getContent(Integer.parseInt(idx));
				replyList = memberDao.replylist(Integer.parseInt(idx));
			}
			
			int lovecount = memberDao.loveCount(idx2);
			
			request.setAttribute("board", board);
			request.setAttribute("idx", idx);
			request.setAttribute("cp", cpage);
			request.setAttribute("ps", pagesize);
			request.setAttribute("filename", filename);
			request.setAttribute("replyList", replyList);
			request.setAttribute("lovecount", lovecount);
			
			forward = new ActionForward();
			forward.setRedirect(false); // forward
			forward.setPath("/WEB-INF/views/web/board_content.jsp");

		} catch (Exception e) {
			System.out.println("??");
			e.printStackTrace();
		}
		return forward;
	}

}
