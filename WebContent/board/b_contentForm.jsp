<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- <%@ include file="../member/setting.jsp" %>  --%>
<%@ include file="b_setting.jsp" %>
<link href="/BMS_project/style/boardstyle.css" rel="stylesheet" type="text/css" media="screen" />

<html>
<body>
	<h2><center>상세페이지</center></h2>
	<table align="center">
		<tr>
			<th style="width:150px">글번호</th>
			<td style="width:150px">${number}</td>
		
			<th style="width:150px">조회수</th>
			<td style="width:150px">${dto.readCnt}</td>
		</tr>
		
		<tr>
			<th style="width:150px">작성자</th>
			<td style="width:150px">${dto.writer}</td>
			
			<th style="width:150px">작성일</th>
			<td style="width:150px" align="center">
				<fmt:formatDate type="both" pattern="yyyy-MM-dd HH:mm" value="${dto.reg_date}"/>
			</td>
		</tr>
		
		<tr>
			<th>글제목</th>
			<td colspan="3">${dto.subject}</td>
		</tr>
		
		<tr>
			<th>글내용</th>
			<td colspan="3">${dto.content}</td>
		</tr>
		
		<tr>
			<th colspan="4">
				<input class="inputButton" type="button" value="글수정" 
					   onclick="window.location='b_modifyForm.bo?num=${dto.num}&pageNum=${pageNum}'">
				<input class="inputButton" type="button" value="글삭제"
					   onclick="window.location='b_deleteForm.bo?num=${dto.num}&pageNum=${pageNum}'">	   
				<input class="inputButton" type="button" value="답글쓰기"
					   onclick="window.location='b_writeForm.bo?num=${dto.num}&ref=${dto.ref}&ref_step=${dto.ref_step}&ref_level=${dto.ref_level}'">
				<input class="inputButton" type="button" value="목록보기"
						onclick="window.location='boardList.bo?pageNum=${pageNum}'">
			
			</th>
		
		</tr>
		
	
	</table>
</body>
</html>