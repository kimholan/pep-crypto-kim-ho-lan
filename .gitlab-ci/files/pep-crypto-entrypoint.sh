#!/bin/bash
#-----------------------------------------------------------------------------------------------------------------------
# Skip options unless -- was provided as first argument
#-----------------------------------------------------------------------------------------------------------------------
if [[ $# -gt 0 ]] && [[ "$1" != "--"* ]]; then
    exec "$@"
fi

echo "[COMMAND]" ${@}

################################################################################
# Thorntail
################################################################################

NODE_NAME=$(hostname -i | md5sum | cut -f 1 -d' ')

JAR_FILE=${JAR_FILE:-pep-crypto-thorntail-@@NEXT-VERSION@@-thorntail.jar}
JAVA_HOME=${JAVA_HOME:-/usr/java/default}
JAVA_OPTS=${JAVA_OPTS:--XX:+UseParallelGC -XX:+UseParallelOldGC -XX:-TieredCompilation -XX:ActiveProcessorCount=2 -Xms128M -Xmx384M -Djava.net.preferIPv4Stack=true -Dresteasy.preferJacksonOverJsonB -Djboss.node.name=${NODE_NAME}}
JAR_OPTS=${JAR_OPTS:-}
PATH=${JAVA_HOME}/bin:${PATH}


if [[ " $@ " == *" jacoco "* ]];
then
    export JAVA_TOOL_OPTIONS="-javaagent:/jacocoagent.jar=includes=nl.logius.pepcrypto.*,output=tcpserver,address=*"
fi


if  [[ " $@ " == *" thorntail-max-post-size "* ]];
then
    exec ${JAVA_HOME}/bin/java ${JAVA_OPTS} -jar ${JAR_FILE} ${JAR_OPTS} -s /thorntail-max-post-size.yml
fi


if [[  $# -eq 0 ]] || [[ " $@ " == *" thorntail "* ]];
then
    JAR_OPTS+=" -s /thorntail-defaults.yml "

    exec ${JAVA_HOME}/bin/java ${JAVA_OPTS} -jar ${JAR_FILE} ${JAR_OPTS} -s /thorntail-defaults.yml
fi

if [[ " $@ " == *" thorntail-no-defaults "* ]];
then
    exec ${JAVA_HOME}/bin/java ${JAVA_OPTS} -jar ${JAR_FILE} ${JAR_OPTS}
fi

