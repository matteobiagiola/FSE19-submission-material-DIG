#!/bin/bash

if test $# -lt 2 ; then echo 'ARGS: PROJECT [dimeshift|splittypie|phoenix|pagekit|petclinic|retroboard], PO [apogen|manual]' ; exit 1 ; fi

cp="$HOME/.m2/repository/fr/inria/gforge/spoon/spoon-core/6.0.0/spoon-core-6.0.0.jar:$HOME/.m2/repository/org/eclipse/tycho/org.eclipse.jdt.core/3.13.50.v20171007-0855/org.eclipse.jdt.core-3.13.50.v20171007-0855.jar:$HOME/.m2/repository/com/martiansoftware/jsap/2.1/jsap-2.1.jar:$HOME/.m2/repository/log4j/log4j/1.2.17/log4j-1.2.17.jar:$HOME/.m2/repository/commons-io/commons-io/2.5/commons-io-2.5.jar:$HOME/.m2/repository/org/apache/maven/maven-model/3.5.0/maven-model-3.5.0.jar:$HOME/.m2/repository/org/codehaus/plexus/plexus-utils/3.0.24/plexus-utils-3.0.24.jar:$HOME/.m2/repository/org/apache/commons/commons-lang3/3.5/commons-lang3-3.5.jar:$HOME/.m2/repository/org/apache/logging/log4j/log4j-api/2.10.0/log4j-api-2.10.0.jar:$HOME/.m2/repository/org/apache/logging/log4j/log4j-core/2.10.0/log4j-core-2.10.0.jar:$HOME/.m2/repository/org/jgrapht/jgrapht-core/1.1.0/jgrapht-core-1.1.0.jar:$HOME/.m2/repository/org/jgrapht/jgrapht-ext/1.1.0/jgrapht-ext-1.1.0.jar:$HOME/.m2/repository/org/jgrapht/jgrapht-io/1.1.0/jgrapht-io-1.1.0.jar:$HOME/.m2/repository/org/antlr/antlr4-runtime/4.6/antlr4-runtime-4.6.jar:$HOME/.m2/repository/org/tinyjee/jgraphx/jgraphx/2.0.0.1/jgraphx-2.0.0.1.jar:$HOME/.m2/repository/jgraph/jgraph/5.13.0.0/jgraph-5.13.0.0.jar:$HOME/.m2/repository/com/google/guava/guava/19.0/guava-19.0.jar:$HOME/.m2/repository/com/mitchellbosecke/pebble/2.4.0/pebble-2.4.0.jar:$HOME/.m2/repository/com/coverity/security/coverity-escapers/1.1/coverity-escapers-1.1.jar:$HOME/.m2/repository/org/slf4j/slf4j-api/1.7.21/slf4j-api-1.7.21.jar:$HOME/.m2/repository/org/jboss/forge/roaster/roaster-api/2.20.1.Final/roaster-api-2.20.1.Final.jar:$HOME/.m2/repository/org/jboss/forge/roaster/roaster-jdt/2.20.1.Final/roaster-jdt-2.20.1.Final.jar"

PROJECT=$1
PO=$2

if [[  $PROJECT != "dimeshift" \
    && $PROJECT != "splittypie" \
    && $PROJECT != "phoenix" && $PROJECT != "pagekit" \
    && $PROJECT != "petclinic" && $PROJECT != "retroboard" ]]; then
    echo "Unknown project: $PROJECT"
    exit 1
fi

if [[  $PO != "apogen" \
    && $PO != "manual" ]]; then
    echo "Unknown PO: $PO"
    exit 1
fi

PROPERTIES_FILE=$PWD/config.properties
cp "config/$PO/$PROJECT.config.properties" $PROPERTIES_FILE

java -cp $cp:target/classes main.Main load_properties