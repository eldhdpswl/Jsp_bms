<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="setting.jsp" %>    
    
<html>
<body>
	<h2><center>장바구니 책담기 - 처리페이지</center></h2>
	<c:if test = "${cnt == 0}">
	 	장바구니 책담기에 실패하였습니다.
		<script type="text/javascript">
		
			errorAlert(payfail);
		</script>
	</c:if>
	
	<c:if test = "${cnt != 0}">
		<script type="text/javascript">
		
			successAlert(paysuccess);
			
			window.location.href="paylist.mem";
		</script>
			
		<%-- <c:redirect url="cart.mem"/> --%>	
	</c:if>
	
	
</body>
</html>