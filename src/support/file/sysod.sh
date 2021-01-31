#!/bin/sh

usage() {
  echo "Usage: ${0##*/} {start|stop|restart} "
  exit 1
}
[ $# -gt 0 ] || usage

# 确认 java 环境
if [ -z "$JAVA_HOME" -a -z "$JRE_HOME" ]; then
  JAVA_PATH=`which java 2>/dev/null`
  if [ "x$JAVA_PATH" != "x" ]; then
    JAVA_PATH=`dirname $JAVA_PATH 2>/dev/null`
    JRE_HOME=`dirname $JAVA_PATH 2>/dev/null`
  fi
  if [ "x$JRE_HOME" = "x" ]; then
    # XXX: Should we try other locations?
    if [ -x /usr/bin/java ]; then
      JRE_HOME=/usr
    fi
  fi

  if [ -z "$JAVA_HOME" -a -z "$JRE_HOME" ]; then
    echo "请设置 JAVA_HOME 或者 JRE_HOME"
    exit 1
  fi
fi

if [ -z "$JRE_HOME" ]; then
  JRE_HOME="$JAVA_HOME"
fi

if [ -z "$_RUNJAVA" ]; then
  _RUNJAVA="$JRE_HOME"/bin/java
fi

# 设置程序变量
PRGDIR=`dirname "$0"`
SYSO_BASE=`cd "$PRGDIR/.." >/dev/null; pwd`
SYSO_TMPDIR=$SYSO_BASE/temp
SPRING_CONFIG=$SYSO_BASE/conf/
APP_JAR=$SYSO_BASE/lib/syso.jar

JAVA_OPTS="-Xms128m -Xmx128m -Xss228k -Xmn64m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -XX:+UseSerialGC"
JAVA_OPTS="$JAVA_OPTS -Djava.awt.headless=true  -XX:MaxDirectMemorySize=10m -Djava.security.egd=file:/dev/./urandom"
JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$SYSO_TMPDIR/java_heapdump_pid_%p.log"

echo Using SYSO_BASE:   $SYSO_BASE
echo Using SYSO_TMPDIR: $SYSO_TMPDIR
echo Using JRE_HOME:    $JRE_HOME

pid=
# 检查是否正在运行
running() {
  pid=`ps auxww| grep "$APP_JAR" | grep -v grep | awk '{print $2}'`
  if [ -n "$pid" ];then
    return 0
  fi
  return 1
}

ACTION=$1
# Do the action
case "$ACTION" in
  start)
    if running; then
      echo "Syso Running at $pid"
      exit 1
    fi

    nohup "$_RUNJAVA" $JAVA_OPTS \
      -Dsyso.base="$SYSO_BASE" \
      -Djava.io.tmpdir="$SYSO_TMPDIR" \
      -Dspring.config.location="$SPRING_CONFIG" \
      -jar $APP_JAR \
      >/dev/null 2>&1 &

    pid=$!
    echo "Syso started at - $pid"
    ;;

  stop)
    if running; then
      kill "$pid" 2>/dev/null
      echo "Syso stopped."
    else
      echo "Syso not running."
      exit 1
    fi
    ;;

  restart)
    if running; then
      echo "Stopping syso ... "
      kill "$pid" 2>/dev/null
    fi
    sleep 1
    SYSOD_SH=$0
    if [ ! -f $SYSOD_SH ]; then
      SYSOD_SH=$SYSO_BASE/bin/sysod.sh
    fi
    
    "$SYSOD_SH" start "$@"
    ;;

  *)
    usage
    ;;
esac

exit 143
