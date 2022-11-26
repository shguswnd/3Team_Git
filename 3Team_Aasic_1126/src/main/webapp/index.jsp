<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="Stylesheet" href="style/default.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<div id="pageContainer">
		<h3>UI(Css 공통 페이지 적용 확인)</h3>
		<h3>DB연결 정보 확인</h3>
		<%
		Connection conn = null;

		Context context = new InitialContext(); //현재 프로젝트에 이름기반 검색
		DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/oracle");//java:comp/env/ + name

		//POOL안에서 connection 가지고 오기
		conn = ds.getConnection();

		boolean connect1 = conn.isClosed();

		//POINT
		//POOL에 connection 받환하기
		conn.close(); //반환하기
		boolean connect2 = conn.isClosed();
		%>
		<c:set var="connect1" value="<%=connect1%>" />
		db 연결여부 : ${ connect1 }<br>
		<c:set var="connect2" value="<%=connect2%>" />
		db 연결여부 : ${ connect2 }<br>
	</div>

	<br>
	<br>
	<br>
	
		<c:set var="hotList" value="${requestScope.hotList}" />
	<form name="hotList" method="POST" action="hotList.do">
	<div id="pagecontainer">
		<div>
			<table width="80%" border="1" cellspacing="0" align="center">
				<tr>
					<th width="10%">HOT</th>
					<th width="20%">${hotList.boardname}</th>
					<th width="60%">제목</th>
					<th width="10%">조회수</th>
				</tr>
				<c:forEach var="hotList" items="${hotList}">
					<tr onmouseover="this.style.backgroundColor='skyblue'"
						onmouseout="this.style.backgroundColor='white'">
						<td align="center">${hotList.idx}</td>
						<td align="left"><a
							href="boardContent.user?boardname=${boardname}&idx=${hotList.idx}&cp=${cpage}&ps=${pagesize}">
								<c:choose>
									<c:when
										test="${hotList.title != null && fn:length(hotList.title) > 20}">
										${fn:substring(hotList.title,0,20)}...
									</c:when>
									<c:otherwise>
										${hotList.title}
									</c:otherwise> 
								</c:choose>
						</a></td>
						<td align="center">${hotList.userid}</td>
						<td align="center">${hotList.writedate}</td>
						<td align="center">${hotList.readnum}</td>
					</tr>
				</c:forEach>
				</table>
				</div>
				</div>
				</form>
</body>
</html>