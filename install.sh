#!/bin/bash

# SudokuFeud home (default: /opt/sudokufeud)
SUDOKUFEUD_HOME=/opt/sudokufeud
if [ $1 ]
then
	SUDOKUFEUD_HOME=$1
fi
if [ ! -e ${SUDOKUFEUD_HOME} ]
then
	echo "SudokuFeud home doesn't exist: $SUDOKUFEUD_HOME"
	exit -1
fi

# convert and read git properties from Java properties file
cat src/main/resources/git.properties | awk -f readproperties.awk > git_properties.sh
. git_properties.sh
rm git_properties.sh

APP_HOME="$SUDOKUFEUD_HOME/$git_commit_id_describe"
if [ -e "$APP_HOME" ]
then
	rm -rf "$APP_HOME"
fi

mkdir "$APP_HOME"
mkdir "$APP_HOME"/logs
cp -r config "$APP_HOME"/
cp target/sudokufeud-*.jar "$APP_HOME"/
cp run.sh "$APP_HOME"/
cp run-aws.sh "$APP_HOME"/
cp shutdown.sh "$APP_HOME"/

LATEST_APP_HOME="$SUDOKUFEUD_HOME"/latest
if [ -e "$SUDOKUFEUD_HOME"/latest ]
then
	rm "$LATEST_APP_HOME"
fi
ln -s "$APP_HOME" "$LATEST_APP_HOME"

# echo "commit id is $git_commit_id_describe"

