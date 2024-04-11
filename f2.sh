#! /bin/bash
if [ $# == 0 ];then
  echo -e "请输入参数：\nstart   启动日志消费flume；\nstop   关闭日志消费flume；"&&exit
fi
case $1 in
"start"){
      echo " --------启动 node3 消费flume-------"
      ssh node3 "nohup /opt/server/flume-1.9.0/bin/flume-ng agent --conf-file /opt/server/flume-1.9.0/job/flume-kafka-hdfs.conf --conf /opt/server/flume-1.9.0/conf --name a1 -Dflume.root.logger=INFO,LOGFILE >/opt/server/flume-1.9.0/logs/flume.log  2>&1 &"
};;

"stop"){
      echo "---------- 停止 node3 上的 日志消费flume ----------"
      flume_count=$(xcall jps -ml | grep flume-kafka-hdfs|wc -l);
      if [ $flume_count != 0 ];then
          ssh node3 "ps -ef | grep flume-kafka-hdfs | grep -v grep | awk '{print \$2}' | xargs -n1 kill -9"
      else
          echo " node3 当前没有日志采集flume在运行"
      fi
  };;
esac