ps -ef | grep java | grep core-1.0.jar |  awk '{print "kill -15 ", $2}' > 1.sh
chmod 755 1.sh
./1.sh
rm -rf 1.sh