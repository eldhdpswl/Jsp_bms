<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="b_setting.jsp" %>
<link href="/BMS_project/style/boardstyle.css" rel="stylesheet" type="text/css" media="screen" />

<html>
<body onload="writeFocus();">
	<h2><center>글쓰기</center></h2>
	<form action="b_writePro.bo" method="post" name="writeform" onsubmit="return writeCheck();">
		<input type="hidden" name="num" value="${num}">
		<input type="hidden" name="ref" value="${ref}">
		<input type="hidden" name="ref_step" value="${ref_step}">
		<input type="hidden" name="ref_level" value="${ref_level}">
		
		<table align="center">
		
			<tr>
				<th>작성자</th>
				<td>
					<input class="input" type="text" name="writer" maxlength="20">
				</td>
			</tr>
		
			<tr>
				<th>비밀번호</th>
				<td>
					<input class="input" type="password" name="pwd" maxlength="10">
				</td>
			</tr>
			
			<tr>
				<th>제목</th>
				<td>
					<input class="input" type="text" name="subject" maxlength="50" style="width:270px">
				</td>
			</tr>
			
			<tr>
				<th>내용</th>
				<td>
					<textarea class="input" rows="10" cols="40" name="content" style="width:270px"></textarea>
				</td>
			</tr>
			
			<tr>
				<th colspan="2">
					<input class="inputButton" type="submit" value="작성">
					<input class="inputButton" type="reset" value="취소">
					<input class="inputButton" type="button" value="목록보기" 
							onclick="window.location='boardList.bo'">
				</th>
			
			</tr>
			
		
		</table>
		
		
		
	
	</form>
	
</body>
</html>