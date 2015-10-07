#!/bin/sh
mysql_command="mysql -C -h192.168.224.184 -pgxsrT7ijyo1pzblvrZ -P3306 -uotsmisc --default-character-set=utf8 -A -N -s -e"
status=`$mysql_command "select status from suspected_order where order_id = \"$3\" and status = 1" $1`
if [ ${#status} -eq 0 ]; then
	$mysql_command "insert into suspected_order(order_id, order_num, status) values (\"$3\", \"$4\", \"$2\") on duplicate key update status = values(status)" $1
	$mysql_command "insert ignore into op_log(op_type, data_id, operator, content, operator_type) values (\"order\", \"$3\", \"系统\", \"恶意订单\", \"SYSTEM\")" $1
	sh -x send_mail.sh $4
fi
