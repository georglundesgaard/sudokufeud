#!/bin/bash

if [ $1 ]
then
	curl -X POST admin:$1@localhost:8080/shutdown
else
	echo "Usage: $0 admin-password"
fi
