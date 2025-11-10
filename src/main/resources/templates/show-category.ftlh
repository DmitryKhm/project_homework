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

        <#list categories as category>
            <h1>"Your category id is:${category.getId()}" </h1>
            <h1>"Your category name is ${category.categoryName}"</h1>
            <h1>"Your category group is ${category.categoryGroup}"</h1>
        </#list>
    </div>
</div>
<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>