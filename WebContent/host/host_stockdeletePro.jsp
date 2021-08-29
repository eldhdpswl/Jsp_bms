<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="hostSetting.jsp"%> 

<html>
<body>
	<h2><center>책삭제 - 처리페이지</center></h2>
		<!-- 2. 답글이 없는 경우 삭제 실패인 경우 : '삭제실패' 메시지 -->
		<c:if test="${deleteCnt == 0}">
			<script type="text/javascript">
				alert("책 삭제에 실패했습니다.\n확인후 다시 시도하세요!!");
				window.location="host_stockList.host?pageNum=${pageNum}";
			</script>
		</c:if>
		
		<!-- 3. 답글이 없는 경우 삭제 성공인 경우 : '삭제성공' 메시지 -->
		<c:if test="${deleteCnt == 1}">
			<script type="text/javascript">
				alert("책 삭제에 성공했습니다.");
				window.location="host_stockList.host?pageNum=${pageNum}";
			</script>
		</c:if>

</body>
</html>