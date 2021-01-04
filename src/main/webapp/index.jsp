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
<body onload='refreshData("")'>

<!--script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script-->
<script>
    function refreshData(reqData) {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/todolist/index.do",
            data: reqData,
            dataType: 'text',
            origin: "http://localhost:8081"
        })
            .done(function (data) {
                document.getElementById("cardbody").innerHTML= data;
                document.getElementById("desc").innerText = "";
                document.getElementById("done").setAttribute("checked", false);
            })
            .fail(function (err) {
                alert("err" + err.message);
            })
    };
    function addItem() {
        var reqData = "id=0&description=" + document.getElementById("desc").innerText + "&done=";
        if ( document.getElementById("done").checked) {
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
        var reqData = "doneid=" + id + "&done=";
        if ( document.getElementById("id").checked) {
            reqData = reqData + "true";
        } else {
            reqData = reqData + "false";
        }
        refreshData(reqData);
    }
    function filter() {
        var allInputs = $("input[type=checkbox]").filter(':checked:enabled');
        var visible = document.getElementById("filterCheck").checked;
        var selected = '';
        for (var i = 0, max = allInputs.length; i < max; i++){
            selected = allInputs[i].id;
            tr = table.getElementById(selected);
            if (tr) {
                if (visible) {
                    tr.style.display = "";
                } else {
                    tr.style.display = "none";
                }
            }
        }
    }
</script>
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
<div class="panel" >
    <p><label><input type="checkbox" id="filterCheck" onchange="filter()"/>Только невыполненые</label><p>
    <p id="cardbody"><p>
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
