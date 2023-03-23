#!/bin/sh


JAVA_OPTS="-Xmx512M -Xms256M"

PRG_LIB=./libs

for i in `ls $PRG_LIB`; do JAVA_CLASSPATH=$JAVA_CLASSPATH:$PRG_LIB/$i; done
# echo $JAVA_CLASSPATH

MAIN_CLASS=fr.ird.akado.ui.swing.AkadoAvdthUI

java $JAVA_OPTS -classpath $JAVA_CLASSPATH $MAIN_CLASS -D-Dfile.encoding=UTF-8
