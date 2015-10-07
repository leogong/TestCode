#!/bin/bash
#用途:验证排序
sort -C args.txt ;
if [ $? -eq 0 ]; then
	echo Sorted;
else
	echo Unsorted;
fi
