<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>login</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm"></div>
        <div class="col-sm">
            <form action="/login" method="post">
                <div class="form-group">
                    <label for="exampleInputEmail">Email address</label>
                    <input name="email" class="form-control" id ="exampleInputEmail" placeholder="Enter email"type="text">
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword">Password</label>
                   <input name="password" class="form-control" id="exampleInputPassword" placeholder="Password" type="password">
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
        <div class="col-sm"></div>
    </div>
</div>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>