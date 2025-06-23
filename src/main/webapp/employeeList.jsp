<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>员工列表</title>
		<style>
			body {
				font-family: Arial, sans-serif;
				margin: 20px;
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;
			}
			form {
				margin-bottom: 20px;
			}
			label {
				margin-right: 10px;
			}
			input,
			select {
				margin-right: 10px;
			}
			table {
				width: 50%;
				min-width: 500px;
				border-collapse: collapse;
			}
			th,
			td {
				padding: 8px;
				text-align: left;
				border-bottom: 1px solid #ddd;
			}
			th {
				background-color: #f2f2f2;
			}
		</style>
	</head>
	<body>
		<form action="/employee/filter" method="get">
			<label for="name">姓名:</label>
			<input type="text" id="name" name="name" value="${name}" />
			<label for="gender">性别:</label>
			<select id="gender" name="gender" >
				<option value="">全部</option>
				<option value="MALE" ${gender.equals("MALE") ? "selected" : ""}>男</option>
				<option value="FEMALE" ${gender.equals("FEMALE") ? "selected" : ""}>女</option>
			</select>
			<label for="department">部门:</label>
			<select id="department" name="department">
				<option value="">全部</option>
                <c:forEach items="${depts}" var="dept">
					<c:if test="${dept.deptId == departmentId}">
						<option value="${dept.deptId}" selected>${dept.name}</option>
					</c:if>
					<c:if test="${dept.deptId != departmentId}">
						<option value="${dept.deptId}">${dept.name}</option>
					</c:if>
				</c:forEach>
			</select>
			<label for="phone">电话:</label>
			<input type="text" id="phone" placeholder="此选项将忽略前方两个选项" name="phone" />
			<button type="submit">筛选</button>
		</form>
		<a href="/employee/addEmployee">添加新员工</a>
		<table>
			<thead>
				<tr>
					<th>ID</th>
					<th>姓名</th>
					<th>性别</th>
					<th>部门</th>
					<th>电话</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${isEmpty==true}">
					<tr>
						<td colspan="6" style="text-align: center;">没有找到符合条件的员工</td>
					</tr>
				</c:if>
				<c:if test="${isEmpty==false}">
                	<c:forEach items="${employees}" var="user">
                	    <tr>
                	        <th scope="row">${user.id}</th>
							<td>${user.name}</td>
							<td>${user.gender=="MALE"?"男":"女"}</td>
							<td>
								<c:forEach items="${depts}" var="dept">
									<c:if test="${dept.deptId == user.dept}">
										${dept.name}
									</c:if>
								</c:forEach>
							</td>
							<td>${user.phone}</td>
							<td>
								<a href="/employee/getById?id=${user.id}">查看详情</a>
								<a href="/employee/deleteEmployee?id=${user.id}">删除该员工</a>
							</td>
                	    </tr>
                	</c:forEach>
				</c:if>
			</tbody>
		</table>
	</body>
</html>
