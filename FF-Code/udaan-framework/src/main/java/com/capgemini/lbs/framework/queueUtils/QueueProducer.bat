set JAVA_HOME=C:\Program Files\Java\jdk1.7.0
SET CLASSPATH=%1;%CLASSPATH%;D:\First_Flight\SVN_CODE\Udaan_Main_Trunk\udaan-library\udaan-jars\;D:\First_Flight\SVN_CODE\Udaan_Main_Trunk\udaan-library\queue-lib;;
set PATH=%PATH%;%JAVA_HOME%/bin
java -Djava.ext.dirs=lib -cp "./ctbs-framework.jar;." com.capgemini.lbs.framework.queue.utils.QueueProducerUtil
