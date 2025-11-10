<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Account</title>
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">

        <#list accounts as account>
            <h1>"Your account id is:${account.id}" </h1>
            <h1>"Your accounts values is ${account.accValue}"</h1>
        </#list>
    </div>
</div>
<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>