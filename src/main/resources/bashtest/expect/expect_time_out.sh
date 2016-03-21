#!/usr/bin/expect
# Prompt function with timeout and default.

#脚本的第一部分首先是得到运行参数并将其保存到内部变量中
set prompt [lindex $argv 0]
set def [lindex $argv 1]
set response $def
set tout [lindex $argv 2]

send_tty "$prompt: "
#send_tty命令用来实现在终端上显示提示符字串和一个冒号及空格
set timeout $tout
#set timeout命令设置后面所有的expect命令的等待响应的超时时间为$tout(-l参数用来关闭任何超时设置)。
expect "\n" {
set raw $expect_out(buffer)

# remove final carriage return
set response [string trimright "$raw" " "]
}
if {"$response" == ""} {set response $def}
send "$response "

# Prompt function with timeout and default.
set prompt [lindex $argv 0]
set def [lindex $argv 1]
set response $def
set tout [lindex $argv 2]