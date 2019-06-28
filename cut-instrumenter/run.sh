#!/bin/bash

if test $# -lt 2 ; then echo 'ARGS: PROJECT [dimeshift|splittypie|phoenix|pagekit|petclinic|retroboard], PO [apogen|manual]' ; exit 1 ; fi

cp="$HOME/.m2/repository/fr/inria/gforge/spoon/spoon-core/6.2.0/spoon-core-6.2.0.jar:$HOME/.m2/repository/org/eclipse/tycho/org.eclipse.jdt.core/3.13.50.v20171007-0855/org.eclipse.jdt.core-3.13.50.v20171007-0855.jar:$HOME/.m2/repository/com/martiansoftware/jsap/2.1/jsap-2.1.jar:$HOME/.m2/repository/log4j/log4j/1.2.17/log4j-1.2.17.jar:$HOME/.m2/repository/commons-io/commons-io/2.5/commons-io-2.5.jar:$HOME/.m2/repository/org/apache/maven/maven-model/3.3.9/maven-model-3.3.9.jar:$HOME/.m2/repository/org/codehaus/plexus/plexus-utils/3.0.22/plexus-utils-3.0.22.jar:$HOME/.m2/repository/org/apache/commons/commons-lang3/3.5/commons-lang3-3.5.jar:$HOME/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.9.2/jackson-databind-2.9.2.jar:$HOME/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.9.0/jackson-annotations-2.9.0.jar:$HOME/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.9.2/jackson-core-2.9.2.jar:$HOME/.m2/repository/org/jboss/forge/roaster/roaster-api/2.20.1.Final/roaster-api-2.20.1.Final.jar:$HOME/.m2/repository/org/jboss/forge/roaster/roaster-jdt/2.20.1.Final/roaster-jdt-2.20.1.Final.jar"

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

SUFFIX=
if [[ $PO == "apogen" ]]; then
    SUFFIX=Apogen
fi

PROPERTIES_FILE=$PWD/src/main/resources/app.properties
cat >$PROPERTIES_FILE <<EOL
sourceCodeCutToInstrumentPath=workspace/fse2019/${PROJECT}/src/main/java/main/ClassUnderTest$SUFFIX.java
formatCUT=false
instrumentCutForTestSuiteRun=false
instrumentCutForEvosuiteRun=false
EOL

java -cp $cp:target/classes main.Main

