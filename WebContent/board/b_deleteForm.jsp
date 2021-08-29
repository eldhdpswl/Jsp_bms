<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file ="b_setting.jsp" %>
<link href="/BMS_project/style/boardstyle.css" rel="stylesheet" type="text/css" media="screen" />

<html>
<body onload="pwdFocus();">
	<h2><center>글 삭제</center></h2>
	
	<form action="b_deletePro.bo" method="post" name="pwdform"
			onsubmit="return pwdCheck();">
		
		<input type="hidden" name="num" value="${num}">
		<input type="hidden" name="pageNum" value="${pageNum}">
	
		<table align="center">
			<tr>
				<th colspan="2">비밀번호입력하세요.</th>
			</tr>
			
			<tr>
				<th>비밀번호</th>	
				<td><input class="class" type="password" name="pwd" maxlength="10"></td>
			</tr>
			
			<tr>
				<th colspan="2">
					<input class="inputButton" type="submit" value="글삭제">
					<input class="inputButton" type="reset" value="삭제취소"
							onclick="window.history.back();">
				</th>
			
			</tr>
		
		</table>
	
	</form>
			
</body>
</html>