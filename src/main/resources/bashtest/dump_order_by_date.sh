#!/bin/sh
mysql_command="mysql -C -hxxxx -pcx2iYageUbZ3PSXR -P3316 -uotsmisc --default-character-set=utf8 -A -N -s -e"
dbs=`$mysql_command 'select db_name from ota_information' otappb`
for db in $dbs
do
{
	$mysql_command "select o.order_num, 
				o.contact_phone, 
				o.contact_name, 
				o.customer_names, 
				o.hotel_name, 
				p.content
			from order_info o 
			inner join op_log p 
			on 
				p.op_type = \"order\" and p.data_id = o.id and p.content like \"创建订单%\" 
			left join order_pay_extra ex 
			on 
				ex.transaction_id = o.order_num left join suspected_order s on s.order_id = o.id where o.order_date between \"$1\" and \"$1 23:59:59\"" \
	$db >> /home/q/otstools/order_$1
} 
done
wait
echo "done"
