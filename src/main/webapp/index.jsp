<!DOCTYPE html>
<html>
<head>
    <%@ page contentType="text/html; charset=UTF-8" %>
    <script
            src="https://code.jquery.com/jquery-3.5.1.js"
            integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
            crossorigin="anonymous"></script>
    <meta charset="utf-8">
    <title>Tasks</title>
</head>


<body>
<form>
    <p>
        <label for="name">Task name:</label><br/>
        <input type="text" name="task" value="enter name" id="name"/><br>
        <label for="comment">New task:</label><br/>
        <textarea id="comment" name="comment" placeholder="Description"
                  cols="30" rows="7"></textarea>
    </p>
    <p>
        <input type="submit" class="btn btn-primary" value="Add new task" onclick="sendData()"/>
    </p>
</form>
<form id="tasks">
</form>
</body>
</html>

<script>
    window.onload = function () {
        $.ajax({
            type: 'GET',
            url: '<%=request.getContextPath()%>/index',
            data: {request: "GET request"},
            dataType: 'json',
            success: function (data) {
                collectTasks(data)
            }
        });
    }

    function collectTasks(data) {
        let p = document.createElement('p')
        let h2 = document.createElement('h2');
        h2.innerText = 'Tasks:'
        let form = document.getElementById('tasks')
        form.appendChild(h2)
        for (key in data) {
            const name = data[key]
            let checkbox = document.createElement('div')
            checkbox.innerHTML = name + '<input class="checkbox" type="checkbox" name=' + name + '/>'
            p.appendChild(checkbox)
        }
        let button = document.createElement('div')
        button.innerHTML = '<button type="submit" onclick="sendUpdate()">Update</button>'
        p.appendChild(button)
        form.appendChild(p)
    }

    function validate() {
        const name = document.getElementById('name').value
        const description = document.getElementById('comment').value
        if (name == '' || description == '') {
            alert('Please fill the form fields')
            return false
        }
        return true
    }

    function sendData() {
        if (validate()) {
            const name = document.getElementById('name').value
            const description = document.getElementById('comment').value
            $.ajax({
                type: 'POST',
                url: '<%=request.getContextPath()%>/index',
                data: {
                    name: name,
                    description: description
                },
                dataType: 'json',
                success: console.log('done')
            });
        }
    }

    function sendUpdate() {
        var name = new Array();
        var checkboxes = document.getElementsByClassName('checkbox');
        for (key in checkboxes) {
            if (checkboxes[key].checked) {
                name[key] = checkboxes[key].name
            }
        }
        $.ajax({
            type: 'POST',
            url: '<%=request.getContextPath()%>/index',
            data: {
                name: name,
                selected: true
            },
            dataType: 'json',
            success: console.log('done')
        });
    }

</script>