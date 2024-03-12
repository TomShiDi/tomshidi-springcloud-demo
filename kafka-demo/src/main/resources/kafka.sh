#!/bin/bash
# 1.判断参数个数
if [[ $# != 1 ]];then
  echo -e "请输入参数：\nstart    启动kafka集群；\nstop    关闭kafka集群" && exit
fi

# 2.执行
case $1 in
  "start")
    for host in node1 node2 node3
      do
        echo "========== $host 启动kafka集群 =========="
        ssh $host "kafka-server-start.sh -daemon /opt/server/kafka/config/server.properties"
      done
  ;;
  "stop")
    for host in node1 node2 node3
        do
          echo "========== $host 停止kafka集群 =========="
          ssh $host "kafka-server-stop.sh"
        done
  ;;
  *)
    echo -e "请输入参数：\nstart    启动kafka集群；\nstop    关闭kafka集群"
  ;;
esac