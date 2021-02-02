<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	*{
		margin: 0;
		padding: 0;
		box-sizing: border-box;
	}
	
	.box{
		width: 400px;
		box-shadow: 0px 0px 3px 0px rgba(0,0,0,.1);
		background-color: white;
		padding: 50px;
	}
	
	.box > h1{
		text-align: center;
	}

	body{
		display: flex;
		height: 100vh;
		justify-content: center;
		align-items: center;
		position: relative;
		background-color: #222;
	}

	.btn{
		background-color: #ff5555;
		color: white;
		border: none;
		border-radius: 3px;
		padding: 5px 13px;
		margin-top: 10px;
		font-size: 18px;
	}
	
	form > .input_box{
		width: 100%;
		margin: 20px 0;
	}
	
	form > .input_box > input{
		border: none;
		border-radius: 3px;
		width: 100%;
		padding: 15px;
		background-color: rgb(245,245,245);
	}
	
	.btn_box{
		margin-top: 15px;
		text-align: center;
	}
</style>
</head>
<body>
	<c:if test="${!empty msg}">
		<script>
			alert("${msg}");
		</script>
	</c:if>
	<div class="box">
		<h1>회원가입</h1>
		<form method="post">
			<div class="input_box">
				<input type="text" name="userid" placeholder="아이디"/>
			</div>
			<div class="input_box">
				<input type="text" name="name" placeholder="유저 이름"/>
			</div>
			<div class="input_box">
				<input type="password" name="password" placeholder="비밀번호" />
			</div>
			<div class="input_box">
				<input type="password" name="password_ok" placeholder="비밀번호 확인"/>
			</div>
			<div class="btn_box">
				<input type="submit" value="회원가입" class="btn"/>
			</div>
		</form>
	</div>
</body>
</html>