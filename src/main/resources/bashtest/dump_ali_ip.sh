#!/bin/sh
mysql_command="mysql -C -hxxx -pcx2iYageUbZ3PSXR -P3316 -uotsmisc --default-character-set=utf8 -A -N -s -e"
dbs=`$mysql_command 'select db_name from ota_information' otappb`
rootDir=/home/q/otstools

cd $rootDir

#do cleanup
rm -rf temp_dump_new_ip/*
rm -rf ctrip/*

now=$(date -d "now" +%Y-%m-%d\ %H:%M:%S)
#now="2013-5-15"
#dbs=otappb_f3js
for db in $dbs
do
{
	if [ -s "scan_order_date/${db}_last_scan" ]; then  
		last_date=`cat scan_order_date/${db}_last_scan`
	else
		last_date="$now"
	fi
	$mysql_command "select o.id, 
				o.order_num, 
				o.contact_phone,
				p.content, 
				o.user_name,
				o.order_date,
				ex.pay_account
			from order_info o 
			inner join op_log p 
			on 
				p.op_type = \"order\" and p.data_id = o.id and p.content like \"创建订单%\" 
			left join order_pay_extra ex 
			on 
				ex.transaction_id = o.order_num left join suspected_order s on s.order_id = o.id 
			where 
				order_date >= \"$last_date\"" \
	$db > temp_dump_new_ip/${db}
	cat temp_dump_new_ip/${db} | grep -Po "\d+\.\d+\.\d+\.\d+" | grep -P "^115\.17[01]|1\.203"|sort | uniq > temp_dump_new_ip/${db}_ips
	for ip in `cat temp_dump_new_ip/${db}_ips`
	do
		a=`cat temp_dump_new_ip/${db}| grep "$ip" | grep kehuzijinbu`
		if [ ${#a} != 0 ]; then
			echo $a >> ctrip/${db}
			sh -x add_block.sh ip $ip n
		fi
	done
	if [ -f "ctrip/${db}" ]; then
		cat ctrip/${db} | awk '{print $1" "$2}' | xargs sh -x add_sus.sh $db 1
	fi
	cat temp_dump_new_ip/${db} | awk '{print $(NF-2)" "$(NF-1)}' | sort -r | head -1 > scan_order_date/${db}_last_scan
	if [ ! -s "scan_order_date/${db}_last_scan" ]; then
		echo $now > scan_order_date/${db}_last_scan
	fi
} &
done
wait
echo "done"
