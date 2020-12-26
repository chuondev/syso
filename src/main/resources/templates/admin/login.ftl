<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta content="Syso 后台管理" name="description" />
    <!-- App favicon -->
    <link rel="shortcut icon" href="/static/images/favicon.ico">

    <!-- Libs css -->

    <!-- App css -->
    <link href="/static/fonts/mdi/css/materialdesignicons.css" rel="stylesheet" type="text/css" />
    <link href="/static/css/app.min.css" rel="stylesheet" type="text/css" id="light-style" />
    
    <title>登录 - Powered by Syso</title>
</head>
<body>
    <#include "_notice.ftl">
    <div class="container pt-5 mb-5">
        <div class="row justify-content-center">
          <div class="col-12 col-md-5 col-xl-4 my-5">
            
            <!-- Heading -->
            <div class="text-center mb-4">
                <a href="/"><img src="/static/images/logo.svg" alt="" height="30"></a>
            </div>
            
            <form action="/admin/doLogin" method="post">
              <div class="form-group">
                <input type="text" name="loginName" class="form-control" placeholder="登录名/邮箱地址" autofocus="autofocus" required value="${u!}">
              </div>

              <div class="form-group">
                <input type="password" name="password" class="form-control" placeholder="输入密码" required value="${p!}">
              </div>
              
              <div class="form-row">
                <div class="form-group col-7">
                  <input type="text" name="captcha" class="form-control" placeholder="输入验证码" required>
                </div>
                <div class="form-group col-5 d-flex align-items-center justify-content-end">
                  <img style="cursor: pointer;" alt="验证码" title="点击刷新" src="/captcha" id="captcha-img" onclick="$('input[name=captcha]').val('');$('#captcha-img').attr('src', '/captcha?t=' + new Date().getTime());">
                </div>
              </div>
              <!-- Submit -->
              <button class="btn btn-lg btn-block btn-primary mb-3">登 录</button>

              <!-- Link -->
              <div class="text-center">
                <small class="text-muted text-center"><a class="disabled" href="#">忘记密码？</a></small>
              </div>
              
            </form>
          </div>
        </div> <!-- / .row -->
    </div> <!-- / .container -->

    <footer class="footer footer-alt">
        ©2020 Syso - chuonye.com
    </footer>

    <!-- Libs JS -->
    <script src="/static/libs/jquery/dist/jquery.min.js"></script>
    <script src="/static/libs/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
    
    <script type="text/javascript">
        $(document).ready(function() {
            "use strict";
            $('.notice').delay(20).slideDown(function () {
                var t = $(this);
                t.addClass('show');
                t.delay(4000).fadeOut(function () {
                    $(this).remove();
                });
            });
            
            function getParam(name, url = window.location.href){
                name = name.replace(/[\[\]]/g, '\\$&');
                var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
                    results = regex.exec(url);
                if (!results) return null;
                if (!results[2]) return null;
                return decodeURIComponent(results[2].replace(/\+/g,' '));
            }
            var u = getParam('u'),
                p = getParam('p');
            if (u!=null && p!=null) {
              $('input[name="loginName"]').val(u);
              $('input[name="password"]').val(p);
            }
        });
    </script>
</body>
</html>
