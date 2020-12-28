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
                        <li class="breadcrumb-item active">文章管理</li>
                    </ol>
                </div>
                <h4 class="page-title">文章管理</h4>
            </div>
        </div>
    </div> <!-- / .row -->

    <div class="row">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <!-- <div class="row no-gutters">
                        <div class="col-6">
                        </div>
                        <div class="col-6 text-right">
                            <label><input type="search" class="form-control form-control-sm" placeholder="关键词"></label>
                        </div>
                    </div> -->
                    <div class="mb-3">
                        <form action="/admin/posts/markdown" method="post" enctype="multipart/form-data">
                            <button type="submit" class="btn btn-sm btn-primary">导入 Markdown</button>
                            <input type="file" name="file" multiple="">
                        </form>
                    </div>
                    <div class="table-responsive-sm">
                        <form method="post" id="table-form">
                        <table class="table table-hover table-centered mb-0">
                            <colgroup>
                                <col width="1%"/>
                                <col width="30%"/>
                                <col width="5%"/>
                                <col width="6%"/>
                                <col width="6%"/>
                                <col width="6%"/>
                                <col width="8%"/>
                            </colgroup>
                            <thead>
                                <tr>
                                    <th><input type="checkbox" class="select-all" value=""></th>
                                    <th>标题</th>
                                    <th>状态</th>
                                    <th>阅读数</th>
                                    <th>发布时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            <#if posts.content?size gt 0>
                                <#list posts.content as post>
                                  <tr class="wk-table-row">
                                      <td><input type="checkbox" class="select-checkbox" value="${post.id}" name="postId[]"></td>
                                      <#if post.status == 0>
                                          <td><a target="_blank" href="/archives/${post.slug}.html">${post.title}</a></td>
                                          <td>已发布</td>
                                      <#else>
                                          <td>${post.title}<a class="ml-1 small" target="_blank" href="/admin/posts/preview/${post.id}">预览</a></td>
                                          <td>草稿</td>
                                      </#if>    
                                      <td>${post.views}</td>
                                      <td><@common.timeline time="${post.updateTime}"?datetime /></td>
                                      <td class="table-action">
                                          <#if post.status == 1>
                                          <a href="/admin/posts/publish/${post.id}">发布</i></a>
                                          </#if>
                                          <a href="/admin/posts/modify/${post.id}">编辑</i></a>
                                          <a href="/admin/posts/remove/${post.id}" onclick="if(confirm('确认要删除吗?')==false)return false;">删除</i></a>
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
                                    <a class="dropdown-item" lang="确认要发布这些文章吗?" href="/admin/posts/batchPublish">发布</a>
                                    <a class="dropdown-item" lang="确认要删除这些文章吗?" href="/admin/posts/batchRemove">删除</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-6">
                        <@pagination url="/admin/posts" page=posts />
                        </div>
                    </div>

                </div> <!-- end card body-->
            </div> <!-- end card -->
        </div><!-- end col-->
    </div> <!-- / .row -->
    
</div><!-- / .container-fluid -->

<@footer />