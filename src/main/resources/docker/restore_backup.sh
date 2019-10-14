#!/usr/bin/env bash

cat backup.sql | docker exec -i ebooks_base /usr/bin/mysql -u root --password=adminadmin