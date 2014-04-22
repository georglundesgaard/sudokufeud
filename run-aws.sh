#!/bin/bash

#CMD_ARGS="-Xms64m -Xmx512m -Dhazelcast.config=config/hazelcast-aws.xml"
CMD_ARGS="-Xms64m -Xmx512m"

if [ -e target ]
then
	java ${CMD_ARGS} -jar target/sudokufeud-1.0-SNAPSHOT.jar
else
	java ${CMD_ARGS} -jar sudokufeud-1.0-SNAPSHOT.jar
fi
