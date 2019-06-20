<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>用户登录</title>
</head>
<body>
  <p>用户登录</p>
  <form action="/user/login" method="POST">
    用户：<input name="name"/>
    密码：<input type="password" name="password"/>
    <input type="submit" value="确定"/>
  </form>
</body>
</html>