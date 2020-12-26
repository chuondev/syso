<#-- Header -->
<#macro header title="小创编程">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta content="${options.siteDesc}" name="description" />
    <#if options.siteKeywords?has_content>
    <meta content="${options.siteKeywords}" name="keywords" />
    </#if>
    <!-- App favicon -->
    <link rel="shortcut icon" href="${options.siteFavicon}">

    <!-- App css -->
    <link href="/static/fonts/mdi/css/materialdesignicons.css" rel="stylesheet" type="text/css" />
    <link href="/static/css/app.min.css" rel="stylesheet" type="text/css" id="light-style" />

    <title>${title} | Powered by Syso</title>
    
    <#if options.siteAnalytics?has_content>${options.siteAnalytics}</#if>
</head>

<body data-layout="topnav">
<div class="content-page">
    <div class="content">
        <!-- Topbar-->
        <div class="wk-navbar topnav-navbar">
            <div class="container-fluid">

                <!-- Brand -->
                <a href="/" class="topnav-logo mr-3">
                    <span class="topnav-logo-lg">
                        <img src="${options.siteLogo}" alt="" height="24">
                    </span>
                </a>

                <!-- Toggler -->
                <a class="navbar-toggle" data-toggle="collapse" data-target="#topnav-menu-content">
                    <div class="lines">
                        <span></span>
                        <span></span>
                        <span></span>
                    </div>
                </a>

                <nav class="topnav navbar navbar-light navbar-expand-lg">
                    <div class="collapse navbar-collapse" id="topnav-menu-content">
                        <ul class="navbar-nav">
                            <li class="nav-item active">
                                <a class="nav-link" href="/">首页</a>
                            </li>
                            <li class="nav-item" title="努力研发中，敬请关注...">
                                <a class="nav-link disabled" href="/">项目</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" target="_blank" href="https://github.com/chuondev/download">下载</a>
                            </li>

                            <#--
                            <li class="nav-item">
                                <a class="nav-link" href="/">手册</a>
                            </li>
                            -->
                        </ul>
                    </div>
                </nav>

            </div>
        </div>
</#macro>


<#-- Footer -->
<#macro footer>
</div>  <!-- / .content -->

    <footer class="footer text-center">©2020 chuonye.com</footer>

</div> <!-- / .content-page -->

<!-- Libs JS -->
<script src="/static/libs/jquery/dist/jquery.min.js"></script>

<!-- App JS -->
<script type="text/javascript">
    $(document).ready(function() {
        "use strict";
        
        var $backToTop = $('<div>', {'class': 'back-to-top'}),
            $icon = $('<i>', {'class': 'mdi mdi-arrow-collapse-up'});

        $backToTop.appendTo('body');
        $icon.appendTo($backToTop);
        
        $(window).scroll(function() {
          if ($(this).scrollTop() > 150) {
            $backToTop.addClass('show');
          } else {
            $backToTop.removeClass('show');
          }
        });
        
        $backToTop.click(function (e) {
          e.preventDefault();
        
          $('body, html').animate({
            scrollTop: 0
          }, 300);
        });
    });
</script>
<#-- 页面专有js -->
<#nested >

</body>
</html>
</#macro>