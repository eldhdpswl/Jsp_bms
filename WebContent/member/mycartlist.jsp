<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="setting.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  

<script src="${project}script.js"></script>

<link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600' rel='stylesheet' type='text/css' />
<link href="/BMS_project/style/style.css" rel="stylesheet" type="text/css" media="screen" />
<!-- <link href="../style/memberjoin.css" rel="stylesheet" type="text/css" media="screen" /> -->
<link href="/BMS_project/style/login.css" rel="stylesheet" type="text/css" media="screen" />


<html>
<body onload="inputFocus();">
<c:if test="${error==1}">
	<script type="text/javascript">
		alert("구매할 책을 선택해주세요");
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
				<h2>MY 장바구니 LIST</h2>
				<hr><br>
				<form action="pay.mem" name="mycartlist" onsubmit="return paylistcheck();">
					<table style="width:1000px" align="center">
						<tr>
							<th colspan="10" align="center" style="height:25px">
								<a href="cart.mem">도서목록보기</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								도서목록(책권수 : ${cnt}) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="paylist.mem">구매목록</a>
								
								
							</th>
						</tr>
						
						<tr>
							<th style="width:5%">선택</th>
							<th style="width:10%">책번호</th>
							<th style="width:20%">표지</th>
							<th style="width:15%">책이름</th>
							<th style="width:10%">판매가</th>
							<th style="width:10%">수량</th>
							<th style="width:10%">합계</th>
							<!-- <th style="width:10%">선택</th> -->
							
						</tr>
					
						<!-- 게시글 있으면 -->
						
						<c:if test="${cnt > 0}">
							<c:forEach var="dto" items="${dtos}">
								<tr>
								
									<th align="center" >
										<input type="checkbox" name="cartNum" value="${dto.cartNum}">
									</th>
									
									<td align="center">
										${dto.bookNum}
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
										<input type="text" name="mycartcount" value="${dto.bookcount}">권				
									</td>
									<td align="center">
										${dto.total}
										
										<input type="hidden" name="id" value="${memId}">
										<input type="hidden" name="bookNum" value="${dto.bookNum}">
										<input type="hidden" name="kimg" value="${dto.kimg}">
										<input type="hidden" name="bookName" value="${dto.bookName}">
										<input type="hidden" name="price" value="${dto.price}">
										<input type="hidden" name="total" value="${dto.total}">
									</td>
									
									<!-- <td align="center">
										<input class="inputButton" type="button" name="pay();" value="결제하기"><br>
										<input class="inputButton" type="button" name="cartdelete" value="삭제하기">
									</td> -->
								</tr>
							
							</c:forEach>
						</c:if>
						
						<!-- 게시글 없으면 -->
						<c:if test="${cnt == 0}">
							<tr>
								<td colspan="10" align="center">
									게시글이 없습니다. 글을 작성해주세요.!!
								</td>
							</tr>
						</c:if>
					</table>
					
					<!-- 페이지컨트롤 -->
					<table style="width:1000px" align="center">
						<th align="center">
							<!-- 체크한 값이 없을때 -->
							<c:if test="${cnt == 0}">
								<input class="inputbutton" type="submit" name="payment" value="결제하기" disabled>
								<input class="inputbutton" type="button" name="cartdelete" value="삭제하기" disabled>
							</c:if>
							
							<!-- 체크한 값이 있을떄 -->
							<c:if test="${cnt != 0}">
								<input class="inputbutton" type="submit" name="payment" value="결제하기">
								<input class="inputbutton" type="button" name="cartdelete" value="삭제하기">
																	<!-- onclick="cartdelete();" -->
							</c:if>
						</th>
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