<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="b_setting.jsp" %>

<html>
<body>
	<h2><center>글삭제 - 처리페이지</center></h2>
	<!-- 비밀번호가 다른 경우 : '삭제에러' 메시지 -->
	<c:if test="${selectCnt == 0}">
		<script type="text/javascript">
			errorAlert(pwdError);
		</script>
	</c:if>
	
	<!-- 비밀번호가 일치하는 경우 -->
	<c:if test="${selectCnt != 0}">
	
		<!-- 1. 답글이 있는 경우 '답글이 있으므로 삭제안함' -->
			<c:if test="${deleteCnt == -1}">
				<script type="text/javascript">
					alert("답글이 있는 경우 삭제할 수 없습니다.");
					window.location="boardList.bo?pageNum=${pageNum}";
				</script>
			</c:if>
	
		<!-- 2. 답글이 없는 경우 삭제 실패인 경우 : '삭제실패' 메시지 -->
		<c:if test="${deleteCnt == 0}">
			<script type="text/javascript">
				alert("글 삭제에 실패했습니다.\n확인후 다시 시도하세요!!");
				window.location="boardList.bo?pageNum=${pageNum}";
			</script>
		</c:if>
		
		<!-- 3. 답글이 없는 경우 삭제 성공인 경우 : '삭제성공' 메시지 -->
		<c:if test="${deleteCnt == 1}">
			<script type="text/javascript">
				alert("글 삭제에 성공했습니다.");
				window.location="boardList.bo?pageNum=${pageNum}";
			</script>
		</c:if>
	
	</c:if>
	
</body>
</html>