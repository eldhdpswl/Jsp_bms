<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="setting.jsp" %>

<script src="${project}script.js"></script>
<link href="/BMS_project/style/login.css" rel="stylesheet" type="text/css" media="screen" /> 

<html>
<body onload="confirmIdFocus();"> <!-- onload는 페이지에 들어오자마자 해야할일을 정해주는 -->
	<h2>중복확인 페이지</h2>
	<%-- <%
		String id = (String) request.getAttribute("id");
		int cnt = (Integer)request.getAttribute("cnt");
	%> --%>
	
	<form action="confirmId.mem" method="post" name="confirmform"
			onsubmit="return confirmIdCheck();">
		<%-- <%	//id중복
			if(cnt==1){
		%> --%>
		<c:if test="${cnt==1}">
			<table>
				<tr>
					<th colspan="2">
						<span>${id}</span>사용할 수 없습니다.
					</th>
				
				</tr>
				<tr>
					<th>아이디 : </th>
					<td><input class="input" type="text" name="id" maxlength="20" 
								style="width:100px">
					</td>
				</tr>
				<tr>
					<th colspan="2">
						<input class="inputButton" type="submit" value="확인">
						<input class="inputButton" type="reset" value="취소"
								onclick="self.close();">
						
					</th>
				</tr>
				
				
			</table>
		</c:if>			
		<%-- <%
			//id중복이 아닌 경우
			} else{
		%>		 --%>
		<c:if test="${cnt != 1}">
				<table>
					<tr>
						<td align="center">
							<span>${id}</span>는 사용할 수 있습니다.
						</td>
					</tr>
					<tr>
						<th>
							<input class="inputButton" type="button" value="확인" 
									onclick="setId('${id}')">
						
						</th>
					
					</tr>
				
				</table>
		</c:if>
		<%-- <% 		
			}
		
		%>	 --%>
		
	
	
	</form>
	
	
</body>
</html>