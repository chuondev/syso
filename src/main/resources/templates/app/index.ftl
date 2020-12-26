<@compress single_line = true>
<#include "_layouts.ftl">
<@header title="${options.siteTitle}"/>
<!-- Main Content-->
<div class="container-fluid syso-main">
<div class="row">
<div class="col-xl-9 col-lg-9">
    <div class="card pb-4">
        <ul class="list-post">
            <#include "_list-posts.ftl">
        </ul>
    </div> <!-- end card-->
</div> <!-- end col-->

<div class="col-xl-3 col-lg-3"> 
    <div class="card">
        <div class="card-header fs-16 font-weight-bold">小创编程</div>
        <div class="card-body">
            <div class="syso-notice">
                <div class="mb-1">研究分析常用 Java 中间件的源码</div>
                <div class="mb-1">仿写中间件，比如 Tomcat，Netty，h2database ...</div>
                <div>翻译一些英文技术文章和手册</div>
            </div>
            <div class="syso-divider"><span>关注本站</span></div>
            <div class="follow-me">

                <a title="https://www.github.com/chuondev" target="_blank" href="https://www.github.com/chuondev"><i class="mdi mdi-github-circle"></i></a>
                <a title="chuonye@foxmail.com" href="mailto:chuonye@foxmail.com"><i class="mdi mdi-email-outline"></i></a>
                <a title="RSS 订阅" href="/rss"><i class="mdi mdi-rss"></i></a>
            </div>
        </div>
    </div><!-- / .card -->
    
    <div class="card">
        <div class="card-body weixin">
            <img src="/static/app/weixin.jpg">
            <div>
                <div>关注「小创编程」公众号</div>
                <div class="text-muted fs-13 mt-1">随时获取最新内容</div>
            </div>
        </div>
    </div>

</div> <!-- end col-->
</div><!-- / .row -->

</div><!-- / .container-fluid -->

<@footer />
</@compress>