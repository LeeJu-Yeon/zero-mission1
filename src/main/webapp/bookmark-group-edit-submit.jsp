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
		int groupID = Integer.parseInt( request.getParameter("groupID") );
		String groupName = request.getParameter("groupName");
		int sortOrder = Integer.parseInt( request.getParameter("sortOrder") );
		
		BookmarkGroupService bookmarkGroupService = new BookmarkGroupService();
		
		if ( bookmarkGroupService.updateBookmarkGroup(groupID, groupName, sortOrder) ) {
	%>
			<script>
				alert("성공 : 북마크 그룹 정보를 수정하였습니다.");
				window.location.href = 'bookmark-group.jsp';
			</script>
	<%
		} else {
	%>
			<script>
				alert("실패 : 북마크 그룹 정보를 다시 수정해 주세요.");
				window.history.back();
			</script>
	<%	
		}
	%>
	
</body>
</html>