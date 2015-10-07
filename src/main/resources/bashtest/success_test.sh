#!/bin/bash
#FileName:success_test.sh
CMD="ls -a"
status
$CMD
if [ $? -eq 0 ];
then
echo "$CMD executed successfully"
else
echo "$CMD executed unsuccessfully"
fi
