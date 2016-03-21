#!/usr/bin/expect
set ip [lindex $argv 0]
set code [lindex $argv 1]
set json [lindex $argv 2]
spawn ./client-tcp $ip
expect "code:" {
send "$code \n"
}
expect "json:" {
send "$json \n"
}
expect eof
exit