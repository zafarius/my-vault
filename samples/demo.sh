#!/bin/bash
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

# request all saved files and save as download.zip
curl --output download.zip -b cookies.txt http://localhost:8080/api/v1/account/${account_id}/file

# unzip
unzip download.zip