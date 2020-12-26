<#include "_macros.ftl">
<@header title="账号设置" />
<div class="container-fluid">
    <!-- Page title -->
    <div class="row">
        <div class="col-12">
            <div class="page-title-box">
                <div class="page-title-right">
                    <ol class="breadcrumb m-0">
                        <li class="breadcrumb-item"><a href="/admin">首页</a></li>
                        <li class="breadcrumb-item active">账号设置</li>
                    </ol>
                </div>
                <h4 class="page-title">账号设置</h4>
            </div>
        </div>
    </div> <!-- / .row -->

    <div class="row">
      <div class="col-xl-4 col-lg-5">
        <div class="card text-center">
          <div class="card-body">
            <img src="/static/images/avatar.jpg" class="rounded-circle avatar-lg img-thumbnail" alt="profile-image" style="width: 5rem;height: 5rem;">

            <h4 class="mb-4 mt-2">${user.nickname}</h4>

            <div class="text-left mt-3">
              <p class="text-muted mb-2 fs-13"><strong>邮箱 :</strong> <span class="ml-2">${user.email!"user@email.domain"}</span></p>
              <p class="text-muted mb-2 fs-13"><strong>创建时间 :</strong> <span class="ml-2">${user.createTime}</span></p>
              <p class="text-muted mb-2 fs-13"><strong>最后登录 :</strong> <span class="ml-2"><@common.timeline time="${user.lastLogin}"?datetime /></span></p>
            </div>

          </div> <!-- end card-body -->
        </div> <!-- end card-->
      </div> <!-- end col-->

        <div class="col-xl-8 col-lg-7">
            <div class="card">
                <div class="card-body">
                  <h3 class="fs-18">账号信息</h3>
                  <form action="/admin/profile/modify" method="post">
                    <div class="form-group">
                      <label class="input-required">登录名</label>
                      <input type="text" class="form-control form-control-sm" name="name" value="${user.name}" required>
                      <small class="form-text text-muted">登录名独一无二，用于登录，默认为 <strong>syso</strong></small>
                    </div>
                    <div class="form-group">
                      <label class="input-required">昵称</label>
                      <input type="text" class="form-control form-control-sm" name="nickname" value="${user.nickname}" required>
                      <small class="form-text text-muted">用于页面显示，可以与登录名不同，如果为空则默认等于登录名</small>
                    </div>
                    <div class="form-group">
                      <label>邮箱地址</label>
                      <input type="email" class="form-control form-control-sm" name="email" value="${user.email!""}" placeholder="邮件发送暂未实现" disabled>
                      <small class="form-text text-muted">邮箱地址也用于登录，忘记密码时此地址是联系用户的唯一方式</small>
                    </div>
                    <input type="hidden" name="userId" value="${user.id}">
                    <button type="submit" class="btn btn-primary">保存</button>
                  </form>
                    
                  <h3 class="fs-18 mt-5">修改密码</h3>
                  <form action="/admin/profile/passwd/${user.id}" method="post">
                    <div class="form-group">
                      <label class="input-required">密码</label>
                      <input type="password" class="form-control form-control-sm" name="passwd" required>
                      <small class="form-text text-muted">建议使用特殊字符与字母、数字的混合，增加系统安全性</small>
                    </div>
                    <div class="form-group">
                      <label class="input-required">确认密码</label>
                      <input type="password" class="form-control form-control-sm" name="confirm" required>
                      <small class="form-text text-muted">确认输入密码, 与上面输入的保持一致</small>
                    </div>
                    <button type="submit" class="btn btn-primary">确认修改</button>
                  </form>

                </div> <!-- end card-body-->
            </div> <!-- end card-->
        </div> <!-- end col-->
        
       
    </div><!-- / .row -->

</div><!-- / .container-fluid -->

<@footer />