<#import "/spring.ftl" as spring/>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>report</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm"></div>
        <form action="/transactions" method="post">
            <form>
                <div class="form-group">
                    <label for="exampleInputId">FromAccount Id</label>
                    <@spring.formInput "form.accountId"  "class=\"form-control\" id=\"exampleInputId\"
                           placeholder=\"Account Id\"" "accountId"/>
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
