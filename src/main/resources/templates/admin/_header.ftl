<!-- Topbar-->
<div class="wk-navbar topnav-navbar">
    <div class="container-fluid">

        <!-- Brand -->
        <a href="/admin" class="topnav-logo">
            <span class="topnav-logo-lg">
                <img src="/static/images/logo.svg" alt="" height="24">
            </span>
        </a>

        <!-- Notification -->
        <ul class="list-unstyled topbar-right-menu float-right mb-0">
            
            <li class="notification-list">
                <a class="nav-link post-write text-primary" href="/admin/posts/write">
                    <i class="mdi mdi-feather"></i><span class="align-top">写文章</span>
                </a>
            </li>

            <li class="notification-list">
                <a class="nav-link syso-web" href="/" target="_blank" title="前台首页">
                    <i class="mdi mdi-web"></i>
                </a>
            </li>

            <li class=" notification-list">
                <a class="nav-link">
                    <i class="mdi mdi-bell-outline noti-icon"></i>
                </a>
            </li>

            <li class="dropdown notification-list">
                <a class="nav-link dropdown-toggle nav-user arrow-none mr-0" data-toggle="dropdown" id="topbar-userdrop" href="#" role="button" aria-haspopup="true"
                    aria-expanded="false">
                    <span class="user-avatar mr-1"> 
                        <img src="/static/images/avatar.jpg" alt="user-image" class="rounded-circle">
                    </span>
                    <span>
                        <span class="user-name">${_syso_user.nickname}</span>
                    </span>
                </a>
                <div class="dropdown-menu dropdown-menu-right dropdown-menu-animated topbar-dropdown-menu profile-dropdown" aria-labelledby="topbar-userdrop">
                    <!-- item-->
                    <a href="/admin/profile" class="dropdown-item notify-item">
                        <i class="mdi mdi-account-outline mr-1"></i>
                        <span>账号信息</span>
                    </a>

                    <!-- item-->
                    <a href="/admin/logout" class="dropdown-item notify-item">
                        <i class="mdi mdi-logout mr-1"></i>
                        <span>退出</span>
                    </a>
                </div>
            </li>
        </ul>
        
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
                        <a class="nav-link" href="/admin" role="button">控制台</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="/admin/posts" role="button">文章管理</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="/admin/medias" role="button">媒体资源</a>
                    </li>

                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle arrow-none" href="#" id="topnav-pages" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span>系统</span><div class="arrow-down"></div>
                        </a>
                        <div class="dropdown-menu" aria-labelledby="topnav-pages">
                            <a href="/admin/options" class="dropdown-item">网站设置</a>
                            <a href="/admin/backup" class="dropdown-item">备份与恢复</a>
                            <a href="/admin/devtools" class="dropdown-item">开发者工具</a>
                        </div>
                    </li>
                </ul>
            </div>
        </nav>

    </div>
</div>
<#include "_notice.ftl">