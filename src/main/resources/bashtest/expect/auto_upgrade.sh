#!/usr/bin/env bash

function check_hex_result()
{
    if [ ! -n $1 ]; then
        echo "upgrade \""$2"\" result is not OK!,vsm_uid:"$3",hex of result:"$1
        exit 1
    fi

}
ip="10.97.228.60"
call_back_url="http://10.189.91.113/callback.php"
vsm_wk_digest="hVAUkrxxZGOk7fuwKsx2nUVkQmvbWz7It5kU+vrUvLs="
vsm_wk_pack_version="VW0.00.21"
vsm_service_digest="Wiv2OctBc5ADFtDWEjANXyawu96u2vL2X271wOURJho="
vsm_service_pack_version="EH1.24.01"
vsm_tools_digest="RJXz/K7zow5VLRbOYEPn/iuDjpxx5jK9aS2Ihvb3pf0="
vsm_tools_pack_version="ET1.00.02"
pack_url_prefix="http://10.189.91.113/hsm_20160307"
interactive_shell="./expect_hsm.sh"
check_vsm_uid_code="11"
upgrade_vsm_code="6"
check_vsm_upgrade_result_code="21"
request_id="\"requestid\":\"testzz"
vsm_uids=`${interactive_shell} ${ip} ${check_vsm_uid_code} "{"${request_id}"\"}" | grep -Po "(?<=\"vsmids\": \[).*?(?=\])" | sed "s/\"//g" |  xargs -d "," -n 1 | sed "s/ //g"`
IFS_old=$IFS
IFS=$'\n'

for vsm_uid in ${vsm_uids}
do
echo -n "${vsm_uid} "

##vsm service upgrade
#upgrade_result_str=`${interactive_shell} ${ip} ${upgrade_vsm_code} "{"${request_id}",\"vsmid\":\""${vsm_uid}"\",\"version\":\"${vsm_service_pack_version}\",\"packurl\":\""${pack_url_prefix}"/EVSM-SERVER_"${vsm_wk_pack_version}".pup""\",\"alg\":1,\"digest\":\""${vsm_tools_digest}"\",\"callbackurl\":\""${call_back_url}"\"}" |tail -2|sed "s/ //g"`
#hex_of_result=`echo ${upgrade_result_str} | grep -P "\w+0000"`
###check result code
#$(check_hex_result ${hex_of_result} "vsm_service" ${vsm_uid});

#vsm tools upgrade
upgrade_result_str=`${interactive_shell} ${ip} ${upgrade_vsm_code} "{"${request_id}"_tools_"${vsm_uid}"\",\"vsmid\":\""${vsm_uid}"\",\"version\":\"${vsm_tools_pack_version}\",\"packurl\":\""${pack_url_prefix}"/EVSM-TOOLS_"${vsm_tools_pack_version}".pup""\",\"alg\":1,\"digest\":\""${vsm_tools_digest}"\",\"callbackurl\":\""${call_back_url}"\"}"` # |tail -2|sed "s/ //g"
#hex_of_result=`echo ${upgrade_result_str} | grep -P "\w+0000"`
#check result code
#$(check_hex_result ${hex_of_result} "vsm_service" ${vsm_uid});

echo -n " tools ok ; "

sleep 3

#vsm worker upgrade
upgrade_result_str=`${interactive_shell} ${ip} ${upgrade_vsm_code} "{"${request_id}"_worker_"${vsm_uid}"\",\"vsmid\":\""${vsm_uid}"\",\"version\":\"${vsm_wk_pack_version}\",\"packurl\":\""${pack_url_prefix}"/VSMS-WK_"${vsm_wk_pack_version}".pup""\",\"alg\":1,\"digest\":\""${vsm_wk_digest}"\",\"callbackurl\":\""${call_back_url}"\"}" `#|tail -2|sed "s/ //g"
hex_of_result=`echo ${upgrade_result_str} | grep -P "\w+0000"`
echo -n " worker ok ; "
sleep 3
#check result code
#$(check_hex_result ${hex_of_result} "vsm_work" ${vsm_uid});

#check vsm version & status
#vsm_check_str=`${interactive_shell} ${ip} ${check_vsm_upgrade_result_code} "{"${request_id}",\"vsmid\":\""${vsm_uid}"\"}" | grep -E ".*vsmid.*status.*"`
#vsm_version_after_upgrade=`echo ${vsm_check_str} | grep -Po "(?<=version\": \").*?(?=\",)"`
#vsm_status_after_upgrade=`echo ${vsm_check_str} | grep -Po "(?<=status\": \").*?(?=\",)"`

#if [ ${vsm_version_after_upgrade} != ${vsm_service_pack_version} ]; then
#    echo version is NOT OK. To upgrade to version of ${vsm_service_pack_version}, but now version is ${vsm_version_after_upgrade}
#    exit 1
#fi
#echo ${vsm_status_after_upgrade}
#if [ ${vsm_status_after_upgrade} != "ok" ]; then
#    echo -n status is not OK. now status is ${vsm_status_after_upgrade}
#    exit 1
#fi
echo everything is ok!
done
IFS=${IFS_old}
exit