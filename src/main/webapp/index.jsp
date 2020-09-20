<!DOCTYPE html>
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
    <meta charset="utf-8">
    <title>Tasks</title>
</head>

<script>
    function validate() {
        const name = document.getElementById('name').value
        const description = document.getElementById('comment').value
        if (name == '' || description == '') {
            alert('Please fill the form fields')
            return false
        }
        return true
    }

    function sendData(){
        if(validate()){

        }
    }

</script>

<body>
<form method="get">
    <p>
        <label for="comment">Task name:</label><br/>
        <input type="text" name="task" value="enter name" id="name"/><br>
        <label for="comment">New task:</label><br/>
        <textarea id="comment" name="comment" placeholder="Description"
                  cols="30" rows="7"></textarea>
    </p>
    <p>
        <input type="submit" value="Add new task" onclick="validate()"/>
    </p>
</form>
</body>
</html>