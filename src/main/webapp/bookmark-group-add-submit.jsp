<%@page import="bookmarkgroup.BookmarkGroupService"%>
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
		String groupName = request.getParameter("groupName");
		int sortOrder = Integer.parseInt( request.getParameter("sortOrder") );
		
		BookmarkGroupService bookmarkGroupService = new BookmarkGroupService();
		
		if ( bookmarkGroupService.addBookmarkGroup(groupName, sortOrder) ) {
	%>
			<script>
				alert("성공 : 북마크 그룹을 추가하였습니다.");
				window.location.href = 'bookmark-group.jsp';
			</script>
	<%
		} else {
	%>
			<script>
				alert("실패 : 북마크 그룹을 다시 추가해 주세요.");
				window.history.back();
			</script>
	<%	
		}
	%>
	
</body>
</html>