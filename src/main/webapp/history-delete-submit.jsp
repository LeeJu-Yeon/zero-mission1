<%@page import="searchhistory.SearchHistoryService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>와이파이 정보 구하기</title>
</head>
<body>

	<%
		request.setCharacterEncoding("utf-8");
		int searchID = Integer.parseInt( request.getParameter("searchID") );
	
		SearchHistoryService searchHistoryService = new SearchHistoryService();
		
		if ( searchHistoryService.deleteSearchHistory(searchID) ) {
	%>
			<script>
				alert("성공 : 해당 검색 기록을 삭제하였습니다.");
				window.location.href = 'history.jsp';
			</script>
	<%
		} else {
	%>
			<script>
				alert("실패 : 해당 검색 기록을 다시 삭제해 주세요.");
				window.location.href = 'history.jsp';
			</script>
	<%	
		}
	%>
	
</body>
</html>