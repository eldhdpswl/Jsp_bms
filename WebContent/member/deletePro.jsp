<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="setting.jsp" %>        
<script src="${project}script.js"></script>

<html>
<body>
	<h2>회원탈퇴 처리페이지</h2>
	deletePro.jsp페이지입니다.	<br>

	
	
	<!-- 아이디와 비밀번호가 일치 -->
	<c:if test="${selectCnt==1}">
		<!-- 삭제에러 -->
		<c:if test="${deleteCnt==0}">		
	
			<script type="text/javascript"> //이렇게 해야지 자바랑 구분지을수 있다.
				errorAlert(deleteError);
			</script>
			
		</c:if>		
 		
		<!-- 삭제성공 - 세션 삭제 -->
		<c:if test="${deleteCnt != 0}">
			<!-- 세션삭제 -->
			<% 
			request.getSession().invalidate(); 
			request.setAttribute("cnt", 2);
			%>
			<!-- 1초후에 alert창 띄우고 main.do로 이동 -->
			<script type="text/javascript">
				setTimeout(function(){
					alert("탈퇴처리되었습니다.");
					window.location='login.mem';
				}, 1000);
			</script>
		</c:if>				
	</c:if>	
		
	<!-- 비밀번호 불일치 -->
	<c:if test="${selectCnt !=1}">
		<script type="text/javascript"> //이렇게 해야지 자바랑 구분지을수 있다.
			errorAlert(passwdError);
		</script>	
	</c:if>

</body>
</html>