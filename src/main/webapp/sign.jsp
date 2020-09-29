<%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 27.09.2020
  Time: 13:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SignIn</title>
    <script
            src="https://code.jquery.com/jquery-3.5.1.js"
            integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
            crossorigin="anonymous"></script>
</head>
<body id="body">
<h2>Sign in:</h2>
<form id="form">
    <div>Login:<br>
        <input type="text" id="login" required name="login"/>
    </div>
    <div>Password:<br>
        <input type="password" id="password" required name="password"/>
    </div>
    <div>
        <input type="submit" value="Sign in"/>
    </div>
</form>
<h2>If you not registered yet, please follow on registration page:</h2>
<a href="http://localhost:8080/todo_list/registration.jsp">Registration now</a>
</body>
</html>

<script>
    $("#form").submit(function (e) {
        e.preventDefault()
        sendData()
        setTimeout(getAnswer, 500)
    })

    function sendData() {
        const login = document.getElementById('login').value
        const password = document.getElementById("password").value
        $.ajax({
            type: 'POST',
            url: '<%=request.getContextPath()%>/sign',
            data: {
                login: login,
                password: password,
            },
            dataType: "json",
            success: console.log('done')
        })
    }

    function getAnswer() {
        $.ajax({
            type: 'GET',
            url: '<%=request.getContextPath()%>/sign',
            data: {
                sign: "get data"
            },
            dataType: "json",
            success: function (data) {
                showMessage(data)
            }
        })
    }

    function showMessage(data) {
        const message = data['answer']
        let body = document.getElementById('body')
        let divElement = document.createElement('div')
        if (message) {
            divElement.innerText = 'Fine! You are entered'
            body.appendChild(divElement)
            setTimeout(redirect, 5000)
        } else {
            divElement.innerText = 'Login or password not same'
            body.appendChild(divElement)
        }
    }

    function redirect() {
        document.location.href = '<%=request.getContextPath()%>/index.jsp'
    }
</script>