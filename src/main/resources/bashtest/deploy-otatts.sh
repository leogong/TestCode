#!/bin/sh
(
flock -n 200
if [ $? -eq 1 ]; then
	echo "有人正在部署";
	exit;
fi
cd /home/q
rm -rf otatts-common-api
sudo svn co --no-auth-cache xxxx otatts-common-api
cd otatts-common-api
sudo mvn -U clean deploy
) 200>/home/q/.lock2
