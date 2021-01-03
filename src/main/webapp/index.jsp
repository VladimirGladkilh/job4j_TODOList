<%--
  Created by IntelliJ IDEA.
  User: Gladkih
  Date: 03.01.2021
  Time: 23:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        .accordion {
            background-color: #eee;
            color: #444;
            cursor: pointer;
            padding: 18px;
            width: 100%;
            border: none;
            text-align: left;
            outline: none;
            font-size: 15px;
            transition: 0.4s;
        }

        .active, .accordion:hover {
            background-color: #ccc;
        }

        .panel {
            padding: 0 18px;
            display: none;
            background-color: white;
            overflow: hidden;
        }
    </style>
    <title>ToDoLIst</title>
    <link rel="icon" type="image/png" href="favicon.ico"/>
</head>
<body>

<button class="accordion">Добавить задачу</button>
<div class="panel">
    <form action="<%=request.getContextPath()%>/index.do" method="post">
        <div class="form-group">
            <label>Задача</label>
            <input type="text" class="form-control" name="description" value="">
            <label>Выполнена</label>
            <input type="checkbox" class="form-control" name="done" value="">
        </div>
        <button type="submit" class="btn btn-primary">Добавить</button>
    </form>
</div>

<button class="accordion">Список задач</button>
<div class="panel">
    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
</div>
<script>
    var acc = document.getElementsByClassName("accordion");
    var i;

    for (i = 0; i < acc.length; i++) {
        acc[i].addEventListener("click", function() {
            this.classList.toggle("active");
            var panel = this.nextElementSibling;
            if (panel.style.display === "block") {
                panel.style.display = "none";
            } else {
                panel.style.display = "block";
            }
        });
    }
</script>
</body>
</html>
