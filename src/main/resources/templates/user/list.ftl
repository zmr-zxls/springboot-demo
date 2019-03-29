<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>y用户列表</title>
    <style>
        table {
            border-collapse: collapse;
            width: 800px;
            margin: 50px auto;
        }
        table td,
        table th {
            padding: 8px;
            border: 1px solid black;
        }
    </style>
</head>
<body>
    <table>
        <thead>
            <tr>
                <th>姓名</th>
                <th>密码</th>
                <th>手机号码</th>
            </tr>
        </thead>
        <tbody>
            <#list users as user>
                <tr>
                    <td>${user.username}</td>
                    <td>${user.password}</td>
                    <td>${user.phoneNumber}</td>
                </tr>
            </#list>
        </tbody>
    </table>
</body>
</html>