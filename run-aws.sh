#!/bin/bash

java -Xms64m -Xmx512m -Dhazelcast.config=config/hazelcast-aws.xml -jar target/sudokufeud-1.0-SNAPSHOT.jar
