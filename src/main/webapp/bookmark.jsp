<%@page import="bookmark.Bookmark"%>
<%@page import="bookmark.BookmarkService"%>
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

	<h1> 북마크 리스트 보기 </h1>
	
	<%@ include file="menu.jsp" %>
	
	<table style="text-align: center;">
		<thead>
			<tr>
				<th> 북마크 ID </th>
				<th> 북마크가 속한 그룹 이름 </th>
				<th> 와이파이명 </th>
				<th> 등록일자 </th>
				<th> 비고 </th>
			</tr>
		</thead>
		
		<tbody>
			<%
				BookmarkService bookmarkService = new BookmarkService();
				List<Bookmark> bookmarkList = bookmarkService.getBookmarkList();
				
				if ( bookmarkList.size() == 0 ) {
			%>
					<tr>
						<td colspan="5">
							등록된 북마크가 없습니다.
						</td>
					</tr>
			<%
				} else {
					
					for ( Bookmark item : bookmarkList ) {
			%>
						<tr>
							<td> <%= item.getBookmarkID() %> </td>
							<td> <%= item.getGroupName() %> </td>
							<td>
								<a href="wifi-detail.jsp?wifiID=<%= item.getWifiID() %>">
									<%= item.getWifiName() %>
								</a>
							</td>
							<td> <%= item.getAddDate() %> </td>
							
							<td>
								<form action="bookmark-delete.jsp" method="POST">
									<input type="hidden" name="bookmarkID" value="<%= item.getBookmarkID() %>">
									<input type="hidden" name="groupName" value="<%= item.getGroupName() %>">
									<input type="hidden" name="wifiName" value="<%= item.getWifiName() %>">
									<input type="hidden" name="addDate" value="<%= item.getAddDate() %>">
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