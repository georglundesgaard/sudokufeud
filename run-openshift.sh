#!/bin/bash

#CMD_ARGS="-Xms64m -Xmx512m -Dhazelcast.config=config/hazelcast-aws.xml"
CMD_ARGS="-Xms64m -Xmx512m -Dserver.address=${OPENSHIFT_DIY_IP} -Dserver.port=${OPENSHIFT_DIY_PORT}"

java ${CMD_ARGS} -jar sudokufeud-1.0-SNAPSHOT.jar
