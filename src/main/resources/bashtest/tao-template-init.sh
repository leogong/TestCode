#!/usr/bin/env bash

tar -zxvf admin_with_clean.tar.gz
tar -zxvf tao.tar.gz
sudo rm -rf /home/admin/*
sudo mv home/admin/* /home/admin/
sudo chown -R admin.admin /home/admin/
sudo chmod -R 755  /home/admin/
sudo rm -rf /opt/taob*
sudo mv opt/taob* /opt/
sudo chown -R root.root /opt/taob*
sudo yum install -y tengine-proxy-2.0.7-18475.el5u4 -b current
sudo ps -ef | grep nginx | grep -v grep | awk '{print $2}' | sudo xargs kill -9