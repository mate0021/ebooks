#!/bin/usr/env bash
mysql -uroot -proot < /tmp/create_user.sql

# docker exec ebooks_securedb bash /tmp/init.sh