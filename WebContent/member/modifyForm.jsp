<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="setting.jsp" %>  
<script src="${project}script.js"></script>
<link href="/BMS_project/style/login.css" rel="stylesheet" type="text/css" media="screen" />

<html>
<body onload="passwdFocus();">
	<h2>회원정보 수정</h2>
	<form action="modifyView.mem" method="post" name="passwdform"
			onsubmit="return passwdCheck();">
		<table>
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
					<input class="inputButton" type="submit" value="정보수정">
					<input class="inputButton" type="reset" value="수정취소"
							onclick="window.history.back();">
				
				</th>
				
				
			
			</tr>
		
		</table>
	
	</form>

</body>
</html>