<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="hostSetting.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  

<script src="${project}script.js"></script>

<link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600' rel='stylesheet' type='text/css' />
<link href="/BMS_project/style/style.css" rel="stylesheet" type="text/css" media="screen" />
<!-- <link href="../style/memberjoin.css" rel="stylesheet" type="text/css" media="screen" /> -->
<link href="/BMS_project/style/login.css" rel="stylesheet" type="text/css" media="screen" />


<html>
<body onload="inputFocus();">
<div id="wrapper">
	<div id="header-wrapper">
		<div id="header">
			<%@ include file ="../member/logo.jsp" %>
		</div>
	</div>
	<!-- end #header -->
	<div id="menu">
		<c:if test="${sessionScope.memId == null}">
			<%@ include file="../member/main_menu.jsp" %>
		</c:if>
		<c:if test="${sessionScope.memId != null}">
			<%@ include file="hostMain_menu.jsp" %>
		</c:if>
	</div>
	<!-- end #menu -->
	<div id="page">
		<div id="page-bgtop">
			<div id="page-bgbtm">
				<div width="1100px" align="center">
				<h2>주문상품 목록</h2>
				<hr><br>
				<form action="payPro.mem" name="mycartlist">
					<table style="width:1000px" align="center">
					
						<tr>
							<th colspan="10" align="center" style="height:25px">
								<a href="cart.mem">도서목록보기</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								도서목록(책권수 : ${cnt}) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="mycartlist.mem">내 장바구니 목록보기</a>
								
							</th>
						</tr>
						
						<tr>
							<th style="width:5%">책번호</th>
							<th style="width:10%">표지</th>
							<th style="width:10%">책이름</th>
							<th style="width:10%">판매가</th>
							<th style="width:7%">수량</th>
							<th style="width:10%">합계</th>
							<th style="width:10%">선택</th> 
							
						</tr>
						
						<!-- 게시글 있으면 -->
						<c:if test="${cnt > 0}">
							<c:forEach var="dto" items="${dtos}">
								<tr>
									<td align="center">
										${dto.bookNum}
										<input type="hidden" name="bookNum" value="${dto.bookNum}">
										
									</td>
									
									<td>
										<br>
										<img src="<%=request.getServletContext().getContextPath()%>/host/images/${dto.kimg}" width="100%" height="900%">
									</td>
									
									<td>
										<%-- <a href="cartContent.mem?bookNum=${dto.bookNum}&pageNum=${pageNum}&number=${number+1}">${dto.bookName}</a> --%>
										${dto.bookName}
									</td>
									
									<td align="center">
										${dto.price}
									</td>
									
								    <td align="center">
										<input type="text" name="bookcount" value="${dto.bookcount}">권		
										<input type="hidden" name="payNum" value="${dto.payNum}">
										${dto.payNum}
										<input type="hidden" name="id" value="${memId}">
									</td>
									<td align="center">
										${dto.total}
										
									</td>
									
									<td align="center">
									
										<input class="inputButton" type="button" onclick="window.location='payPro.mem?bookcount=${dto.bookcount}&payNum=${dto.payNum}&id=${memId}&bookNum=${dto.bookNum}'" name="pay" value="결제하기"><br>
										<input class="inputButton" type="button" name="cartdelete" value="삭제하기">
										
									</td>
								</tr>
								<tr>
									
								</tr>
							
							</c:forEach>
						</c:if>
						
						<!-- 게시글 없으면 -->
						<c:if test="${cnt == 0}">
							<tr>
								<td colspan="10" align="center">
									구매목록이 없습니다..!!
								</td>
							</tr>
						</c:if>
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
	<%@ include file ="../member/main_footer.jsp" %>
</div>
<!-- end #footer -->
</body>
</html>