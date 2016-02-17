#!/usr/bin/env bash
mkdir -p outfile/
wget -P outfile/ http://192.168.65.11/deploy_dependencies/commons/tomcat.tar.gz
tar -xzf outfile/tomcat.tar.gz -C outfile/
rm -rf outfile/tomcat.tar.gz
mkdir -p outfile/tomcat/webapps/ROOT
cp -ra trunk/scfOperation_web/target/scfOperation_web-0.0.1-SNAPSHOT/*  outfile/tomcat/webapps/ROOT
cp trunk/start.sh outfile/
cp trunk/appspec.yml outfile/
cp trunk/validate.sh outfile/

ls outfile/

cd outfile/
appid=hearthstone.testapp
tomcat_jmx_port=10826
tomcat_shutdown_port=8005
tomcat_bind_port=8080
tomcat_redirect_port=8443
tomcat_ajp_port=8009
tomcat_ajp_redirect_port=8443
echo $appid
echo $tomcat_jmx_port
echo $tomcat_shutdown_port
echo $tomcat_bind_port
echo $tomcat_redirect_port
echo $tomcat_ajp_port
echo $tomcat_ajp_redirect_port

sed -i 's/appid/'"$appid"'/g' tomcat/bin/setenv.sh.etpl
sed -i 's/jmxport/'"$tomcat_jmx_port"'/g' tomcat/bin/setenv.sh.etpl

sed -i 's/appid/'"$appid"'/g' tomcat/conf/logging.properties

sed -i 's/tomcatport/'"$tomcat_shutdown_port"'/g' tomcat/conf/server.xml
sed -i 's/bindport/'"$tomcat_bind_port"'/g' tomcat/conf/server.xml
sed -i 's/bindredirect/'"$tomct_redirect_port"'/g' tomcat/conf/server.xml
sed -i 's/ajpport/'"$tomcat_ajp_port"'/g' tomcat/conf/server.xml
sed -i 's/ajpredirect/'"$tomcat_ajp_redirect_port"'/g' tomcat/conf/server.xml
sed -i 's/appid/'"$appid"'/g' tomcat/conf/server.xml