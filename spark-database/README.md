## About
This module contains code written to demonstrate how to use databases along with Spark framework.

## Building and Running the code

Prerequisites:

You will need:
 * `git`
 * Apache Spark 
 * Docker
 
  

1 Clone this project

```
git clone https://github.com/akalu/spark-recipes
```

2 cd spark-database

3 Package application using maven command

```
mvn clean package
```

4 Run docker daemon

5 Run docker containers

```
docker-compose up --build
```


6 Run Spark/Scala application using spark-submit command as shown below:

```
spark-submit --jars ..\lib\postgresql-42.1.4.jar --class net.ddp.database.CsvToRelationalDatabaseApp .\target\spark-database-1.0-SNAPSHOT.jar

```
A new table books will be created in database spark_db containing 5 records with authors names


## Notes
