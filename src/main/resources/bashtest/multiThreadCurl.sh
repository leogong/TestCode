#!/usr/bin/env bash
!/bin/bash
times=$1
for ((i=0;i<$times;i++));do
	{
		curl --data "x=0&y=5&z=1" --cookie "x=1;y=2"  "url" > /dev/null;echo "done!"
	}&
done
wait
echo "all done!"