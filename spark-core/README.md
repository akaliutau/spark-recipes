## About
This module contains code written to demonstrate rudimentary functionality of Spark framework.
Usually it does not require extra functionality to run, such as external databases, etc

## Building and Running the code

Prerequisites:

You will need:
 * `git`
 * Apache Spark  

1. Clone this project

```
git clone https://github.com/akalu/spark-recipes
```

2. cd spark-core

3. Package application using maven command

```
mvn clean package
```

4. Run Spark/Scala application using spark-submit command as shown below:

```
spark-submit --class net.ddp.transformation.CsvToDataframeApp .\target\spark-core-1.0-SNAPSHOT.jar
```
(choose appropriate class name as entry point)


## Notes
