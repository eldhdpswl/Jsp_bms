<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="b_setting.jsp" %>
<link href="/BMS_project/style/boardstyle.css" rel="stylesheet" type="text/css" media="screen" />

<html>
<body onload="pwdFocus();">
	<h2>글 수정</h2>
	<form action="b_modifyView.bo" method="post" name="pwdform"
			onsubmit="return pwdCheck();">
		
		<input type="hidden" name="num" value="${num}">
		<input type="hidden" name="pageNum" value="${pageNum}">
		
		<table align="center">
			<tr>
				<th colspan="2">
					비밀번호를 입력하세요!!
				</th>
			</tr>
			
			<tr>
				<th>비밀번호</th>
				<td>
					<input class="input" type="password" name="pwd" maxlength="10">
				</td>
			</tr>
			
			<tr>
				<th colspan="2">
					<input class="inputButton" type="submit" value="확인">
					<input class="inputButton" type="reset" value="취소"
						onclick="window.history.back();">
						
				</th>
			</tr>
		</table>
	
	
	</form>

</body>
</html>