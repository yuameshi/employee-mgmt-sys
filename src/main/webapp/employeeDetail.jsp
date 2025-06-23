<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>员工详情</title>
		<style>
			body {
				display: flex;
				flex-direction: column;
				align-items: center;
				padding: 100px;
				min-height: 100vh;
			}
			table {
				border-collapse: collapse;
				border: 2px solid rgb(140 140 140);
				font-family: sans-serif;
				font-size: 0.8rem;
				letter-spacing: 1px;
			}

			caption {
				caption-side: bottom;
				padding: 10px;
				font-weight: bold;
			}

			thead,
			tfoot {
				background-color: rgb(228 240 245);
			}

			th,
			td {
				border: 1px solid rgb(160 160 160);
				padding: 8px 10px;
			}

			td:last-of-type {
				padding: 8px 45px;
				text-align: center;
			}

			tfoot th {
				text-align: right;
			}

			tfoot td {
				font-weight: bold;
			}
		</style>
	</head>
	<body>
		<h1>员工详情</h1>
		<table>
			<thead>
				<tr>
					<th>属性</th>
					<th>值</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>员工编号</td>
					<td>${employee.id}</td>
				</tr>
				<tr>
					<td>员工姓名</td>
					<td>${employee.name}</td>
				</tr>
				<tr>
					<td>员工性别</td>
					<td>${employee.gender=="MALE"?"男":"女"}</td>
				</tr>
				<tr>
					<td>手机号</td>
					<td>${employee.phone}</td>
				</tr>
				<tr>
					<td>部门</td>
					<td>${department.name}</td>
				</tr>
				<tr>
					<td>招聘日期</td>
					<td>${hireDateStr}</td>
				</tr>
				<tr>
					<td>创建时间</td>
					<td>${createDateStr}</td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<th scope="row" colspan="2">
						<a href="/employee/filter">返回员工列表</a>
					</th>
				</tr>
			</tfoot>
		</table>
	</body>
</html>
