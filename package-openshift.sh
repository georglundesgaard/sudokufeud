#!/bin/bash


# convert and read git properties from Java properties file
cat src/main/resources/git.properties | awk -f readproperties.awk > git_properties.sh
. git_properties.sh
rm git_properties.sh

SAVED_PWD=`pwd`
SF_DIR=sudokufeud-${git_commit_id_describe}
SF_PACKAGE=${SF_DIR}.tar.gz

if [ -e ${SF_PACKAGE} ]
then
	rm ${SF_PACKAGE}
fi

cd /tmp
if [ -e ${SF_DIR} ] 
then
	rm -rf ${SF_DIR}
fi

if [ -e ${SF_PACKAGE} ]
then
	rm ${SF_PACKAGE}
fi

mkdir ${SF_DIR}
cp -a ${SAVED_PWD}/config ${SF_DIR}/
cp -a ${SAVED_PWD}/target/sudokufeud-*.jar ${SF_DIR}/
cp -a ${SAVED_PWD}/run-openshift.sh ${SF_DIR}/

tar c ${SF_DIR} | gzip - > ${SF_PACKAGE}

rm -rf ${SF_DIR}
mv ${SF_PACKAGE} ${SAVED_PWD}/ 

cd ${SAVED_PWD}
