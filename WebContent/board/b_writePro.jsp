<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file ="b_setting.jsp" %>

<html>
<body>

	<h2><center>글쓰기 - 처리페이지</center></h2>
	<!--글쓰기 실패했을때  -->
	<c:if test = "${cnt == 0}">
		<script type="text/javascript">
			errorAlert(insertError);
		</script>
	</c:if>
	
	<!-- 글쓰기 성공했을때 -->
	<c:if test = "${cnt != 0}">
		<c:redirect url="boardList.bo" />	
	</c:if>
	
</body>
</html>