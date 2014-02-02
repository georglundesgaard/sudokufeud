#!/bin/bash

java -Xms64m -Xmx512m -Dhazelcast.config=config/hazelcast-aws.xml -jar sudokufeud-api/target/sudokufeud-api-1.0-SNAPSHOT.jar
