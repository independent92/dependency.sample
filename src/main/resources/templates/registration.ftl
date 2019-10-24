<#import "/spring.ftl" as spring />

<html>
<head></head>
<body>
<h1>Registration</h1>
<form action="/registration" method="POST" >
    <table>
        <tr>
            <@spring.bind "accountInputDto.email"/>
            <td>User:</td>
            <td><input type='text' name='email'></td>
            <td><@spring.showErrors " ", ""/></td>
        </tr>
        <tr>
            <@spring.bind "accountInputDto.password"/>
            <td>Password:</td>
            <td><input type='password' name='password' /></td>
            <td><@spring.showErrors " ", ""/></td>
        </tr>
        <tr>
            <@spring.bind "accountInputDto.passwordConfirmation"/>
            <td>Password confirmation:</td>
            <td><input type='password' name='passwordConfirmation' /></td>
            <td><@spring.showErrors " ", ""/></td>
        </tr>
        <tr>
            <@spring.bind "accountInputDto.firstName"/>
            <td>First name:</td>
            <td><input type='text' name='firstName' /></td>
            <td><@spring.showErrors " ", ""/></td>
        </tr>
        <tr>
            <td>Middle name:</td>
            <td><input type='text' name='middleName' /></td>
        </tr>
        <tr>
            <@spring.bind "accountInputDto.lastName"/>
            <td>Last name:</td>
            <td><input type='text' name='lastName' /></td>
            <td><@spring.showErrors " ", ""/></td>
        </tr>
        <tr>
            <td><input type="submit" value="submit" /></td>
        </tr>
        <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </table>
</form>
</body>
</html>