<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="hostSetting.jsp"%>    
    
<html>
<body>
	<h2><center>책 등록 - 처리페이지</center></h2>	
	<!-- 책등록 실패했을때 -->
	<c:if test = "${cnt == 0}">
		책등록에 실패했습니다.
	</c:if>
	
	<!-- 책등록 성공했을때 -->
	<c:if test = "${cnt != 0}">
		System.out.println("책등록에 성공했습니다.");
		<c:redirect url="host_stockaddForm.host" />
	</c:if>
	
</body>
</html>