<#import "/spring.ftl" as spring/>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>registration</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm"></div>
        <form action="/registration" method="post">
            <form>
                <div class="form-group">
                    <label for="exampleInputName">User Name</label>

                    <@spring.formInput "form.name"  "class=\"form-control\" id=\"exampleInputName\"
                           placeholder=\"User Name\"" "name"/>
                </div>
                <div class="form-group">
                    <label for="exampleInputEmail1">Email address</label>
                    <@spring.formInput "form.email"  "class=\"form-control\" id=\"exampleInputEmail\"
                           placeholder=\"Enter email\"" "email"/>
                    <@spring.showErrors"<br>"/>
                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone
                        else.</small>
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword1">Password</label>
                    <@spring.formInput  "form.password" "class=\"form-control\" id=\"exampleInputPassword\"
                           placeholder=\" Password\"" "password"/>
                    <@spring.showErrors"<br>"/>
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
