#!/bin/bash
if [ $# == 0 ]; then
  for host in node1 node2 node3
    do
      echo "********** $host **********"
      ssh $host "jps"
    done
  eval "exit"
else
  for host in node1 node2 node3
    do
      echo "********** $host **********"
      ssh $host "$@"
    done
    eval "exit"
fi