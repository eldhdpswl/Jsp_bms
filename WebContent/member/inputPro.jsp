<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="setting.jsp" %>    
<script src="${project}script.js"></script>

<html>
<body>
<%-- <%
	int cnt = (Integer)request.getAttribute("cnt");
	if(cnt==0){
%> --%>	

	<c:if test="${cnt==0}">	
		<script type="text/javascript">
			errorAlert(insertError);
		</script>
	</c:if>	
	
<%-- <% 	
	}else{ /* insert 성공 */
		
		response.sendRedirect("mainSuccess.do?cnt=" + cnt);
	}
%> --%>
	<c:if test="${cnt != 0}">
		<c:redirect url="mainSuccess.do?cnt=${cnt}" />
	</c:if>
		


</body>
</html>