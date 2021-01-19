#!/bin/bash
#clean tomcat log
sudo truncate -s 0 /var/log/tomcat8/catalina.out

#Define log file name
lastlogfilename="moveup.log."$(date -d '1 days ago' +%Y-%m-%d)
echo $lastlogfilename
#Define zip file name
lastlogzipfilename=$lastlogfilename".zip"
echo $lastlogzipfilename
#zip the log file
zip -q -r /var/log/$lastlogzipfilename /var/log/$lastlogfilename
#remove log file
rm -f /var/log/$lastlogfilename

#Define 7 days log zip file
lastlogzipfilename7days="/var/log/moveup.log."$(date -d '8 days ago' +%Y-%m-%d)".zip"
echo $lastlogzipfilename7days
#start if
if [ -f "$lastlogzipfilename7days" ]; then
echo "the file exists!"
#upload zip file to s3
#**** path (prod1) need edit ****
aws s3 cp $lastlogzipfilename7days s3://moveup-prod-log/prod3/

#get all file from s3
i=0
#**** path (prod1) need edit ****
for aname in $(aws s3 ls s3://moveup-prod-log/prod3/|awk '{print $4}')
do
    filelist[${i}]=$aname
    let i=${i}+1
    #echo $aname
done

#remove the file where 30 days ago
count=0
while(($i > 0))
do
    let i=${i}-1
    #echo ${filelist[${i}]}
    let count=${count}+1
    if [ "$count" -gt "30" ]; then
        #**** path (prod1) need edit ****
        aws s3 rm s3://moveup-prod-log/prod3/${filelist[${i}]}
    fi
done

#remove the local log zip file 7days ago
rm -f $lastlogzipfilename7days
#end if
fi
