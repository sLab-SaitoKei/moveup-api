# nohup ./tail-slack.sh "/var/log/moveup.log" "https://hooks.slack.com/services/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" "ERROR" &

# tailing ERROR|Error|error|Cause:

#!/bin/bash

tail -n0 -F "$1" | while read LINE; do
  (echo "$LINE" | grep -e "$3") && curl -X POST --silent --data-urlencode \
    "payload={\"text\": \"$(echo $LINE | sed "s/\"/'/g")\"}" "$2";
done