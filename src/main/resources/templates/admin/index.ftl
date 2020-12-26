<#include "_macros.ftl">
<@header title="管理首页" />
<div class="container-fluid">
    <!-- Page title -->
    <div class="row">
        <div class="col-12">
            <div class="page-title-box">
                <div class="page-title-right">
                    <ol class="breadcrumb m-0">
                        <li class="breadcrumb-item"><a href="/admin">首页</a></li>
                        <li class="breadcrumb-item active">控制台</li>
                    </ol>
                </div>
                <h4 class="page-title">控制台</h4>
            </div>
        </div>
    </div> <!-- / .row -->

    <div class="row">
        <div class="col-lg-3 col-md-3 col-6">
            <div class="card">
                <div class="card-body text-center">
                    <h3><span>${totalPosts!0}</span></h3>
                    <p class="text-muted font-15 mb-0">文章总数</p>
                </div>
            </div>
        </div>

        <div class="col-lg-3 col-md-3 col-6">
            <div class="card">
                <div class="card-body text-center">
                    <h3><span>${totalViews!0}</span></h3>
                    <p class="text-muted font-15 mb-0">阅读量</p>
                </div>
            </div>
        </div>
        
        <div class="col-lg-3 col-md-3 col-6">
            <div class="card">
                <div class="card-body text-center">
                    <h3><span>${totalPageViews!0}</span></h3>
                    <p class="text-muted font-15 mb-0">访问量</p>
                </div>
            </div>
        </div>


        <div class="col-lg-3 col-md-3 col-6">
            <div class="card">
                <div class="card-body text-center">
                    <h3><span>NaN</span></h3>
                    <p class="text-muted font-15 mb-0">其他</p>
                </div>
            </div>
        </div>
    </div> <!-- / .row -->

    <div class="row">
        <div class="col-xl-4 col-lg-6">
            <div class="card">
                <div class="card-body">
                    <a href="javascript:void(0);" onclick="refreshServerMetrics();" class="p-0 float-right mb-3 fs-16"><i class="mdi mdi-autorenew"></i></a>
                    <h4 class="header-title mt-1">系统资源</h4>
                    <div class="position-relative">
                        <div class="metrics-backdrop">
                            <img src="/static/images/loading_white_lg.gif">
                        </div>
                        <ul class="list-group" id="server-metrics">
                            <#include "_osmetrics.ftl">
                        </ul>
                    </div>
                </div> <!-- end card-body-->
            </div> <!-- end card-->
        </div> <!-- end col-->
        
        <div class="col-xl-4 col-lg-6">
            <div class="card">
                <div class="card-body">
                    <h4 class="header-title">最新文章</h4>
                    <ul class="list-group list-group-flush">
                    <#list latestPosts as post>
                      <li class="list-group-item pl-0">
                          <a target="_blank" href="/archives/${post.slug}.html">${post.title}</a>
                          <span class="float-right"><@common.timeline time="${post.createTime}"?datetime /></span>
                      </li>
                    </#list>
                    </ul>
                </div> <!-- end card-body-->
            </div> <!-- end card-->
        </div> <!-- end col-->

        <div class="col-xl-4 col-lg-12">
            <div class="card">
                <div class="card-body">
                    <h4 class="header-title mb-2">版本历史</h4>

                    <div data-simplebar style="max-height: 424px;">
                        <div class="timeline-alt pb-0">
                            <div class="timeline-item">
                                <i class="timeline-icon border-primary"></i>
                                <div class="timeline-item-info">
                                    <a href="#" class="text-primary font-weight-bold mb-1 d-block">v1.0</a>
                                    <small>发布第一个版本</small>
                                    <p class="mb-0 pb-2"><small class="text-muted">2020-12-19 11:55</small></p>
                                </div>
                            </div>
                        </div>
                        <!-- end timeline -->
                    </div> <!-- end slimscroll -->
                </div>
                <!-- end card-body -->
            </div>
            <!-- end card-->
        </div><!-- end col -->
    </div><!-- / .row -->

</div><!-- / .container-fluid -->

<@footer>
<script type="text/javascript">
    function refreshServerMetrics() {
        $('.metrics-backdrop').addClass('show');
        $.ajax({
            type: 'GET',
            dataType: 'html',
            url: '/admin/server-metrics'
        })
        .done(function(data) {
            $('#server-metrics').empty().html(data);
        })
        .fail(function() {
            console.log("error - 网络错误");
        })
        .always(function() {
            $('.metrics-backdrop').removeClass('show');
        });
    }
</script>
</@footer>