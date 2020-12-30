<#include "_macros.ftl">
<@header title="网站设置" />
<div class="container-fluid">
    <!-- Page title -->
    <div class="row">
        <div class="col-12">
            <div class="page-title-box">
                <div class="page-title-right">
                    <ol class="breadcrumb m-0">
                        <li class="breadcrumb-item"><a href="/admin">首页</a></li>
                        <li class="breadcrumb-item active">系统</li>
                        <li class="breadcrumb-item active">网站设置</li>
                    </ol>
                </div>
                <h4 class="page-title">网站设置</h4>
            </div>
        </div>
    </div> <!-- / .row -->

    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-body">
                    <form action="/admin/options/modify" method="post">
                        <div class="form-group">
                            <label>站点名称</label>
                            <input type="text" class="form-control form-control-sm" name="siteTitle" value="${options.siteTitle!}" required>
                            <small class="form-text text-muted">显示为网页的标题</small>
                        </div>
                        <div class="form-group">
                            <label>站点地址</label>
                            <input type="text" class="form-control form-control-sm" name="siteUrl" value="${options.siteUrl!}" required>
                            <small class="form-text text-muted">站点地址用于生成 rss sitemap</small>
                        </div>
                        <div class="form-group">
                            <label>站点 Logo</label>
                            <input type="text" class="form-control form-control-sm" name="siteLogo" value="${options.siteLogo!}">
                        </div>
                        <div class="form-group">
                            <label>站点 favicon</label>
                            <input type="text" class="form-control form-control-sm" name="siteFavicon" value="${options.siteFavicon!}">
                        </div>
                        <div class="form-group">
                            <label>站点描述</label>
                            <input type="text" class="form-control form-control-sm" name="siteDesc" value="${options.siteDesc!}">
                            <small class="form-text text-muted">站点描述标记</small>
                        </div>
                        <div class="form-group">
                            <label>关键词</label>
                            <input type="text" class="form-control form-control-sm" name="siteKeywords" value="${options.siteKeywords!}">
                            <small class="form-text text-muted">关键词标记，以半角逗号 "," 分割多个关键字.</small>
                        </div>
                        <div class="form-group">
                            <label>统计代码</label>
                            <textarea class="form-control form-control-sm" style="min-height: 120px;" name="siteAnalytics">${options.siteAnalytics!}</textarea>
                            <small class="form-text text-muted">第三方网站统计代码，如：百度统计、Google Analytics</small>
                        </div>
                        <button type="submit" class="btn btn-primary">保存</button>
                    </form>
                </div> <!-- end card-body-->
            </div> <!-- end card-->
        </div> <!-- end col-->
    </div><!-- / .row -->

</div><!-- / .container-fluid -->

<@footer />