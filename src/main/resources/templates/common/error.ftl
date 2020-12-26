<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta content="Syso Site" name="description" />
    <!-- App favicon -->
    <link rel="shortcut icon" href="/static/images/favicon.ico">

    <!-- App css -->
    <link href="/static/css/app.min.css" rel="stylesheet" type="text/css" id="light-style" />
    
    <title>${title} - Powered by Syso</title>
</head>
<body>
<main class="d-flex w-100 min-vh-100">
  <div class="container d-flex flex-column">
    <div class="row h-100">
      <div class="col-md-10 col-lg-10 mx-auto d-table h-100">
        <div class="d-table-cell align-middle">
            <div class="text-center">
              <h3 class="display-4 font-weight-bold">${display}</h3>
              <p class="fs-18">${desc}</p>
              <#if message??>
                <p class="fs-16">${message}</p>
              </#if>
              <a href="javascript:history.back()" class="btn btn-primary pl-4 pr-4 mt-2">返回上一页</a>
            </div>
        </div>
      </div>
    </div>
  </div>
</main>
</body>
</html>
