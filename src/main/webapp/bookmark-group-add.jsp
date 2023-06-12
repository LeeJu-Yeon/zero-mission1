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

	<h1> 북마크 그룹 추가하기 </h1>
	
	<%@ include file="menu.jsp" %>
	
	
	<form action="bookmark-group-add-submit.jsp" method="POST">
		<table>
		
			<colgroup>
	    		<col width="20%">
	    		<col width="80%">
	    	</colgroup>
	    	
			<tbody>
				<tr>
					<th> 북마크 그룹 이름 </th>
					<td>
						<input type="text" name="groupName" required>
					</td>
				</tr>
				<tr>
					<th> 정렬 순서 </th>
					<td>
						<input type="text" name="sortOrder" required
							   pattern="[0-9]{1,5}" title="5자리 이하의 숫자만 입력해 주세요.">
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" value="추가">
					</td>
				</tr>
			</tbody>
			
		</table>
	</form>
	
	
</body>
</html>