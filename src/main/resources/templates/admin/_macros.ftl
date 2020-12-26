<#-- Header -->
<#macro header title>
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
    
    <title>${title} - Powered by Syso</title>
</head>
<body data-layout="topnav">
  <div class="content-page">
    <div class="content">
    <#include "_header.ftl">
</#macro>


<#-- Footer -->
<#macro footer standard=true>
<#if standard>
</div>  <!-- / .content -->

<footer class="footer text-center">©2020 Syso - chuonye.com</footer>

</div> <!-- / .content-page -->
</#if>

<!-- Libs JS -->
<script src="/static/libs/jquery/dist/jquery.min.js"></script>
<script src="/static/libs/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

<!-- App JS -->
<script type="text/javascript">
    $(document).ready(function() {
        "use strict";

        var pathName = location.pathname;
        $("#topnav-menu-content .nav-link").each(function () {
            var href = $(this).attr("href");
            if (pathName === href) {
                $(this).parent().addClass("active");
            } else {
                $(this).parent().removeClass("active");
            }
        });
        
        $('.notice').delay(20).slideDown(function () {
            var t = $(this);
            t.addClass('show');
            t.delay(4000).fadeOut(function () {
                $(this).remove();
            });
        });
     
    });
</script>
<script type="text/javascript">
    $(document).ready(function() {
        "use strict";

        $('.wk-table-row').each(function(){
            var $row = $(this);
            $('.select-checkbox', this).click(function() {
               var checked = $(this).prop('checked');

                if (checked) {
                    $row.addClass('selected');
                } else {
                    $row.removeClass('selected');
                    $('.select-all').prop('checked', checked);
                }
            });
        })

        $('.select-all').click(function() {
            var checked = $(this).prop('checked');

            if (checked) {
                $('.wk-table-row').each(function(){
                    var $row = $(this);
                    $row.addClass('selected');
                    $('.select-checkbox', $row).prop('checked', checked);
                })
            } else {
                $('.wk-table-row').each(function(){
                    var $row = $(this);
                    $row.removeClass('selected');
                    $('.select-checkbox', $row).prop('checked', checked);
                })
            }
        });
        
        $('.btn-operate a').click(function() {
            var $this = $(this), 
                lang = $this.attr('lang');

            if (!lang || confirm(lang)) {
                $('#table-form').attr('action', $this.attr('href')).submit();
            }

            return false;
        });
    });
</script>

<#-- 页面专有js -->
<#nested >

</body>
</html>
</#macro>

<#-- 进度条背景色 -->
<#macro alertbg x=0 y=1>
<#compress>
    <#assign p = (x?number)/(y?number)*100>
    <#if p gte 90>bg-danger
    <#elseif p gte 80>bg-warning
    <#else>bg-success
    </#if>
</#compress>
</#macro>

<#-- Footer -->
<#macro pagination url page>
<#if page.totalPages gt 0>
<ul class="pagination justify-content-end">
    <#if page.hasPrevious()>
    <li class="page-item">
        <a class="page-link" href="${url}?page=${page.number}">
            <span aria-hidden="true">&laquo;</span>
        </a>
    </li>
    </#if>
    
    <#if page.number gt 1>
    <li class="page-item"><a class="page-link" href="${url}?page=1">1</a></li>
    <li class="page-item"><a class="page-link">···</a></li>
    </#if>
    
    <#if page.hasPrevious()>
    <li class="page-item"><a class="page-link" href="${url}?page=${page.number}">${page.number}</a></li>
    </#if>
    
    <li class="page-item active"><a class="page-link" href="${url}?page=${page.number+1}">${page.number+1}</a></li>
    
    <#if page.hasNext()>
    <li class="page-item"><a class="page-link" href="${url}?page=${page.number+2}">${page.number+2}</a></li>
    </#if>
    
    <#if page.totalPages-1-page.number gt 1>
    <li class="page-item"><a class="page-link">···</a></li>
    <li class="page-item"><a class="page-link" href="${url}?page=${page.totalPages}">${page.totalPages}</a></li>
    </#if>
    
    <#if page.hasNext()>
    <li class="page-item">
        <a class="page-link" href="${url}?page=${page.number+2}">
            <span aria-hidden="true">&raquo;</span>
        </a>
    </li>
    </#if>
</ul>
</#if>
</#macro>


