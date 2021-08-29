<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- <%@ include file="../member/setting.jsp" %>  --%>

<%@ include file="b_setting.jsp" %>
<link href="/BMS_project/style/hoststyle.css" rel="stylesheet" type="text/css" media="screen" />

<html>
<body>
	<h2><center>게시판</center></h2>
	<table style="width:1000px" align="center">
		<tr>
			<th colspan="6" align="center" style="height:25px">
				글목록(글갯수 : ${cnt})  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="b_writeForm.bo">글쓰기</a>
			</th>
		
		</tr>
		
		<tr>
			<th style="width:15%">글번호(num/ref/step/level)</th>
			<th style="width:25%">글제목</th>
			<th style="width:10%">작성자</th>
			<th style="width:15%">작성일</th>
			<th style="width:5%">조회수</th>
		</tr>
		
		<!-- 게시글 있으면 -->
		<c:if test="${cnt > 0}">
			<c:forEach var="dto" items="${dtos}">
				<tr>
					<td align="center">
						${number}
						<c:set var="number" value="${number-1}"/>
						(${dto.num} / ${dto.ref_step} / ${dto.ref_level})
					</td>
					
					<td >
						<!-- 추가 -->
						<c:if test="${dto.ref_level > 1}"><!-- 들여쓰기 > 1 -->
							<c:set var="wid" value="${(dto.ref_level-1) * 10}"/>
							<img src="${projectboard}images/level.gif" border="0" width="${wid} height="15">
						</c:if>
						
						<!-- 들여쓰기 > 0 : 답변글 -->
						<c:if test="${dto.ref_level > 0}">
							<img src="${projectboard}images/re.gif" border="0" width="20" height="15">
						</c:if>
						
						
						<!-- 추가END -->
						<a href="b_contentForm.bo?num=${dto.num}&pageNum=${pageNum}&number=${number+1}">${dto.subject}</a>
						
						<!-- hot 이미지 -->
						<c:if test="${dto.readCnt > 10}">
							<img src="${projectboard}images/hot.gif" border="0" width="20" height="15">
						</c:if>
					</td>
					
					<td align="center">
						${dto.writer}					
					</td>
					
					<td align="center">
						<fmt:formatDate type="both" pattern="yyyy-MM-dd HH:mm" value="${dto.reg_date}"/>
						
					</td>
					
					<td align="center">
						${dto.readCnt}
					</td>
					
				</tr>
			
			</c:forEach>
		</c:if>
		
		<!-- 게시글 없으면 -->
		<c:if test="${cnt == 0}">
			<tr>
				<td colspan="6" align="center">
					게시글이 없습니다. 글을 작성해주세요.!!
				</td>
			</tr>
		</c:if>
		
	</table>
	
	<!-- 페이지컨트롤 -->
	<table style="width:1000px" align="center">
		<th align="center">
			<c:if test="${cnt > 0}">
				<!-- 처음[◀◀] / 이전블록[◀] 특수문자 : ㅁ한자키 -->
				<c:if test="${startPage > pageBlock}">
					<a href="boardList.bo">[◀◀]</a>
					<a href="boardList.bo?pageNum=${startPage - pageBlock}">[◀]</a>
				</c:if>
				
				<c:forEach var="i" begin="${startPage}" end="${endPage}">
					<c:if test="${i == currentPage}">
						<span><b>[${i}]</b></span>
					
					</c:if>
					
					<c:if test="${i != currentPage}">
						<a href="boardList.bo?pageNum=${i}">[${i}]</a>
					</c:if>
				</c:forEach>
				
				<!-- 다음블록[▶] / 끝[▶▶] -->
				<c:if test="${pageCount > endPage}">
					<a href="boardList.bo?pageNum=${startPage + pageBlock}">[▶]</a>
					<a href="boardList.bo?pageNum=${pageCount}">[▶▶]</a>
				</c:if>
				
				
				
			
			</c:if>
		</th>
	</table>
	
	
	
</body>
</html>