<%@page import="bookmarkgroup.BookmarkGroup"%>
<%@page import="bookmarkgroup.BookmarkGroupService"%>
<%@page import="java.util.List"%>
<%@page import="wifi.WifiService"%>
<%@page import="wifi.WiFi"%>
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

	<h1> 와이파이 상세정보 </h1>
	
	<%@ include file="menu.jsp" %>
	
	
	<form action="bookmark-add-submit.jsp" method="POST">
	
		<select name="groupID" required>
			<option value="" selected disabled> 북마크 그룹 이름 선택 </option>
			<%
				BookmarkGroupService bookmarkGroupService = new BookmarkGroupService();
				List<BookmarkGroup> groupList = bookmarkGroupService.getBookmarkGroupList();
				
				if ( groupList.size() != 0 ) {
					for ( BookmarkGroup item : groupList ) {
			%>
						<option value="<%= item.getGroupID() %>"> <%= item.getGroupName() %> </option>
			<%
					}
				}
			%>
	    </select>
	    
	    <input type="hidden" name="wifiID" value="<%= request.getParameter("wifiID") %>">
	    <input type="submit" value="북마크 추가하기">
	    
    </form>
    <br>
    
    
    <%
    	String wifiID = request.getParameter("wifiID");
    
    	WifiService wifiService = new WifiService();
    	WiFi wifi = wifiService.getDetailWifi(wifiID);
    %>
    
    <table>
    
	    <colgroup>
	    	<col width="20%">
	    	<col width="80%">
	    </colgroup>
	    
    	<tbody>
    		<tr>
    			<th> 관리번호 </th>
    			<td> <%= wifi.getWifiID() %> </td>
    		</tr>
    		<tr>
    			<th> 자치구 </th>
    			<td> <%= wifi.getGu() %> </td>
    		</tr>
    		<tr>
    			<th> 와이파이명 </th>
    			<td> <%= wifi.getWifiName() %> </td>
    		</tr>
    		<tr>
    			<th> 도로명주소 </th>
    			<td> <%= wifi.getRoadAddress() %> </td>
    		</tr>
    		<tr>
    			<th> 상세주소 </th>
    			<td> <%= wifi.getDetailAddress() %> </td>
    		</tr>
    		<tr>
    			<th> 설치위치(층) </th>
    			<td> <%= wifi.getInstallFloor() %> </td>
    		</tr>
    		<tr>
    			<th> 설치유형 </th>
    			<td> <%= wifi.getInstallType() %> </td>
    		</tr>
    		<tr>
    			<th> 설치기관 </th>
    			<td> <%= wifi.getInstallAgency() %> </td>
    		</tr>
    		<tr>
    			<th> 서비스구분 </th>
    			<td> <%= wifi.getServiceType() %> </td>
    		</tr>
    		<tr>
    			<th> 망종류 </th>
    			<td> <%= wifi.getNetworkType() %> </td>
    		</tr>
    		<tr>
    			<th> 설치년도 </th>
    			<td> <%= wifi.getInstallYear() %> </td>
    		</tr>
    		<tr>
    			<th> 실내외구분 </th>
    			<td> <%= wifi.getIndoorOutdoor() %> </td>
    		</tr>
    		<tr>
    			<th> WIFI 접속환경 </th>
    			<td> <%= wifi.getConnectionDetail() %> </td>
    		</tr>
    		<tr>
    			<th> 위도 </th>
    			<td> <%= wifi.getLatitude() %> </td>
    		</tr>
    		<tr>
    			<th> 경도 </th>
    			<td> <%= wifi.getLongitude() %> </td>
    		</tr>
    		<tr>
    			<th> 작업일자 </th>
    			<td> <%= wifi.getWorkDate() %> </td>
    		</tr>
    	</tbody>
    	
    </table>

</body>
</html>