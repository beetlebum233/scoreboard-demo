<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>穷人杯</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap-theme.min.css" />
    <script src="resources/js/jquery-3.2.1.min.js"></script>
    <script src="resources/js/bootstrap.min.js"></script>

</head>
<body>
    <div class="container-fluid">
        <h1>第一届《穷人杯》小组赛积分榜</h1>
        <div class="row">
            <div class="col-xs-3">
                <h3>A组</h3>
                <table class="table table-bordered" id="group-a">
                    <thead>
                        <tr>
                            <td>选手</td>
                            <td>积分</td>
                            <td>净胜球</td>
                            <td>进球数</td>
                        </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
            <div class="col-xs-3">
                <h3>B组</h3>
                <table class="table table-bordered" id="group-b">
                    <thead>
                    <tr>
                        <td>选手</td>
                        <td>积分</td>
                        <td>净胜球</td>
                        <td>进球数</td>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
            <div class="col-xs-3">
                <h3>C组</h3>
                <table class="table table-bordered" id="group-c">
                    <thead>
                    <tr>
                        <td>选手</td>
                        <td>积分</td>
                        <td>净胜球</td>
                        <td>进球数</td>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
            <div class="col-xs-3">
                <h3>D组</h3>
                <table class="table table-bordered" id="group-d">
                    <thead>
                    <tr>
                        <td>选手</td>
                        <td>积分</td>
                        <td>净胜球</td>
                        <td>进球数</td>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-6">
                <h4>比赛结果录入</h4>
                <form class="form-inline" id="match-form">
                    <select class="form-control players" name="homePlayerId">

                    </select>
                    <input class="form-control" name="homeGoal" size="1"/>
                    <label>-</label>
                    <input class="form-control" name="awayGoal" size="1"/>
                    <select class="form-control players" name="awayPlayerId">

                    </select>
                    <button class="btn btn-info" type="button" onclick="addMatch()">提交</button>
                </form>
            </div>
            <div class="col-xs-6">
                <h4>比赛结果</h4>
                <table class="table table-bordered" id="match-result">
                    <thead>
                    <tr>
                        <td>比分</td>
                        <td>时间</td>
                        <td>操作</td>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
<script>
    $.ajaxSetup({
        contentType: "application/x-www-form-urlencoded; charset=utf-8"
    });

    function refresh(){
        $.post("/scoreboard/player/findByGroupId",{groupId : 1}, function(data){
            if(data && data.length && data.length > 0){
                var tbody = $("#group-a").find("tbody");
                tbody.text("");
                for(var i = 0; i < data.length; i++){

                    tbody.append("<tr><td>"+ data[i].name +"</td><td>"+ data[i].score +"</td><td>"+ data[i].goalDifference +"</td><td>"+ data[i].goal +"</td></tr>");
                }
            }
        });

        $.post("/scoreboard/player/findByGroupId",{groupId : 2}, function(data){
            if(data && data.length && data.length > 0){
                var tbody = $("#group-b").find("tbody");
                tbody.text("");
                for(var i = 0; i < data.length; i++){

                    tbody.append("<tr><td>"+ data[i].name +"</td><td>"+ data[i].score +"</td><td>"+ data[i].goalDifference +"</td><td>"+ data[i].goal +"</td></tr>");
                }
            }
        });

        $.post("/scoreboard/player/findByGroupId",{groupId : 3}, function(data){
            if(data && data.length && data.length > 0){
                var tbody = $("#group-c").find("tbody");
                tbody.text("");
                for(var i = 0; i < data.length; i++){

                    tbody.append("<tr><td>"+ data[i].name +"</td><td>"+ data[i].score +"</td><td>"+ data[i].goalDifference +"</td><td>"+ data[i].goal +"</td></tr>");
                }
            }
        });

        $.post("/scoreboard/player/findByGroupId",{groupId : 4}, function(data){
            if(data && data.length && data.length > 0){
                var tbody = $("#group-d").find("tbody");
                tbody.text("");
                for(var i = 0; i < data.length; i++){

                    tbody.append("<tr><td>"+ data[i].name +"</td><td>"+ data[i].score +"</td><td>"+ data[i].goalDifference +"</td><td>"+ data[i].goal +"</td></tr>");
                }
            }
        });

        $.post("/scoreboard/player/findAll",{}, function(data){
            if(data && data.length && data.length > 0){
                for(var i = 0; i < data.length; i++){
                    $(".players").append("<option value='"+ data[i].id +"'>"+ data[i].name +"</option>");
                }

            }
        });

        $.post("/scoreboard/match/findAll",{}, function(data){
            if(data && data.length && data.length > 0){
                var tbody = $("#match-result").find("tbody");
                tbody.text("");
                for(var i = 0; i < data.length; i++){

                    tbody.append("<tr><td>"+ data[i].result +"</td><td>"+ data[i].createdTime +"</td><td><button class=\"btn btn-info\" type=\"button\" onclick=\"undo('"+ data[i].id +"')\">撤销</button></td></tr>");
                }
            }
        });
    }

    function addMatch(){
        if($("input[name=homeGoal]").val() == ""){
            alert("请输入比分");
            return;
        }
        if($("input[name=awayGoal]").val() == ""){
            alert("请输入比分");
            return;
        }
        $("button").prop("disabled",true);
        var param = {};
        $("#match-form :input").each(function(){
            param[$(this).attr("name")] = $(this).val();
        });
        $.post("/scoreboard/match/add",param, function(data){
            refresh();
            $("button").prop("disabled",false);
        });
    }

    function undo(id){
        $.post("/scoreboard/match/delete",{matchId : id}, function(data){
            refresh();
        });
    }

    refresh();
</script>
</html>
