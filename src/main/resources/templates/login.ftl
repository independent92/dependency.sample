
<html>
<head></head>
<body>
<h1>Login</h1>
<form action="/login" method="POST" >
    <table>
        <tr>
            <td>User:</td>
            <td><input type='text' name='email'></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type='password' name='password' /></td>
        </tr>
        <tr>
            <td><input type="submit" value="submit" /></td>
        </tr>
        <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </table>
</form>
</body>
</html>