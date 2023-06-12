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

	<h1> 북마크 삭제 </h1>
	
	<%@ include file="menu.jsp" %>
	
	북마크를 삭제하시겠습니까?<br>
	<br>
	
	
	<% request.setCharacterEncoding("utf-8"); %>
	<form action="bookmark-delete-submit.jsp" method="POST">
		<table>
		
			<colgroup>
	    		<col width="20%">
	    		<col width="80%">
	    	</colgroup>
	   	
			<tbody>
				<tr>
					<th> 북마크가 속한 그룹 이름 </th>
					<td> <%= request.getParameter("groupName") %> </td>
				</tr>
				<tr>
					<th> 와이파이명 </th>
					<td> <%= request.getParameter("wifiName") %> </td>
				</tr>
				<tr>
					<th> 등록일자 </th>
					<td> <%= request.getParameter("addDate") %> </td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<a href="bookmark.jsp">돌아가기</a> | 
						
						<input type="hidden" name="bookmarkID" value="<%= request.getParameter("bookmarkID") %>">
						<input type="submit" value="삭제">
					</td>
				</tr>
			</tbody>
			
		</table>
	</form>
	
	
</body>
</html>