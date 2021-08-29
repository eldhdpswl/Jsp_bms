<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file ="b_setting.jsp" %>
<link href="/BMS_project/style/boardstyle.css" rel="stylesheet" type="text/css" media="screen" />

<html>
<body onload="modifyFocus();">
	<h2><center>글 수정</center></h2>
	
	<c:if test="${selectCnt == 0}">
		<script type="text/javascript">
			errorAlert(pwdError);
		</script>
	</c:if>
	
	<c:if test="${selectCnt != 0}">
		<form action="b_modifyPro.bo" method="post" name="modifyform" 
			onsubmit="return modifyCheck();">
			<!-- submit일때는 hidden으로 넘김 -->
			<input type="hidden" name="num" value="${num}">
			<input type="hidden" name="pageNum" value="${pageNum}">
			
			<table align="center">
				<tr>
					<th colspan="2">수정할 정보를 입력하세요!!</th>
				</tr>
				
				<tr>
					<th>작성자</th>
					<td>${dto.writer}</td>
				</tr>
				
				<tr>
					<th>글제목</th>
					<td><input class="input" type="text" name="subject" maxlength="50"
						value="${dto.subject}" style="width:270px">
					</td>
				</tr>
				
				<tr>
					<th>글내용</th>
					<td>
					<textarea class="input" rows="10" cols="40" name="content">${dto.content}</textarea>
					</td>
				</tr>

				<tr>
					<th>비밀번호</th>
					<td><input class="input" type="password" name="pwd" maxlength="10"
						value="${dto.pwd}">
					</td>
				</tr>
				
				<tr>
					<th colspan="2">
						<input class="inputButton" type="submit" value="수정">
						<input class="inputButton" type="reset" value="수정취소">
						<input class="inputButton" type="button" value="목록보기"
								onclick="window.location='boardList.bo?pageNum=${pageNum}'">
					</th>
				</tr>
				
				
				
			</table>
		</form>
	</c:if>
	
</body>
</html>