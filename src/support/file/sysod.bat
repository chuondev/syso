@echo off

setlocal

rem 检测 Java 运行环境
if not "%JRE_HOME%" == "" goto gotJreHome
if not "%JAVA_HOME%" == "" goto gotJavaHome
echo 请设置 JAVA_HOME 或 JRE_HOME
goto end
:gotJavaHome
set "JRE_HOME=%JAVA_HOME%"
:gotJreHome
if not exist "%JRE_HOME%/bin/java.exe" goto noJreHome
goto okJava
:noJreHome
echo JRE_HOME 设置不正确
goto end
:okJava
set _RUNJAVA="%JRE_HOME%/bin/java.exe"

rem 设置程序变量
cd ..
set "SYSO_BASE=%cd%"
set "SYSO_TMPDIR=%SYSO_BASE%/temp"
set "SPRING_CONFIG=%SYSO_BASE%/conf/"
set "APP_JAR=%SYSO_BASE%/lib/syso.jar"

set "JAVA_OPTS=-server -Xms128m -Xmx128m -Xss228k -Xmn64m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -XX:+UseSerialGC"
set "JAVA_OPTS=%JAVA_OPTS% -Djava.awt.headless=true  -XX:MaxDirectMemorySize=10m -Djava.security.egd=file:/dev/./urandom"
set "JAVA_OPTS=%JAVA_OPTS% -XX:+AlwaysPreTouch -XX:-TieredCompilation -XX:+UseCompressedOops -XX:+UseCompressedClassPointers"
set "JAVA_OPTS=%JAVA_OPTS% -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=%SYSO_TMPDIR%/java_heapdump_pid_%p.log"

rem 运行信息
echo Using SYSO_BASE:   %SYSO_BASE%
echo Using SYSO_TMPDIR: %SYSO_TMPDIR%
echo Using JRE_HOME:    %JRE_HOME%
goto doStart

:doStart
%_RUNJAVA% %JAVA_OPTS% -Dsyso.base="%SYSO_BASE%" -Djava.io.tmpdir="%SYSO_TMPDIR%" -Dspring.config.location="%SPRING_CONFIG%" -jar %APP_JAR%
goto end

:end
