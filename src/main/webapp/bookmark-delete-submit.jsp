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
		int bookmarkID = Integer.parseInt( request.getParameter("bookmarkID") );
		
		BookmarkService bookmarkService = new BookmarkService();
		
		if ( bookmarkService.deleteBookmark(bookmarkID) ) {
	%>
			<script>
				alert("성공 : 북마크를 삭제하였습니다.");
				window.location.href = 'bookmark.jsp';
			</script>
	<%
		} else {
	%>
			<script>
				alert("실패 : 북마크를 다시 삭제해 주세요.");
				window.history.back();
			</script>
	<%	
		}
	%>
	
</body>
</html>