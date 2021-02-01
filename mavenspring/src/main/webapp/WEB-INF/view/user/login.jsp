<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${!empty msg}">
		<script>
			alert("${msg}");
		</script>
	</c:if>
	<form method="post">
		<input type="text" name="userid"/>
		<input type="password" name="password" />
		<input type="submit" value="전송" />
	</form>

</body>
</html>