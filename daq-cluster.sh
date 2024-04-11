#!/bin/bash

case $1 in
"start"){
        echo ================== 启动 集群 ==================

        #启动 Zookeeper集群
        zk-cluster.sh start

        #启动 Hadoop集群
        hadoop-cluster.sh start

        #启动 Kafka采集集群
        kafka.sh start

        #启动采集 Flume
        f1.sh start

        #启动日志消费 Flume
        f2.sh start

        #启动业务消费 Flume
        f3.sh start

        #启动 maxwell
        maxwell.sh start

        };;
"stop"){
        echo ================== 停止 集群 ==================

        #停止 Maxwell
        maxwell.sh stop

        #停止 业务消费Flume
        f3.sh stop

        #停止 日志消费Flume
        f2.sh stop

        #停止 日志采集Flume
        f1.sh stop

        #停止 Kafka采集集群
        kafka.sh stop

        #停止 Hadoop集群
        hadoop-cluster.sh stop

        #停止 Zookeeper集群
        zk-cluster.sh stop

};;
esac