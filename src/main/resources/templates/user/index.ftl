<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户主页</title>
</head>
<body>
    <h3>Hello, Springboot (SSR: freemarker render engine)</h3>
    <h3>Hello, User:${(user.username)!}</h3>
    <form action="/user/upload" enctype="multipart/form-data" method="post">
        <input type="file" name="file"/>
        <input type="submit" value="提交"/>
    </form>
</body>
</html>