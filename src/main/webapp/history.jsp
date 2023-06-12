<%@page import="searchhistory.SearchHistory"%>
<%@page import="searchhistory.SearchHistoryService"%>
<%@page import="java.util.List"%>
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

	<h1> 위치 히스토리 목록 </h1>
	
	<%@ include file="menu.jsp" %>
	
	<table style="text-align: center;">
		<thead>
			<tr>
				<th> 번호 </th>
				<th> 위도 </th>
				<th> 경도 </th>
				<th> 조회일자 </th>
				<th> 비고 </th>
			</tr>
		</thead>
		
		<tbody>
			<%
				SearchHistoryService searchHistoryService = new SearchHistoryService();
				List<SearchHistory> historyList = searchHistoryService.getSearchHistoryList();
				
				if ( historyList.size() == 0 ) {
			%>
					<tr>
						<td colspan="5">
							검색 기록이 없습니다.
						</td>
					</tr>
			<%
				} else {
					
					for ( SearchHistory item : historyList ) {
			%>
						<tr>
							<td> <%= item.getSearchID() %> </td>
							<td> <%= String.format( "%.7f", Double.parseDouble(item.getSearchLatitude()) ) %> </td>
							<td> <%= String.format( "%.7f", Double.parseDouble(item.getSearchLongitude()) ) %> </td>
							<td> <%= item.getSearchDate() %> </td>
							<td>
								<form action="history-delete-submit.jsp" method="POST">
									<input type="hidden" name="searchID" value="<%= item.getSearchID() %>">
									<input type="submit" value="삭제">
								</form>
							</td>
						</tr>
			<%
					}
				}
			%>
		</tbody>
	</table>

</body>
</html>