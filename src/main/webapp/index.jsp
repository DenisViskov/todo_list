<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Tasks</title>
</head>
<body>
<form method="get">
    <p>
        <label for="comment">Task name:</label><br/>
        <input type="text" name="task" value="enter name"/><br>
        <label for="comment">New task:</label><br/>
        <textarea id="comment" name="comment" placeholder="Description"
                  cols="30" rows="7"></textarea>
    </p>
    <p>
        <input type="submit" value="Add new task"/>
    </p>
</form>
</body>
</html>