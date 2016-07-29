#!/bin/bash

apt-get update

# We want to add Oracle's repositry to get jdk1.8
apt-get install -y software-properties-common
apt-get install -y python-software-properties
add-apt-repository ppa:webupd8team/java
apt-get update

# As java8 has an interactive installer, we need to
# manualy agree with terms and conditions, not to 
# interrupt the provisioning process
echo "debconf shared/accepted-oracle-license-v1-1 select true" | \
	debconf-set-selections
echo "debconf shared/accepted-oracle-license-v1-1 seen true" | \
	debconf-set-selections

apt-get install -y oracle-java8-installer
apt-get install -y maven

# echo "mysql-server mysql-server/root_password password" | \
# 	debconf-set-selections
# echo "mysql-server mysql-server/root_password_again password" | \
# 	debconf-set-selections
export DEBIAN_FRONTEND=noninteractive
sudo -E apt-get -y -q install mysql-server

mysql -e "CREATE DATABASE imageboard"
mysql -e "UPDATE mysql.user SET Password = PASSWORD('qwerty123456') WHERE User = 'root'"
mysql -e "DROP USER ''@'localhost'"
mysql -e "DROP USER ''@'$(hostname)'"
mysql -e "DROP DATABASE test"
mysql -e "FLUSH PRIVILEGES"

cd /vagrant
mvn spring-boot:run
