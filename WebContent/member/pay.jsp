<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="setting.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  

<script src="${project}script.js"></script>

<link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600' rel='stylesheet' type='text/css' />
<link href="/bms_project/resources/style/style.css" rel="stylesheet" type="text/css" media="screen" />
<!-- <link href="../style/memberjoin.css" rel="stylesheet" type="text/css" media="screen" /> -->
<link href="/bms_project/resources/style/login.css" rel="stylesheet" type="text/css" media="screen" />


<html>
<body onload="inputFocus();">

<c:if test="${refundCnt ==0 }">
	<script type="text/javascript">
		alert("환불신청에 실패하였습니다.다시 시도해주세요");
	</script>
</c:if>

<c:if test="${paycancelcnt ==0}">
	<script type="text/javascript">
		alert("구매취소에 실패하였습니다.다시 시도해주세요");
	</script>
</c:if>

<%-- <c:if test="${Dcnt == null}">
	<script type="text/javascript">
		alert("구매취소되었습니다.");
		
	</script>
</c:if> --%>

<div id="wrapper">
	
	<div id="header-wrapper">
		<div id="header">
			<%@ include file ="logo.jsp" %>
		</div>
	</div>
	<!-- end #header -->
	<div id="menu">
		<c:if test="${sessionScope.memId == null}">
			<%@ include file="main_menu.jsp" %>
		</c:if>
		<c:if test="${sessionScope.memId != null}">
			<%@ include file="main_menu_connect.jsp" %>
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
							<th style="width:8%">상태</th>
							<th style="width:10%">선택</th> 
							
						</tr>
						
						<!-- 게시글 있으면 -->
						<c:if test="${cnt > 0}">
							<c:forEach var="dto" items="${dtos}">
								<c:if test="${(dto.step*1)!=4}">
								<tr>
									<td align="center">
										${dto.bookNum}
										<input type="hidden" name="bookNum" value="${dto.bookNum}">
										
									</td>
									
									<td>
										<br>
										<img src="/bms_project/resources/images/${dto.kimg}" width="100%" height="900%">
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
									
										<input type="hidden" name="id" value="${memId}">
									</td>
									<td align="center">
										${dto.total}
										
									</td>
									<td align="center">
										<c:if test="${(dto.step*1)==1}">
											배송대기중
										</c:if>
										<c:if test="${(dto.step*1)==2}">
											배송완료
										</c:if>
										<c:if test="${(dto.step*1)==3}"><!-- 환불할떄  -->
											환불대기중
										</c:if>
									</td>
									<c:if test="${(dto.step*1)==1}">
										<td align="center">
											<input class="inputButton" type="button" onclick="window.location='paycancel.mem?bookNum=${dto.bookNum}&bookcount=${dto.bookcount}&payNum=${dto.payNum}'" name="pay" value="구매취소"><br>
										</td>
									</c:if>
									<c:if test="${(dto.step*1)==2}">
										<td align="center">	
											<input class="inputButton" type="button" onclick="window.location='refund.mem?payNum=${dto.payNum}'" name="pay" value="환불신청"><br>
											<input class="inputButton" type="button" onclick="window.location=''" name="pay" value="구매확정"><br>
										</td>
									</c:if>
									
								</tr>
								</c:if>
								
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
	<%@ include file ="main_footer.jsp" %>
</div>
<!-- end #footer -->
</body>
</html>