#!/bin/bash
if [ $# == 0 ]; then
  echo "请输入需要分发的文件或目录" && exit
fi

for host in node1 node2 node3
  do
    echo "********** ${host} **********"
    for file in "$@"
      do
        if [ -e $file ]; then
          pdir=$(cd -P $(dirname $file);pwd)
          filename=$(basename $file)
          ssh $host "mkdir -p $pdir"
          rsync -av $pdir/$filename $host:${pdir}
        else
          echo "文件/目录不存在!"
        fi
      done
  done