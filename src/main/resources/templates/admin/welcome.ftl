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

    <title>Syso 初始化</title>
</head>
<body>
    <div class="container">
        <div class="row justify-content-center syso-init">
          <div class="col-lg-8 col-md-12">
            <div class="syso-init-body">
                <h1>Syso 初始化成功!</h1>
                <div class="init-user">
                  <span>默认登录名是：</span><strong class="mono">${user}</strong><br />
                  <span>随机密码是：</span><strong class="mono">${passwd}</strong>
                  <div class="note fs-18"><strong>请及时修改登录名和密码！</strong></div>
                </div>

                <div class="session">
                  <p>您可以将下面两个链接保存到收藏夹：</p>
                  <ul>
                    <li><a href="/admin/login?u=${user}&p=${passwd}">点击访问后台控制面板</a></li>
                    <li><a href="/">点击查看前台 Blog 页面</a></li>
                  </ul>
                </div>

                <h1 class="mt-5">许可及协议</h1>
                <p>Syso 使用 <a href="https://www.gnu.org/licenses/gpl-3.0.html">GPL-v3.0</a> 开源协议，在协议许可范围内，我们允许用户复制、修改和分发此程序，或将其用于商业以及非商业用途。</p>
                <p>如果您遇到使用上的问题, 程序 BUG, 以及优化上的建议, 欢迎在 <a href="https://github.com/chuondev/syso" target="_blank">Github/chuondev</a> 上交流或者直接贡献代码。</p>
            </div>
          </div>
        </div> <!-- / .row -->
    </div> <!-- / .container -->

    <footer class="footer footer-alt">
        ©2020 Syso - chuonye.com
    </footer>
</body>
</html>
