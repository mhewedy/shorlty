# !/bin/bash

if [ $# -ne 3 ]
then
	echo "usage $0 <no_records> <host> <log_file>"
	exit 1
fi

for i in `seq 1 $1`; do curl http://$2/api/shorten?url=http://google.com >> $3; echo -e "" >> $3; done;
