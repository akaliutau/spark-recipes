## About
This repository contains recipes tied with Apache Spark data processing framework

## Building and Running the code

Prerequisites:

You will need:
 * `git`
 * Apache Spark 3.0.0+  

1. Clone this project

```
git clone https://github.com/akaliutau/spark-recipes
```

All code are put into namespace net.ddp which stands for distributed data processing

## Installation notes

On Windows:

Set environment variables:

HADOOP_HOME -> C:\spark - this directory must contain winutils.exe binary.

SPARK_HOME -> C:\spark - spark installation directory

SPARK_LOCAL_DIRS -> C:\spark\tmp - temporary directory which will be used by Spark to hold jar files to run. This directory is not cleaned up by Spark, see the bug description at

https://stackoverflow.com/questions/41825871/exception-while-deleting-spark-temp-dir-in-windows-7-64-bit

Due to this issue it will be necessary to update Spark configuration:

Add to /conf/log4j.properties the following lines:

log4j.logger.org.apache.hadoop.util.ShutdownHookManager=OFF

log4j.logger.org.apache.spark.util.ShutdownHookManager=OFF

log4j.logger.org.apache.spark.SparkEnv=ERROR







