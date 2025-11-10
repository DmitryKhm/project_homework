<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Report</title>
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <#list transactions as transaction>
            <h1>"Your transaction value is:${transaction.getValue()}" </h1></br>
            <h1>"This transaction from account with id:${transaction.getAccountFrom().getId()}" </h1></br>
            <h1>"This transaction to account with id:${transaction.getAccountTo().getId()}" </h1></br>
            <h1>"Your transaction time is:${transaction.getDate()}" </h1></br>
        </#list>
    </div>
</div>
<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>