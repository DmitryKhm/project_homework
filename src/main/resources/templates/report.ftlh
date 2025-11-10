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
        <form action="/report" method="post">
            <form>

                <div class="form-group">
                    <label for="exampleInputId">Date</label>
                    <@spring.formInput "form.date"  "class=\"form-control\" id=\"exampleInputDate\"
                           placeholder=\"Date\"" "date"/>
                </div>
                <div class="form-group">
                    <label for="exampleInputId">Category Id</label>
                    <@spring.formInput "form.categoryId"  "class=\"form-control\" id=\"exampleInputId\"
                           placeholder=\"Category Id\"" "categoryId"/>
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
