set JAVA_HOME=C:\Program Files\Java\jdk1.6.0_21
SET CLASSPATH=%1;%CLASSPATH%
set PATH=%PATH%;%JAVA_HOME%/bin
java -Djava.ext.dirs=lib -cp "./lib/ctbs-asynch-queue-consumer.jar;." src.com.capgemini.lbs.mdblistener.FranchiseeDbSyncConsumer