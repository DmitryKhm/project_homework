<#import "/spring.ftl" as spring/>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>insert_account</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm"></div>
        <form action="/insert-account" method="post">
            <form>
                <div class="form-group">
                    <label for="exampleInputName">Account Name</label>
                    <@spring.formInput "form.accountName"  "class=\"form-control\" id=\"exampleInputName\"
                           placeholder=\"Account Name\"" "accountName"/>
                </div>
                <div class="form-group">
                    <label for="exampleInputValue">Account Value</label>
                    <@spring.formInput  "form.accountValue" "class=\"form-control\" id=\"exampleInputValue\"
                           placeholder=\" Account Value\"" "accountValue"/>
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
