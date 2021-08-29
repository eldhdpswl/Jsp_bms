<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="setting.jsp" %>
    
<html>
<body>
	<h2><center>구매단 전 페이지</center></h2>
	
	<c:if test="${cnt==0}">
		<script type="text/javascript">
			errorAlert(insertcartAddError);
		</script>
	</c:if>
	
	<c:if test="${cnt != 0}">
		<script type="text/javascript">
			successAlert(beforepaySuccess);
			window.location.href="pay.mem";
		</script>
	
	</c:if>
	
</body>
</html>