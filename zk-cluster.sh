#!/bin/bash
if [ $# -eq 0 ]; then
    echo -e "请输入参数：\nstart  启动ZK集群；\nstop  关闭ZK集群；\nstatus  查看ZK状态；" && exit
fi

case $1 in
"start")
  for host in node1 node2 node3
    do
      echo "========== ${host} 启动zookeeper =========="
      ssh $host "/opt/server/zookeeper-3.5.7/bin/zkServer.sh start"
    done
  ;;
"stop")
  for host in node1 node2 node3
    do
      echo "========== ${host} 关闭zookeeper =========="
      ssh $host "/opt/server/zookeeper-3.5.7/bin/zkServer.sh stop"
    done
  ;;
"status")
  for host in node1 node2 node3
    do
      echo "========== ${host} zookeeper状态 =========="
      ssh $host "/opt/server/zookeeper-3.5.7/bin/zkServer.sh status"
    done
  ;;
*)
  echo -e "请输入参数：\nstart  启动ZK集群；\nstop  关闭ZK集群；\nstatus  查看ZK状态；"
  ;;
esac