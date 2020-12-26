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
                        <li class="breadcrumb-item active">媒体资源</li>
                    </ol>
                </div>
                <h4 class="page-title">媒体资源</h4>
            </div>
        </div>
    </div> <!-- / .row -->
    
    <div class="row">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <div class="mb-3">
                        <form action="/admin/medias/upload" method="post" enctype="multipart/form-data">
                            <button type="submit" class="btn btn-sm btn-primary">上传</button>
                            <input type="file" name="files" multiple="">
                        </form>
                    </div>
                    <div class="table-responsive-sm">
                        <form method="post" id="table-form">
                        <table class="table table-hover table-centered mb-0">
                            <colgroup>
                                <col width="1%"/>
                                <col width="8%"/>
                                <col width="8%"/>
                                <col width="6%"/>
                                <col width="6%"/>
                                <col width="25%"/>
                                <col width="4%"/>
                            </colgroup>
                            <thead>
                                <tr>
                                    <th><input type="checkbox" class="select-all" value=""></th>
                                    <th>文件名</th>
                                    <th>文件类型</th>
                                    <th>文件大小</th>
                                    <th>上传日期</th>
                                    <th>路径</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <#if medias.content?size gt 0>
                                <#list medias.content as media>
                                  <tr class="wk-table-row">
                                        <td><input type="checkbox" class="select-checkbox" value="${media.id}" name="mediaId[]"></td>
                                        <td>${media.name}</td>
                                        <td>${media.mimeType}</td>
                                        <td><@common.sizeWord size="${media.size}"?number /></td>
                                        <td><@common.timeline time="${media.createTime}"?datetime /></td>
                                        <td><a target="_blank" href="/${media.path}">/${media.path}</a></td>
                                        <td class="table-action">
                                            <a href="/admin/medias/remove?mediaId=${media.id}" onclick="if(confirm('确认要删除吗?')==false)return false;">删除</a>
                                        </td>
                                    </tr>
                                </#list>
                                <#else>
                                 <tr><td colspan="7" class="text-center">暂无数据</td></tr>
                                </#if>
                            </tbody>
                        </table>
                        </form>
                    </div> <!-- end table-responsive-->
                    
                    <div class="row no-gutters mt-3">
                        <div class="col-6">
                            <div class="dropdown">
                                <button class="btn btn-light btn-sm dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" data-display="static" aria-haspopup="true" aria-expanded="false">
                                                                    批量操作
                                </button>
                                <div class="dropdown-menu btn-operate" aria-labelledby="dropdownMenuButton">
                                    <a class="dropdown-item" lang="确认要删除这些资源吗?" href="/admin/medias/batchRemove">删除</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-6">
                        <@pagination url="/admin/medias" page=medias />
                        </div>
                    </div>

                </div> <!-- end card body-->
            </div> <!-- end card -->
        </div><!-- end col-->
    </div> <!-- / .row -->

</div><!-- / .container-fluid -->

<@footer>
</@footer>