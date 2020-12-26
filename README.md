# Syso

Syso 是一个简单的个人博客系统 https://www.chuonye.com/

## 如何使用

在工程路径 `src/support/app-static/pages` 下，您可以静态预览前台和后台页面

下载 Syso 最新发布的稳定可执行版本的压缩包 [zip/tar.gz](https://github.com/chuondev/download/tree/main/syso)

```
 https://github.com/chuondev/download/raw/main/syso/syso-VERSION.tar.gz
```

在 Linux 上，将其解压到指定的安装位置（通常为 `/usr/local/syso`），会创建如下目录布局：

```
 syso-VERSION
 ├── bin
 │   ├── sysod.bat
 │   └── sysod.sh
 ├── conf
 │   ├── application.properties
 │   ├── application-prod.properties
 │   ├── ehcache.xml
 │   └── logback.xml
 ├── lib
 │   └── syso.jar
 ├── logs
 ├── support
 │   ├── nginx.conf
 │   └── syso.service
 ├── temp
 ├── LICENSE
 ├── README.md
 └── VERSION.txt
```
安装 Syso 操作命令如下：

```
 shell> cd /opt
 shell> tar zxvf /path/to/syso-VERSION.tar.gz
 shell> cd /usr/local
 shell> ln -s full-path-to-syso-VERSION syso
 shell> ln -s /usr/local/syso/support/syso.service /usr/lib/systemd/system/syso.service
```

如果要以服务的方式启停 Syso，需要把 syso.service 中的 JAVA_HOME 改为真实路径：

```
 Environment="JAVA_HOME=/usr/local/jdk1.8"
 或者
 Environment="JAVA_HOME=/usr/local/jdk1.8/jre"
```

改完后使用 `systemctl daemon-reload` 重新加载服务配置

发布的版本中，默认配置使用的是 MySQL 数据库，也可以修改使用 h2 数据库，如果使用 MySQL 需手动创建数据库和用户，命令如下：

```
 mysql> create database syso default character set utf8 collate utf8_general_ci;
 mysql> create user 'syso'@'127.0.0.1' identified by '132456';
 mysql> grant all privileges on syso.* to 'syso'@'127.0.0.1';
 mysql> flush privileges;
```

安装完成后使用 `systemctl start syso` 即可启动，默认绑定地址和端口 [127.0.0.1:9090](https://127.0.0.1:9090)

## 如何构建

构建时请执行：

```
 mvn clean install -Pprod
```

Syso 打包结果 `syso-VERSION.zip/.tar.gz` 将会放在 `syso/target` 

第一次构建花费的时间比较长，因为 Maven 要下载所有的依赖项，`-Pprod` 表示构建的是生产版本，默认配置会跳过 test 的执行

## TODOLIST
前台首页文章列表分页显示\
文章分类，标签功能\
文章同步到第三方平台\
后台页面响应式调试\
后台检测最新版本，手动选择系统更新\
后台一键清理冗余资源\
忘记密码找回（邮箱获取修改密码链接？）

## 版本历史

### syso-1.0-alpha
 + 系统安装自动初始化用户名和密码
 + 网站设置，自定义站点基本信息
 + 开发者工具，查看性能指标，系统资源
 + 备份与恢复，可强制恢复删除原有数据
 + 前台文章自动生成目录，页面添加点击回到顶部
 + 前台 rss sitemap
 + 前台响应式页面
 
 