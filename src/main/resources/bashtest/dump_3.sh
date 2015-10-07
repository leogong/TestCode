#!/bin/sh
mysql_command="mysql -C -hxxxxx -pcx2iYageUbZ3PSXR -P3316 -uotsmisc --default-character-set=utf8 -A -N -s -e"
dbs=`$mysql_command 'select db_name from ota_information' otappb`
rootDir=/home/q/otstools

cd $rootDir

#do cleanup
rm -rf temp_dump_new_ip/*
rm -rf ctrip/*

now=$(date -d "now" +%Y-%m-%d\ %H:%M:%S)
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
				o.order_date
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
	cat temp_dump_new_ip/${db} | grep -Po "\d+\.\d+\.\d+\.\d+" | sort | uniq > temp_dump_new_ip/${db}_ips
	for ip in `cat temp_dump_new_ip/${db}_ips`
	do
		a=`echo $ip | grep -P "^(101.8[234]|119.39.248|.*)"`
		if [ ${#a} != 0 ]; then
			browser=`cat /home/q/rsyncd/otsmisc/u*/* | grep "$ip" | grep "Avant Browser" | head -1`
			if [ ${#browser} != 0 ]; then
				cat temp_dump_new_ip/${db} | grep "$ip" >> ctrip/${db}
				sh -x add_block.sh ip $ip n
			fi
		fi
	done
	if [ -f "ctrip/${db}" ]; then
		cat ctrip/${db} | awk '{print $1" "$2}' | xargs sh -x add_sus.sh $db 1 
	fi
	cat temp_dump_new_ip/${db} | awk '{print $(NF-1)" "$NF}' | sort -r | head -1 > scan_order_date/${db}_last_scan
	if [ ! -s "scan_order_date/${db}_last_scan" ]; then
		echo $now > scan_order_date/${db}_last_scan
	fi
} &
done
wait
echo "done"
