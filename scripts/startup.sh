#!/bin/bash

echo "About to start up!"
current_dir=$(pwd)
deployment_group_deployment_dir=${current_dir}/deployment-root/$DEPLOYMENT_GROUP_ID/$DEPLOYMENT_ID/deployment-archive
deployment_group_war_file=${deployment_group_deployment_dir}/livewar-assembly/target/livewar-example.war
deployment_dir=/deployemnt
log_file=${deployment_dir}/logs.txt
echo "Copying file to main deployment"
war_file=${deployment_dir}/arfna-backend.war
mkdir ${deployment_dir} && cp "${deployment_group_war_file}" ${war_file}
java -jar ${war_file} --add-to-startd=jsp >> ${log_file} 2>&1 &
