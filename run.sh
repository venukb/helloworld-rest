export GC="-XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=compact -XX:+UseNUMA -XX:NewRatio=1 -XX:ConcGCThreads=8 -Xss1280k"
export GC_LOG_OPTS="-Xlog:gc*:gc.log:time,level,tags"
export DEBUGOPTS="-Xdebug -Xrunjdwp:transport=dt_socket,address=localhost:9999,server=y,suspend=n"
export HEAPOPTS="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp"

export XMS=512m
export XMX=512m
export FJ_POOL=512

#export LOG4J_CONFIG="-Dlog4j.configurationFile=log4j2.xml"
export ASYNCLOGOPTS="-Dlog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector -Dlog4j2.asyncLoggerRingBufferSize=1024*1024 -Dlog4j2.asyncLoggerThreadNameStrategy=UNCACHED -Dlog4j2.enableThreadlocals=true -Dlog4j2.enableDirectEncoders=true -Dlog4j2.disableJmx=true"

logFile="debug.log"

JAVA_JAR=app/target/helloworld.jar

java ${JAVA_OPTS} \
${GC} \
${GC_LOG_OPTS} \
${JAVA_YOURKIT_OPTS} \
-XMS${XMS} -Xmx${XMX} \
${HEAPOPTS} \
${LOG4J_CONFIG} \
${DEBUGOPTS} \
${ASYNCLOGOPTS} \
-Djava.util.concurrent.ForkJoinPool.common.parallelism=${FJ_POOL} \
-cp $JAVA_JAR com.venu.rest.Main

#"$@" > "$logFile" 2>&1 < /dev/null & echo $! > "$pidFile"
