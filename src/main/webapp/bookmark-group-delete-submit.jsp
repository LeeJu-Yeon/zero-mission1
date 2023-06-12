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
		
		BookmarkGroupService bookmarkGroupService = new BookmarkGroupService();
		
		if ( bookmarkGroupService.deleteBookmarkGroup(groupID) ) {
	%>
			<script>
				alert("성공 : 북마크 그룹을 삭제하였습니다.");
				window.location.href = 'bookmark-group.jsp';
			</script>
	<%
		} else {
	%>
			<script>
				alert("실패 : 해당 그룹에 북마크가 존재합니다. 빈 그룹만 삭제 가능합니다. 북마크를 먼저 삭제 후 다시 시도해 주세요.");
				window.history.back();
			</script>
	<%	
		}
	%>
	
</body>
</html>