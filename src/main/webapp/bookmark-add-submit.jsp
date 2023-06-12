<%@page import="bookmark.BookmarkService"%>
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
		int groupID = Integer.parseInt( request.getParameter("groupID") );
		String wifiID = request.getParameter("wifiID");
		
		BookmarkService bookmarkService = new BookmarkService();
		
		if ( bookmarkService.addBookmark(groupID, wifiID) ) {
	%>
			<script>
				alert("성공 : 북마크를 추가하였습니다.");
				window.location.href = 'bookmark.jsp';
			</script>
	<%
		} else {
	%>
			<script>
				alert("실패 : 북마크를 다시 추가해 주세요.");
				window.history.back();
			</script>
	<%	
		}
	%>
	
</body>
</html>