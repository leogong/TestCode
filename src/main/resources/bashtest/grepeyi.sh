#!/bin/bash

dir="/home/q/www/user/logs/access."
order_no=$1
day=$2
date=`date +%Y-%m-%d -d "$day days ago"`
grepStr="grep"
if [ $day -ne 0 ]; then
        grepStr="z"${grepStr}
        dir=${dir}${date}".log.gz"
else
        dir=${dir}${date}".log"
fi
$grepStr $order_no $dir
cookies=`$grepStr $order_no $dir | awk -F '"' '{print $8}' | sort | uniq `
ips=`$grepStr $order_no $dir | awk -F '"' '{print $9}' | sort | uniq `
IFS_old=$IFS
IFS=$'\n'
for cookie in $cookies
do
        if [ $cookie != "-" ] && [ $cookie != "" ]; then
                $grepStr $cookie $dir
        fi
done

for ip in $ips
do
        if [ $ip != "-" ] && [ $ip != "" ]; then
                $grepStr $ip $dir
        fi
done

IFS=$IFS_old

