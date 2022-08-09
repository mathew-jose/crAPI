#!/bin/bash
while :
do
	newman run "crAPI Accepted.postman_collection.json" -e Crapi.postman_environment.json --delay-request 200
	sleep 180
done