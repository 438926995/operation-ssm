#!/bin/sh
 
APP_ID="me.ele.scf.scfoperation"
APP_HOME=`pwd`
CATALINA_HOME="${APP_HOME}/tomcat"
 
echo "appid ${APP_ID}"
echo "apphome ${APP_HOME}"
echo "catalina home: ${CATALINA_HOME}"

if [[ "$JAVA_OPTS" != *-Djava.security.egd=* ]]; then
    export JAVA_OPTS="$JAVA_OPTS -Djava.security.egd=file:/dev/./urandom"
fi
 
${CATALINA_HOME}/bin/catalina.sh run