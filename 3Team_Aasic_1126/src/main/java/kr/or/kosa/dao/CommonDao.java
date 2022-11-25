package kr.or.kosa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import kr.or.kosa.dto.Category;
import kr.or.kosa.dto.Member;
import kr.or.kosa.dto.MemberDetail;



//sql1 = "select md.userid as userid, md.pwd as pwd, m.status as status from memberdetail md "
//		+ "inner join member m "
//		+ "on md.userid = m.userid "
//		+ "where md.userid=?";
// 조인 하는법 알려주기.

public class CommonDao {

	DataSource ds = null;
	private Connection conn;
	private PreparedStatement pstmt1;
	private PreparedStatement pstmt2;
	private PreparedStatement pstmt3;
	private ResultSet rs1;
	private String sql1;
	private String sql2;
	private String sql3;
	private int resultRow;
	private boolean idCheck; // 아이디 체크여부
	private Member member;
	private Category category;
	private MemberDetail memberDetail;
	private List<Member> memberList;
	private List<MemberDetail> memberDetailList;
	private List<Category> categoryList;
	private String pwdCheck;
	private int stausCheck;	

	public CommonDao() throws NamingException {
		Context context = new InitialContext();
		ds = (DataSource) context.lookup("java:comp/env/jdbc/oracle");
		conn = null;
		pstmt1 = null;
		pstmt2 = null;
		rs1 = null;
		resultRow = 0;
		idCheck = false;
		sql1 = "";
		sql2 = "";
	}
	
	
	//카테고리 출력 (추후에 동적 게시판 만들때 참고)
	public List<Category> getAllCategory() {
		try {
			conn = ds.getConnection();
			categoryList = new ArrayList<Category>();
			sql1 = "select * from category";
			pstmt1 = conn.prepareStatement(sql1);
			rs1 = pstmt1.executeQuery();

			while(rs1.next()) {
				category = new Category();
				category.setBoardname(rs1.getString("boardname"));
				categoryList.add(category);
			}	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				pstmt1.close();
				rs1.close();
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return categoryList;
	}

	
	//정지된 계정 판단
	public boolean idStatus(String userid) {
		try {
			conn = ds.getConnection();
			sql1 = "select status from member where userid=?";
			pstmt1 = conn.prepareStatement(sql1);
			pstmt1.setString(1, userid);
			rs1 = pstmt1.executeQuery();
			member = new Member();
			if (rs1.next()) {
				member.setStatus(rs1.getInt("status"));
				stausCheck = member.getStatus();
				if (stausCheck == 0) {
					idCheck = false; // 계정 정지
				} else {
					idCheck = true; //계정 사용
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				pstmt1.close();
				rs1.close();
				conn.close();
			} catch (Exception e2) {

			}
		}
		return idCheck;
	}
	
	// 로그인할 때 아이디 체크
	public boolean idCheck(String userid, String pwd) {
		try {
			
			conn = ds.getConnection();
			sql1 = "select * from memberdetail where userid=?";
			pstmt1 = conn.prepareStatement(sql1);
			pstmt1.setString(1, userid);
			rs1 = pstmt1.executeQuery();

			
			if (rs1.next()) {
				pwdCheck = rs1.getString("pwd");
				if (pwdCheck.equals(pwd)) {
					idCheck = true; // 아이디, 비밀번호 일치
				} else {
					idCheck = false; // 비밀번호 불일치
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				pstmt1.close();
				rs1.close();
				conn.close();
			} catch (Exception e2) {

			}
		}
		return idCheck;
	}

//	public List<Users>getUserList(){
//		
//		
//		return dtoList;
//	}

	/*
	 * public List<User>getUserList(){
	 * 
	 * 
	 * return dtoList; }
	 */

	// 회원가입
	public int writeOk(Member member, MemberDetail memberDetail) {
		try {
			conn = ds.getConnection();

			conn.setAutoCommit(false);

			sql1 = "insert into memberdetail(userid,pwd,name,email,phone,address,gender,joindate,profilephoto) " + 
						  "values(?,?,?,?,?,?,?,sysdate,0)";

			pstmt1 = conn.prepareStatement(sql1);
			pstmt1.setString(1, memberDetail.getUserid());
			pstmt1.setString(2, memberDetail.getPwd());
			pstmt1.setString(3, memberDetail.getName());
			pstmt1.setString(4, memberDetail.getEmail());
			pstmt1.setString(5, memberDetail.getPhone());
			pstmt1.setString(6, memberDetail.getAddress());
			pstmt1.setString(7, memberDetail.getGender());
			pstmt1.executeUpdate();

			sql2 = "insert into member(userid, status, ip) values(?,1,?)";
			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1, member.getUserid());
			pstmt2.setString(2, member.getIp());
			resultRow = pstmt2.executeUpdate();


			conn.commit();
			conn.setAutoCommit(true);

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		} finally {
			try {
				pstmt1.close();
				pstmt2.close();
				conn.close();
			} catch (Exception e2) {

			}

		}
		return resultRow;
	}

	// 게시글 삭제하기
		public int deleteOk(String idx, String userid) {
			try {
				conn = ds.getConnection();
				System.out.println("DAOidx 1: "+idx);
				System.out.println("DAOuserid 1: "+userid);
				
				String sql_userid = "select userid from board where idx=?"; // 비번검증
				//String sql_reply = "update board set replystatus=0 where idx_fk=?"; // 테이블에 cascade 처리하면 알아서 자식도 지워짐
				String sql_board = "update board set boardstatus=0 where idx=?"; // 게시글 삭제
//				String sql_reply = "delete from reply where idx_fk=?"; // 테이블에 cascade 처리하면 알아서 자식도 지워짐
//				String sql_board = "delete from board where idx=?"; // 게시글 삭제
				pstmt1 = conn.prepareStatement(sql_userid);
				pstmt1.setString(1, idx);
				rs1 = pstmt1.executeQuery();
				
				//System.out.println("DAOidx 2: "+idx);
				//System.out.println("DAOuserid 2: "+userid);
				if (rs1.next()) { // 삭제글은 존재
					// 사용자의 userid, DB의 userid
					if (userid.equals(rs1.getString("userid"))) { // userid가 맞다면
						// 스테이터스 값 1로 바꾸기 처리
						// 게시글 상태값 바꾸기 (원본글 , 답글)
						pstmt1 = conn.prepareStatement(sql_board);
						pstmt1.setString(1, idx);
						resultRow = pstmt1.executeUpdate();
						if (resultRow > 0) {
							conn.commit(); // 두개의 delete 실반영
						}
					} else { // 비밀번호가 일치 하지 않는 경우
						resultRow = -1;
					}
				} else { // 삭제하는 글이 존재하지 않는 경우
					resultRow = 0;
				}
			} catch (Exception e) {
				e.printStackTrace();
				// rollback
				// 예외가 발생하면
//				try {
//					conn.rollback();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
			} finally {
				try {
					pstmt1.close();
					rs1.close();
					conn.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return resultRow;
		}
		
		// 회원 삭제 or 탈퇴(=상태값 변환)
		public int userDelete(String userid) {
			try {
				conn = ds.getConnection();
				//conn.setAutoCommit(false);
				System.out.println("userid11 : "+userid);
				sql1 = "update member set status=0 where userid = ?";
				System.out.println(sql1);
				pstmt1 = conn.prepareStatement(sql1);
				pstmt1.setString(1, userid);
				
				resultRow = pstmt1.executeUpdate();
				System.out.println(resultRow);
				System.out.println("userid22 : "+userid);
				//sql2 = "update from member status=0 where userid=?";
//				pstmt2 = conn.prepareStatement(sql2);
//				pstmt2.setString(1, userid);
//				resultRow = pstmt2.executeUpdate();

				//conn.commit();
				//conn.setAutoCommit(true);
				
			} catch (SQLException e) {
				e.printStackTrace();
//				try {
//					//conn.rollback();
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}

			} finally {
				try {
					pstmt1.close();
					pstmt2.close();
					conn.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
			return resultRow;
		}
		
}
