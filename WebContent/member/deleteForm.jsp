<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="setting.jsp" %>    
    
<script src="${project}script.js"></script>

<html>
<body onload="passwdFocus()">
	<h2>회원탈퇴</h2>
	
	<form action="deletePro.mem" method="post" name="passwdform"
				onsubmit="return passwdCheck();">
		<table>
			<tr>
				<th colspan="2">비밀번호를 입력하세요</th>
			</tr>
			
			<tr>
				<th>비밀번호</th>
				<td><input class="input" type="password" name="pwd" maxlength="10"></td>
			
			</tr>
			<tr>
				<th colspan="2" align="center">
					<input class="inputButton" type="submit" value="회원탈퇴">
					<input class="inputButton" type="reset" value="탈퇴취소"
						onclick="window.history.back();">
				</th>
				
			
			</tr>
		</table>			
		
	</form>
</body>
</html>