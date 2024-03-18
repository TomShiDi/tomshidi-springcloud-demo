#!/bin/bash

MAXWELL_HOME=/opt/server/maxwell

status_maxwell() {
  result=`ps -ef | grep com.zendesk.maxwell.Maxwell | grep -v grep | wc -l`
  return $result
}

start_maxwell() {
  status_maxwell
  if [[ $? -lt 1 ]]; then
    echo "启动Maxwell"
    $MAXWELL_HOME/bin/maxwell --config $MAXWELL_HOME/config.properties --daemon
  else
    echo "Maxwell已在运行中"
  fi
}

stop_maxwell() {
  status_maxwell
  if [[ $? -gt 0 ]]; then
    echo "停止Maxwell"
    ps -ef | grep com.zendesk.maxwell.Maxwell | grep -v grep | awk '{print $2}' | xargs kill -9
  else
    echo "Maxwell未运行"
  fi
}


case $1 in
  start)
    start_maxwell
  ;;
  stop)
    stop_maxwell
  ;;
  restart)
    stop_maxwell
    start_maxwell
  ;;
  *)
    echo -e "请输入参数：\nstart  启动Maxwell；\nstop  停止Maxwell；\nrestart  重启Maxwell；\n"
  ;;
esac