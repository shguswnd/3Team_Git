<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>게시판 글쓰기</title>
	<script src="https://cdn.ckeditor.com/4.16.0/standard/ckeditor.js"></script>
	<link rel="Stylesheet" href="${pageContext.request.contextPath}/style/default.css" />
	<SCRIPT type="text/javascript">
function check(){
    if(!bbs.title.value){
        alert("제목을 입력하세요");
        bbs.title.focus();
        return false;
    }
 
    document.bbs.submit();
 
}
</SCRIPT>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/userHeader.jsp"></jsp:include>
	 <%-- <c:import url="/WEB-INF/views/include/header.jsp" /> --%>
    <div id="pageContainer">
        <div style="padding-top: 25px; text-align: center">
            <!-- form 시작 ---------->
            <form name="bbs" action="boardWriteOK.user" method="POST" enctype="multipart/form-data">
                <table width="95%" border="2" align="center">
                    <tr>
                        <td width="20%" align="center">말머리 선택</td>
                        <td width="80%" align="left">
                        	<!-- <input type="text" name="category" size="40"> -->
                        	<select name="category">
							  <option value=" [미채택]" selected="selected">[미채택]</option>
							  <option value=" [채택완료]">[채택완료]</option>
							</select>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="center">제목</td>
                        <td width="80%" align="left"><input type="text" name="title" size="40" maxlength="25"></td>
                    </tr>
                    <tr>
                        <td width="20%" align="center">글내용</td>
                        <td width="80%" align="left"><textarea rows="10" cols="60" name="content" class="ckeditor"></textarea></td>
                    </tr>
                   
                    <tr>
                        <td width="20%" align="center">첨부파일</td>
                        <td width="80%" align="left"><input type="file" name="filename"></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <button type="button" class="btn btn-success rounded-pill m-2" onclick="check();">Write</button>
                            <button type="reset" class="btn btn-danger rounded-pill m-2">ReWrite</button>
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="boardname" value=${boardname}>
              </form>            
        </div>
    </div>

</body>
</html>