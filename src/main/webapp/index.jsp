
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <style>
        .itemTable {
            counter-reset: schetchik;
        }

        .itemTable table {
            border-collapse: collapse;
        }

        .itemTable tr {
            counter-increment: schetchik;
        }

        .itemTable td,
        .itemTable tr:before {
            padding: .1em .5em;
            border: 1px solid #E7D5C0;
        }

        .itemTable tr:before {
            content: counter(schetchik);
            display: table-cell;
            vertical-align: middle;
            color: #978777;
        }
    </style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <title>ToDoLIst</title>
    <link rel="icon" type="image/png" href="favicon.ico"/>
</head>
<body>

<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
    function refreshData(reqData) {
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/todolist/index.do",
            data: reqData,
            dataType: 'text',
            origin: "http://localhost:8081"
        })
            .done(function (data) {
                location.reload();
                document.getElementsByClassName("panel")[1].style.display="block";
            })
            .fail(function (err) {
                alert("err" + err.message);
            })
    };

    function addItem() {
        var reqData = "id=0&description=" + document.getElementById("desc").value + "&done=";
        if (document.getElementById("done").checked) {
            reqData = reqData + "true";
        } else {
            reqData = reqData + "false";
        }
        refreshData(reqData);
    }

    function deleteid(id) {
        var reqData = "deleteid=" + id;
        refreshData(reqData);
    }

    function doneid(id) {
        var reqData = "doneid=" + id;
        refreshData(reqData);
    }

    function filter() {
        let allInputs, visible, tr;
        allInputs = $("#itemTable input").filter(":checked").parent().parent();
        visible = !document.getElementById("filterCheck").checked;
        for (var i = 0, max = allInputs.length; i < max; i++) {
            tr = allInputs[i];
            if (visible) {
                tr.style.display = "";
            } else {
                tr.style.display = "none";
            }
        }
    }
</script>
<div class="container">
    <div class="row">
        <% if (request.getAttribute("user") != null) {%>
        <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <c:out value="${user.name}"/> |
            Выйти</a>
        <%} else {%>
        <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">Войти</a>
        <%}%>
    </div>
</div>
<button class="accordion">Добавить задачу</button>
<div class="panel">
    <form action="<%=request.getContextPath()%>/index.do" method="post">
        <div class="form-group">
            <label>Задача</label>
            <input type="text" id="desc" class="form-control" name="description" value="">
            <label>Выполнена</label>
            <input type="checkbox" id="done" class="form-control" name="done" value="">
        </div>
        <button type="button" class="btn btn-primary" onclick="addItem()">Добавить</button>
    </form>
</div>

<button class="accordion">Список задач</button>
<div class="panel" display="block">
    <label><input type="checkbox" id="filterCheck" onchange="filter()"/>Только невыполненые</label>
    <div class="container">
    <table class="table table-bordered" id="itemTable">
        <tr>
            <th>№</th>
            <th>Содержание</th>
            <th>Выполнено</th>
            <th>Удалить</th>
        </tr>
        <c:forEach var="element" items="${requestScope.items}" varStatus="counter">

            <tr id="${element.id}">
                <td>${counter.count}</td>
                <td><c:out value="${element.description}"/></td>
                <td><input type="checkbox" id="${element.id}" ${element.done ? "checked" : ""}
                           onchange="doneid(${element.id})"/></td>
                <td><a href="#" onclick="deleteid(${element.id})"; return false>X</a></td>
            </tr>
        </c:forEach>
    </table>
    </div>
</div>

<script>
    var acc = document.getElementsByClassName("accordion");
    var i;

    for (i = 0; i < acc.length; i++) {
        acc[i].addEventListener("click", function () {
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
