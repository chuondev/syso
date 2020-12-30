<#include "_macros.ftl">
<@header title="备份与恢复" />
<div class="container-fluid">
    <!-- Page title -->
    <div class="row">
        <div class="col-12">
            <div class="page-title-box">
                <div class="page-title-right">
                    <ol class="breadcrumb m-0">
                        <li class="breadcrumb-item"><a href="/admin">首页</a></li>
                        <li class="breadcrumb-item active">系统</li>
                        <li class="breadcrumb-item active">备份与恢复</li>
                    </ol>
                </div>
                <h4 class="page-title">备份与恢复</h4>
            </div>
        </div>
    </div> <!-- / .row -->
    
    <div class="row">
        <div class="col-lg-7">
            <div class="card">
                <div class="card-body">
                    <h4>备份数据</h4>
                    <form action="/admin/backup/export" method="post">
                        <ul>
                            <li class="mb-1">此操作会对<strong>文章和资源</strong>进行备份，媒体资源使用 Base64 编码，如果资源<strong>大小超过 10M </strong>自动忽略备份</li>
                            <li class="mb-1">为了缩小备份文件体积, 建议在备份前删除不必要的数据</li>
                            <li class="mb-1">如果数据量过大, 为了避免操作超时, 建议勾选<strong>异步执行</strong></li>
                            <li><strong class="text-danger">备份文件存储在临时文件夹中，服务器重启会造成备份文件的丢失，请尽快下载</strong></li>
                        </ul>
    
                        <button class="btn btn-primary btn-sm" type="submit">开始备份</button>
    
                        <div class="custom-control custom-checkbox d-inline-block ml-2">
                            <input type="checkbox" class="custom-control-input" id="customCheck1" name="async" value="1">
                            <label class="custom-control-label" for="customCheck1">异步执行</label>
                        </div>
                    </form>
    
                    <div class="syso-divider font-normal"><span>历史文件 (${files?size})</span></div>
                    
                    <div>
                    <#if files?size gt 0>
                    <ul class="list-group list-group-flush">
                        <#list files as file>
                        <li class="list-group-item">
                            <a class="fs-15" href="/admin/backup/download/${file.name}">${file.name}</a>
                            <div class="small">
                              <span><@common.timeline time="${file.createTime}"?number />/<@common.sizeWord size="${file.size}"?number /></span>
                              <a class="ml-1 text-danger" href="/admin/backup/remove/${file.name}" onclick="if(confirm('确认要删除吗?')==false)return false;">删除</a>
                            </div>
                        </li>
                        </#list>
                    </ul>
                    <#else>
                        <div class="text-center text-muted">暂无数据</div>
                    </#if>
                    </div>
                </div> <!-- end card-body-->
            </div> <!-- end card-->
        </div> <!-- end col-->
    
        <div class="col-lg-5">
            <div class="card">
                <div class="card-body">
                    <h4 class="mb-4">恢复数据</h4>
                    
                    <form action="/admin/backup/import" method="post" enctype="multipart/form-data">
                        <p class="mb-4"><input name="file" type="file"></p>
                        <button type="submit" class="btn btn-primary btn-sm">上传并恢复</button>
                        <div class="custom-control custom-checkbox d-inline-block ml-2">
                            <input type="checkbox" class="custom-control-input" id="customCheck2" name="force" value="1">
                            <label class="custom-control-label" for="customCheck2">强制覆盖</label>
                        </div>
                        <p class="mt-3 text-danger"><strong>注：勾选强制覆盖将会清空原有的文章和资源数据</strong></p>
                    </form>
                </div> <!-- end card-body-->
            </div> <!-- end card-->
        </div> <!-- end col-->
    </div>

</div><!-- / .container-fluid -->

<@footer />