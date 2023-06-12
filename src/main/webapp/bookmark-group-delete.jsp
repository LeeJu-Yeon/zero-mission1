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

	<h1> 북마크 그룹 삭제 </h1>
	
	<%@ include file="menu.jsp" %>
	
	북마크 그룹을 삭제하시겠습니까?<br>
	<br>
	
	
	<% request.setCharacterEncoding("utf-8"); %>
	<form action="bookmark-group-delete-submit.jsp" method="POST">
		<table>
		
			<colgroup>
	    		<col width="20%">
	    		<col width="80%">
	    	</colgroup>
    	
			<tbody>
				<tr>
					<th> 북마크 그룹 이름 </th>
					<td>
						<input type="text" value="<%= request.getParameter("groupName") %>" readonly>
					</td>
				</tr>
				<tr>
					<th> 순서 </th>
					<td>
						<input type="text" value="<%= request.getParameter("sortOrder") %>" readonly>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<a href="bookmark-group.jsp"> 돌아가기</a> | 
						
						<input type="hidden" name="groupID" value="<%= request.getParameter("groupID") %>">
						<input type="submit" value="삭제">
					</td>
				</tr>
			</tbody>
			
		</table>
	</form>


</body>
</html>