<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>登录</title>
		<style>
			body {
				display: flex;
				min-height: 100vh;
				justify-content: center;
				align-items: center;
			}
			form.main {
				width: 300px;
				padding: 20px;
				border: 1px solid #ccc;
				border-radius: 5px;
				background-color: #f9f9f9;
				display: flex;
				flex-direction: column;
				gap: 10px;
				justify-content: center;
				align-items: center;
			}
		</style>
	</head>
	<body>
		<form class="main" action="./user/login" method="post">
			<h1>登录</h1>
			<div>
				<label for="username">用户名:</label>
				<input type="text" id="username" name="username" required />
			</div>
			<div>
				<label for="password">密码:</label>
				<input type="password" id="password" name="password" required />
			</div>
			<p style="color: red;">${msg}</p>
			<div>
				<button type="submit">登录</button>
			</div>
		</form>
	</body>
</html>
