<#include "_macros.ftl">
<@header title="写文章" />
<form id="post-form" action="${post.id???then("/admin/posts/modify/${post.id}","/admin/posts/new")}" method="post">
<div class="container-fluid">
    <div class="card post-editor">
        <div class="card-body">
            <div class="editor-title">
              <input type="text" name="title" autocomplete="off" value="${post.title!}" placeholder="请输入文章标题">
            </div>
      
            <div class="editor-content vh-100">
              <textarea autocomplete="off" id="content" name="content" placeholder="输入正文 Markdown">${post.rawContent!}</textarea>
            </div>

            <div class="editor-setting">
              <label class="col-form-label col-sm-2">永久链接</label>
              <div class="col-sm-10 mono d-flex align-items-center">
                <span>localhost/blog/</span><input class="slug-input text-muted" type="text" name="slug" value="${post.slug!}"><span>.html</span>
              </div>
            </div>

        </div> <!-- end card body-->
    </div> <!-- end card -->
</div><!-- / .container-fluid -->

<div class="editor-footer">
    <div class="container-fluid d-flex">
        <div class="d-flex flex-fill align-items-center post-tip fs-14">
            <span class="post-tip-setting dropdown-toggle mr-3"><a>文章设置</a></span>
            <span class="post-tip-save text-muted"></span>
        </div>

        <button name="action" value="published" type="submit" class="border-primary btn btn-primary btn-sm mr-4">发布</button>
        <button name="action" value="draft" type="submit" class="btn btn-light btn-sm mr-4">存草稿</button>
      </div>
</div>
</form>
<@footer standard=false>
<script type="text/javascript">
    $(document).ready(function() {
        "use strict";
        
    });
</script>
</@footer>