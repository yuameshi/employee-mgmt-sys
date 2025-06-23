<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>添加员工</title>
		<style>
			body {
				display: flex;
				flex-direction: column;
				align-items: center;
				padding: 100px;
				min-height: 100vh;
			}
		</style>
	</head>
	<body>
		<h1>添加员工</h1>
		<form action="/employee/addEmployeeSubmit" method="post">
			<div>
				<label for="name">姓名:</label>
				<input type="text" id="name" name="name" required />
			</div>
			<div>
				<label for="phone">电话:</label>
				<input type="text" id="phone" name="phone" required />
			</div>
			<div>
				<label for="gender">性别:</label>
				<select id="gender" name="gender" required>
					<option value="">请选择性别</option>
					<option value="MALE">男</option>
					<option value="FEMALE">女</option>
				</select>
			</div>
			<div>
				<label for="email">邮箱:</label>
				<input type="email" id="email" name="email" required />
			</div>
			<div>
				<label for="dept">部门:</label>
				<select id="dept" name="dept" required>
					<option value="">请选择部门</option>
					<c:forEach items="${depts}" var="dept">
						<option value="${dept.deptId}">${dept.name}</option>
					</c:forEach>
				</select>
			</div>
			<div>
				<label for="hireDate">入职日期:</label>
				<input type="date" id="hireDate" name="hireDate" required />
			</div>
			<div>
				<button type="submit">添加员工</button>
			</div>
		</form>
	</body>
</html>
