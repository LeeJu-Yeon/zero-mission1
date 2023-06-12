<%@page import="wifi.WifiService"%>
<%@page import="wifi.WiFi"%>
<%@page import="searchhistory.SearchHistoryService"%>
<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>와이파이 정보 구하기</title>
	<link rel="stylesheet" type="text/css" href="CSS/styles.css">
</head>
<body>

	<h1> 와이파이 정보 구하기 </h1>
	
	<%@ include file="menu.jsp" %>
	
	
	<form action="home.jsp" method="GET">
	
		<label for="latitude"> 위도 : </label>
		<input type="text" id="latitude" name="latitude" value="<%= (request.getParameter("latitude") == null) ? 0.0 : request.getParameter("latitude") %>"
			   pattern="[0-9]+(\.[0-9]+)?" required>
		
		<label for="longitude"> 경도 : </label>
		<input type="text" id="longitude" name="longitude" value="<%= (request.getParameter("longitude") == null) ? 0.0 : request.getParameter("longitude") %>"
			   pattern="[0-9]+(\.[0-9]+)?" required>
		
		<input type="button" value="내 위치 가져오기" onclick="getLocation()">
		<input type="submit" value="근처 WiFi 정보 보기">
		
	</form>
	<br>
	
	
	<script>
		function getLocation() {
			if ( navigator.geolocation ) {
				navigator.geolocation.getCurrentPosition(showPosition);
			} else {
				alert("위치 정보를 가져올 수 없습니다.");
			}
		}
		
		function showPosition( position ) {
			document.getElementById("latitude").value = position.coords.latitude;
			document.getElementById("longitude").value = position.coords.longitude;
		}
	</script>
	
	
	<table>
		<thead>
			<tr>
				<th> 거리 </th>
				<th> 관리번호 </th>
				<th> 자치구 </th>
				<th> 와이파이명 </th>
				<th> 도로명주소 </th>
				<th> 상세주소 </th>
				<th> 설치위치(층) </th>
				<th> 설치유형 </th>
				<th> 설치기관 </th>
				<th> 서비스구분 </th>
				<th> 망종류 </th>
				<th> 설치년도 </th>
				<th> 실내외구분 </th>
				<th> WiFi접속환경 </th>
				<th> 위도 </th>
				<th> 경도 </th>
				<th> 작업일자 </th>
			</tr>
		</thead>
		
		<tbody>
			<%
				String inputLatitudeStr = (request.getParameter("latitude") == null) ? "0.0" : request.getParameter("latitude");
				String inputLongitudeStr = (request.getParameter("longitude") == null) ? "0.0" : request.getParameter("longitude");
				
				double inputLatitude = Double.parseDouble(inputLatitudeStr);
				double inputLongitude = Double.parseDouble(inputLongitudeStr);
				
				if ( 37.4283 <= inputLatitude && inputLatitude <= 37.7016 && 126.7643 <= inputLongitude && inputLongitude <= 127.1840 ) {
					
					WifiService wifiService = new WifiService();
					List<WiFi> closeWifiList = wifiService.getCloseWifiList(inputLatitude, inputLongitude);
					
					if ( closeWifiList.size() == 0 ) {
			%>
						<tr>
							<td colspan="17" align="center">
								저장된 WiFi 데이터가 없습니다.<br>
								Open API 와이파이 정보 가져오기 메뉴를 먼저 클릭해 주세요.<br>
							</td>
						</tr>
			<%
					} else {
						
						SearchHistoryService searchHistoryService = new SearchHistoryService();
						searchHistoryService.saveSearchHistory(inputLatitude, inputLongitude);
						
						for ( WiFi wifi : closeWifiList ) {
			%>
							<tr>
								<td> <%= Math.round( Double.parseDouble(wifi.getDistance()) * 1000 ) %> m </td>
								<td> <%= wifi.getWifiID() %> </td>
								<td> <%= wifi.getGu() %> </td>
								<td>
									<a href="wifi-detail.jsp?wifiID=<%= wifi.getWifiID() %>">
										<%= wifi.getWifiName() %>
									</a>
								</td>
								<td> <%= wifi.getRoadAddress() %> </td>
								<td> <%= wifi.getDetailAddress() %> </td>
								<td> <%= wifi.getInstallFloor() %> </td>
								<td> <%= wifi.getInstallType() %> </td>
								<td> <%= wifi.getInstallAgency() %> </td>
								<td> <%= wifi.getServiceType() %> </td>
								<td> <%= wifi.getNetworkType() %> </td>
								<td> <%= wifi.getInstallYear() %> </td>
								<td> <%= wifi.getIndoorOutdoor() %> </td>
								<td> <%= wifi.getConnectionDetail() %> </td>
								<td> <%= String.format( "%.6f", Double.parseDouble(wifi.getLatitude()) ) %> </td>
								<td> <%= String.format( "%.6f", Double.parseDouble(wifi.getLongitude()) ) %> </td>
								<td> <%= wifi.getWorkDate() %> </td>
							</tr>
			<%
						}
					}
					
				} else {
			%>
					<tr>
						<td colspan="17" align="center">
							올바른 위치 정보를 입력한 후에 조회해 주세요.<br>
							서울의 위도는 37.428300 ~ 37.701600 사이입니다.<br>
							서울의 경도는 126.764300 ~ 127.184000 사이입니다.<br>
						</td>
					</tr>
			<%
				}
			%>
		</tbody>
		
	</table>

</body>
</html>