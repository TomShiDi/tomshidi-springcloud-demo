#!/bin/bash

case $1 in
"start")
        echo " --------启动 node3 业务数据flume-------"
        ssh node3 "nohup /opt/server/flume-1.9.0/bin/flume-ng agent -n a1 -c /opt/server/flume-1.9.0/conf -f /opt/server/flume-1.9.0/job/kafka_to_hdfs_db.conf >/dev/null 2>&1 &"
;;
"stop")

        echo " --------停止 node3 业务数据flume-------"
        ssh node3 "ps -ef | grep kafka_to_hdfs_db.conf | grep -v grep |awk '{print \$2}' | xargs -n1 kill"
;;
esac