<%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 27.09.2020
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <script
            src="https://code.jquery.com/jquery-3.5.1.js"
            integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
            crossorigin="anonymous"></script>
</head>
<body id="body">
<h2>Welcome to TODO App</h2>
<form>
    <h3>Please fill the form fields:</h3>
    <div>Login:<br>
        <input type="text" id="login" required name="login"/>
    </div>
    <div>Password:<br>
        <input type="password" id="password" required name="password"/>
    </div>
    <div>Confirm password:<br>
        <input type="password" id="confirm_password" required name="confirm_password"/>
    </div>
    <div>
        <input type="submit" onclick="sendData()" value="Sign up"/>
    </div>
</form>
</body>
</html>

<script>
    function validation() {
        const password = document.getElementById("password").value
        const confirm = document.getElementById("confirm_password").value
        if (password != confirm) {
            alert("passwords not same")
            return false
        }
        return true
    }

    function sendData() {
        if (validation()) {
            const login = document.getElementById('login').value
            const password = document.getElementById("password").value
            const confirm = document.getElementById("confirm_password").value
            $.ajax({
                type: 'POST',
                url: '<%=request.getContextPath()%>/registration',
                data: {
                    login: login,
                    password: password,
                    confirm: confirm
                },
                dataType: 'json',
            }).done(function (data) {
                getAnswer(data)
            }).fail(function (err) {
                alert(err);
            });
        }
    }

    function getAnswer(data) {
        const answer = data[0]
        if (answer == 'was added') {
            let body = document.getElementById('body')
            let message = document.createElement('div')
            message.innerText = 'Fine! You was been registered'
            body.appendChild(message)
        }
        if(answer == 'exist'){
            let body = document.getElementById('body')
            let message = document.createElement('div')
            message.innerText = 'User with the same login already exist!'
            body.appendChild(message)
        }
    }
</script>