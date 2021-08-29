<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="setting.jsp" %>

<script src="${project}script.js"></script>

<html>
<body>
	<h2>회원정보 수정처리 페이지</h2>
	<%-- <%
		int cnt = (Integer)request.getAttribute("cnt");
		if(cnt == 0){
	%> --%>
		<c:if test="${cnt==0}">
			<script type="text/javascript">
				errorAlert(updateError);
			</script>	
		</c:if>
	<%-- <%				
		}else{
	%> --%>
		<c:if test="${cnt!=0}">
			<script type="text/javascript">
				alert("회원정보가 수정되었습니다.!!");
				window.location="main.do";
			</script>
		</c:if>	
		
		
</body>
</html>