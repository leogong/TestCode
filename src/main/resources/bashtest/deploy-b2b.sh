#!/bin/sh
(
flock -n 200
if [ $? -eq 1 ]; then
	echo "有人正在部署";
	exit;
fi
cd /home/q/
rm -rf botatts-common-api
sudo svn co --no-auth-cache http://xxx
cd botatts-common-api
sudo mvn -U clean deploy
) 200</home/q/.lock1
