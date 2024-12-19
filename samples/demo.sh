#!/bin/bash

# check if commands exist
if ! command -v  curl 2>&1> /dev/null
then
    echo "curl could not be found"
    exit 1
fi

if ! command -v  jq 2>&1> /dev/null
then
    echo "jq could not be found"
    exit 1
fi

if ! command -v  unzip 2>&1> /dev/null
then
    echo "unzip could not be found"
    exit 1
fi

if ! command -v  base64 2>&1> /dev/null
then
    echo "base64 could not be found"
    exit 1
fi

# register new account
curl -v -d '{"username":"testo", "password":"test123123"}' -H "Content-Type: application/json" -X POST http://localhost:8080/api/v1/account

# authenticate, save session in cookies.txt and retrieve account id
response_account=$(curl --cookie-jar cookies.txt --user testo:test123123 http://localhost:8080/api/v1/account)
account_id=$(echo $response_account | jq ".id" | xargs)

# upload multiple files
curl -v -b cookies.txt http://localhost:8080/api/v1/account/${account_id}/file \
-F "files=@files/cat.jpg" \
-F "files=@files/lorum_ipsum.pdf" \
-F "files=@files/lorum_ipsum.txt"

# request FIRST portion of uploaded files and save as request1.zip
curl  -b cookies.txt "http://localhost:8080/api/v1/account/${account_id}/file?pageSize=2&pageNumber=0&sortBy=CREATED_DATE" | \
jq -r .content | base64 -d > request1.zip
unzip request1.zip

# request SECOND portion of uploaded files and save as request2.zip
curl  -b cookies.txt "http://localhost:8080/api/v1/account/${account_id}/file?pageSize=2&pageNumber=1&sortBy=CREATED_DATE" | \
jq -r .content | base64 -d > request2.zip
unzip request2.zip