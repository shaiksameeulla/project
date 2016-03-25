JAVA_HOME=/usr/java/jdk1.6.0_25
CLASSPATH=$1:$CLASSPATH$
PATH=$PATH$:/usr/java/jdk1.6.0_25
java -Djava.ext.dirs=lib -cp "./lib/ctbs-framework.jar;." src.com.capgemini.lbs.framework.queue.utils.QueueConsumerUtil
