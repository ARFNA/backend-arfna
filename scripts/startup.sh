#!/bin/bash

echo "About to start up!"
current_dir=$(pwd)
war_file=${current_dir}/livewar-assembly/target/livewar-example.war
echo war_file
sudo java -jar ${war_file} --add-to-startd=jsp
