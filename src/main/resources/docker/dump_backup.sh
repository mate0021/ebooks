#!/usr/bin/env bash

docker exec ebooks_base /usr/bin/mysqldump -u root --password=adminadmin ebooks > backup.sql