<#import "/spring.ftl" as spring/>

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
            <form action="/insert-category" method="post">
                <div class="form-group">
                    <label for="exampleInputEmail">Category Name</label>
                    <@spring.formInput "form.categoryName"  "class=\"form-control\" id=\"exampleInputName\"
                           placeholder=\"Enter categoryName\"" "categoryName"/>
                    <@spring.showErrors"<br>"/>
                </div>
                <div class="form-group">
                    <label for="exampleInputEmail">Category Group</label>
                    <@spring.formInput "form.categoryGroup"  "class=\"form-control\" id=\"exampleInputGroup\"
                           placeholder=\"Enter categoryGroup\"" "categoryGroup"/>
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