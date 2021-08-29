<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="setting.jsp" %>

<script src="${project}script.js"></script>

<link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600' rel='stylesheet' type='text/css' />
<link href="/BMS_project/style/style.css" rel="stylesheet" type="text/css" media="screen" />
<!-- <link href="../style/memberjoin.css" rel="stylesheet" type="text/css" media="screen" /> -->
<link href="/BMS_project/style/login.css" rel="stylesheet" type="text/css" media="screen" />


<html>
<body onload="inputFocus();">

<c:if test="${(directbookCnt*1) >0}">
	<script type="text/javascript">
		alert("구매되었습니다.!!");
	</script>
</c:if>

<c:if test="${(BCnt*1)>0}">
	<script type="text/javascript">
		alert("재고수량을 초과하였습니다!");
	</script>
</c:if>

<div id="wrapper">
	<div id="header-wrapper">
		<div id="header">
			<%@ include file ="logo.jsp" %>
		</div>
	</div>
	<!-- end #header -->
	<div id="menu">
		<%@ include file="main_menu_connect.jsp" %>
	</div>
	<!-- end #menu -->
	<div id="page">
		<div id="page-bgtop">
			<div id="page-bgbtm">
				<div width="1100px" align="center">
					<h2><center>상세페이지</center></h2>
					<hr><br>
					<form name="cartcontent">
						<table align="center" >
							<tr>
								<td rowspan="7">
									<br>
									<img src="<%=request.getServletContext().getContextPath()%>/host/images/${dto.kimg}" width="100%" height="1500%">
								</td>
								<th style="width:150px">책번호</th>
								<td>${dto.bookNum}
									<input type="hidden" name="bookNum" value="${dto.bookNum}">
									<input type="hidden" name="pageNum" value="${pageNum}">
									<input type="hidden" name="id" value="${memId}">
									<input type="hidden" name="bookcount" value="${dto.bookcount}">
									
								</td>
								
							</tr>
							
							<tr>
								<th>제목</th>
								<td>${dto.bookName}</td>
							</tr>
							
							<tr>
								<th>저자</th>
								<td>${dto.author}</td>
							</tr>
							
							<tr>
								<th>출판사</th>
								<td>${dto.publisher}</td>
							</tr>
							
							<tr>
								<th>가격</th>
								<td>${dto.price}</td>
							</tr>
							
							<tr>
								<th>내용</th>
								<td>${dto.content}</td>
							</tr>
							
							<tr>
								<th>권수</th>
								<%-- <td>${dto.bookcount}</td> --%>
								<td><input type="text" name="cartcount">권</td>
							</tr>
							
							<tr>
							
								<th colspan="3" align="center">
									<input class="inputButton" type="button" value="장바구니담기" 
										   onclick="cartAdd();">
										  
									<input class="inputButton" type="button" value="바로구매"
										   onclick="directpayCheck();">	   
										   
									<input class="inputButton" type="button" value="목록보기"
											onclick="window.location='cart.mem?pageNum=${pageNum}'">
								</th>
							
							</tr>
							
						
						</table>
					</form>
				
				
				<!-- end #sidebar -->
				<div style="clear: both;">&nbsp;</div> 
			</div>
		</div>
	</div>
	<!-- end #page --> 
</div>
<div id="footer">
	<%@ include file ="main_footer.jsp" %>
</div>
<!-- end #footer -->
</body>
</html>