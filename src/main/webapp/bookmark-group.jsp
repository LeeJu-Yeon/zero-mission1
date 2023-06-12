<%@page import="bookmarkgroup.BookmarkGroup"%>
<%@page import="bookmarkgroup.BookmarkGroupService"%>
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

	<h1> 북마크 그룹 관리 </h1>
	
	<%@ include file="menu.jsp" %>
	
	<a href="bookmark-group-add.jsp">
		<button> 북마크 그룹 추가하기 </button>
	</a>
	<br>
	<br>
	
	<table style="text-align: center;">
		<thead>
			<tr>
				<th> 북마크 그룹 ID </th>
				<th> 북마크 그룹 이름 </th>
				<th> 정렬 순서 </th>
				<th> 생성일자 </th>
				<th> 수정일자 </th>
				<th> 비고 </th>
			</tr>
		</thead>
		
		<tbody>
			<%
				BookmarkGroupService bookmarkGroupService = new BookmarkGroupService();
				List<BookmarkGroup> groupList = bookmarkGroupService.getBookmarkGroupList();
				
				if ( groupList.size() != 0 ) {
					for ( BookmarkGroup item : groupList ) {
			%>
						<tr>
							<td> <%= item.getGroupID() %> </td>
							<td> <%= item.getGroupName() %> </td>
							<td> <%= item.getSortOrder() %> </td>
							<td> <%= item.getCreateDate() %> </td>
							<td> <%= (item.getUpdateDate() == null) ? "" : item.getUpdateDate() %> </td>
							
							<td>
								<form id="editForm" action="bookmark-group-edit.jsp" method="POST" style="display: inline;">
									<input type="hidden" name="groupID" value="<%= item.getGroupID() %>">
									<input type="hidden" name="groupName" value="<%= item.getGroupName() %>">
									<input type="hidden" name="sortOrder" value="<%= item.getSortOrder() %>">
									<input type="submit" value="수정">
								</form>
								
								<form id="deleteForm" action="bookmark-group-delete.jsp" method="POST" style="display: inline;">
									<input type="hidden" name="groupID" value="<%= item.getGroupID() %>">
									<input type="hidden" name="groupName" value="<%= item.getGroupName() %>">
									<input type="hidden" name="sortOrder" value="<%= item.getSortOrder() %>">
									<input type="submit" value="삭제">
								</form>
							</td>
						</tr>
			<%
					}
				} else {
			%>
					<tr>
						<td colspan="6">
							생성된 북마크 그룹이 없습니다.
						</td>
					</tr>
			<%
				}
			%>	
		</tbody>
	</table>

</body>
</html>