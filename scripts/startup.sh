#!/bin/bash

echo "About to start up!"
current_dir=$(pwd)
deployment_dir=${current_dir}/deployment-root/$DEPLOYMENT_GROUP_ID/$DEPLOYMENT_ID/deployment-archive
war_file=${deployment_dir}/livewar-assembly/target/livewar-example.war
log_file=${deployment_dir}/logs.txt
java -jar ${war_file} --add-to-startd=jsp >> ${log_file} 2>&1 &
