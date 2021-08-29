<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="b_setting.jsp" %>

<html>
<body>
	<h2>글 수정</h2>
	<c:if test="${cnt==0}">
		<script type="text/javascript">
			errorAlert(updateError);
		</script>
	</c:if>
	
	<c:if test="${cnt !=0}">
		<script type="text/javascript">
			alert("글이 수정되었습니다.");
			window.location="boardList.bo?pageNum=${pageNum}";
		</script>
	</c:if>
	
</body>
</html>