<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>



<!-- <link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600' rel='stylesheet' type='text/css' /> -->
<link href="/BMS_project/style/hoststyle.css" rel="stylesheet" type="text/css" media="screen" />

<html>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script>
	google.charts.load('current', {packages:['corechart']});
</script>
<body>
<div id="wrapper">
	<div id="header-wrapper">
		<div id="header">
			<!-- <div id="logo">
				<h1><a href="#">BMS</a></h1>
				<p>Design by <a href="http://templated.co" rel="nofollow">TEMPLATED</a></p>
				<p>BMS_PROJECT</p>
			</div>  -->
			<%@ include file ="hostlogo.jsp" %> 
		</div>
	</div>
	<!-- end #header -->
	<div id="menu">
		<%@ include file="hostMain_menu.jsp" %> 
	</div>
	<!-- end #menu -->
	<div id="page">
		<div id="page-bgtop">
			<div id="page-bgbtm">
				<div align="center" >
					
				</div>
				<!-- end #content -->
				
				<div>
					<h2><center>결산</center></h2>
					<hr><br>
					<table align="center">
						<tr>
							<th>결산금액 : </th>
							<td>${dto.total}</td>
						</tr>						
					</table>
				
				</div>
				
				<div align="center" style="display: -webkit-box;">
					<div id="firstChat"></div>
				</div>
				<input type="hidden" name="In" value="${dto.bookforeignIn}">
				<input type="hidden" name="Out" value="${dto.bookforeignOut}">
				
				
				<script type="text/javascript">
					var In = document.all.In.value;
					var Out = document.all.Out.value;
					google.charts.setOnLoadCallback(drawChartFirst);
					
					var firstChart_options = {
							title : '구매수량',
							width:600,
							height:400,
							bar:{
								groupWidth : '50%'
							},
							legend:{
								position:'bottom'
							}
					
					};
					function drawChartFirst(){
						var data=google.visualization.arrayToDataTable([
							['Element', '도서별'],
							['국내', (In*1)],
							['국외', (Out*1)]
						]);
						
						var firstChart = new google.visualization.ColumnChart(document.getElementById('firstChat'));
						firstChart.draw(data, firstChart_options);
					}
				
				
				</script>
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				<!-- end #sidebar -->
				<div style="clear: both;">&nbsp;</div>
			</div>
		</div>
	</div>
	<!-- end #page --> 
</div>
<div id="footer">
	<%@ include file ="hostMain_footer.jsp" %>
</div>
<!-- end #footer -->
</body>
</html>