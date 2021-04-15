About
=======

These are notes covering the most important aspects of Spark ecosystem, including architecture, best practice and limitations

What is it?
===========

*  [Spark](https://spark.apache.org/) is an analytics distributed system; one can use it to process workloads and algorithms in a distributed way. And it's not only good for analytics: once can use Spark for data transfer, massive data transformation, log analysis, and more.
*  Spark supports SQL (natively SQL-92), Java, Scala, R, and Python (through PySpark) as a programming interface. The core is written in Scala.
*  Spark's internal main data storage is the dataframe. The dataframe combines storage capacity with an API. The most close analog of dataframce could be data structure in BigColumn databases. From the API point of view due to its proximity to Java JDBC development, one can find similarities with a JDBC ResultSet.
*  In Java, a dataframe is implemented as a Dataset<Row>.
*  One can quickly set up Spark with Maven and Eclipse. Spark does not need to be installed.
*  Spark is not limited to the MapReduce algorithm: its API allows a lot of algorithms to be applied to data.
*  Streaming is used more and more frequently in enterprises, as businesses want access to real-time analytics. Spark supports streaming.
*  Analytics have evolved from simple joins and aggregations. Enterprises want computers to think for us; hence Spark supports machine learning and deep learning.
*  Graphs are a special use case of analytics supported by Spark.

Basics
=======
*  [Spark quick start](https://spark.apache.org/docs/latest/rdd-programming-guide.html)
*  [Alternative Spark tutorial](https://www.datacamp.com/community/tutorials/apache-spark-tutorial-machine-learning)
*  [15-min Spark tutorial](https://www.edureka.co/blog/spark-tutorial/)
*  [Video tutorial](https://www.youtube.com/watch?v=QaoJNXW6SQo)
*  The initial application built on the basis of Spark framework is called the driver. Data may not have to come to the driver; it can be driven remotely.
*  The driver connects to a master and gets a session. Data will be attached to this session; the session defines the life cycle of the data on the worker's nodes.
*  The master can be local (on local machine) or a remote cluster. Using the local mode will not require you to build a cluster, making life much easier during development phase.
*  Data is partitioned and processed within the partition. Partitions are in memory.
*  Spark can easily read from CSV files read/write data from/to relational databases, other sources could be JSON files, binary files, etc.
*  Spark is lazy: it will work only when you ask it to do so via an action.
*  Spark's APIs rely heavily on method chaining due to its functional fundamentals (Spark is written in functional language, Scala)

Dataframe
==========

*  A dataframe is an immutable distributed collection of data, organized into named columns. Dataframe is an RDD with a schema.
*  A dataframe is implemented as a dataset of rows or in code: Dataset<Row>.
*  A dataset is implemented as a dataset of anything except rows or in code: Dataset<String>, Dataset<Book>, or Dataset<Pojo>.
*  Dataframes can store columnar information, like a CSV file, and nested fields and arrays, like a JSON file. Regardless of input - CSV files, JSON files, or other formats, - the dataframe API remains the same.
*  In a JSON document, you can access nested fields by using a dot (.).
*  [The API for the dataframe ](https://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/Dataset.html).
*  [The API for the static methods ](https://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/functions.html)
*  If you do not care about column names when you union two dataframes, use union(), overwise use unionByName().
*  One can reuse POJOs directly in a dataset in Spark.
*  An object must be serializable if you want to have it as part of a dataset.
*  The dataset's drop() method removes a column in the dataframe.
*  The dataset's col() method returns a dataset's column based on its name.
*  The to_date() static function transforms a date as a string to a date.
*  The expr() static function will compute the result of expressions by using field names.
*  The lit() static function returns a column with a literal value.
*  A resilient distributed dataset (RDD) is an immutable distributed collection of elements of your data.
*  When performance is critical use a dataframe over an RDD. 
*  Tungsten storage relies on dataframes (https://databricks.com/blog/2015/04/28/project-tungsten-bringing-spark-closer-to-bare-metal.html). 
*  Catalyst is the transformation optimizer. It relies on dataframes to optimize actions and transformations.
*  APIs across Spark libraries (graph, SQL, machine learning, or streaming) are becoming unified under the dataframe API.

Catalyst
=========

Spark is efficiently lazy: it will build the list of transformations as a directed acyclic graph (DAG), which it will optimize using Catalyst, Spark's built-in optimizer.

*  The first article on Catalyst: [Spark SQL: Relational Data Processing in Spark ](http://people.csail.mit.edu/matei/papers/2015/sigmod_spark_sql.pdf)
*  [Understanding your Apache Spark Application Through Visualization ](https://databricks.com/blog/2015/06/22/understanding-your-spark-application-through-visualization.html)
*  When you apply a transformation on a dataframe, the data is not modified.
*  When you apply an action on a dataframe, all the transformations are executed, and, if it needs to be, the data will be modified.
*  Modification of the schema is a natural operation within Spark. One can create columns as placeholders and perform operations on them.
*  Spark works at the column level; there is no need to iterate over the data.
*  Transformations can be done using the built-in functions, lower-level functions, dataframe methods, and UDFs
*  One can print the query plan by using the dataframe's explain() method (which is mostly used for debugging)

Driver
========

*  Spark can work without ingesting data; one can generate data using standard Producer pattern.
*  Spark supports three execution modes: local mode, cluster mode, and interactive mode.
*  Local mode allows developers to get started on Spark development in minutes.
*  Cluster mode is used for production.
*  You can submit a job to Spark or connect to the master.
*  The driver application is where your main() method is.
*  The master node knows about all the workers.
*  The execution takes place on the workers.
*  Sparks handles the distribution of your application JAR in cluster mode, to each worker node.
*  MapReduce is a common method to work on big data in distributed systems. On of its implementation is Hadoop, the Spark is the second one.
*  Continuous integration and continuous delivery (CICD) is an agile methodology that encourages frequent integration and delivery.
*  Lambda functions, introduced in Java 8, allow you to have functions outside the scope of a class.
*  An uber JAR contains all the classes (including the dependencies) of an application in a single file.
*  Maven can build an uber JAR automatically.
*  Maven can deploy your source code at the same time it deploys your JAR file.
*  Spark's map and reduce operations can use classes or lambda functions.
*  Spark provides a web interface to analyze the execution of jobs and applications.
*  Interactive mode allows you to type Scala, Python, or R commands directly in a shell. Interaction can also be achieved with the help of notebooks such as Jupyter or Zeppelin. 

Ingestion
==========

# Ingestion from files

Spark allows to ingest data from files. Alone with well-known file formats the specialized ones are supported such as:

[Apache Avro](https://github.com/databricks/spark-avro) - was designed for remote procedure calls (RPCs) in a similar way as Protocol Buffers (Protobuf), a popular method for transferring serializable data developed and open sourced by Google; learn more at https://developers.google.com/protocol-buffers/.

[Apache Optimized Row Columnar (ORC)](https://orc.apache.org/) - a columnar file format

[Apache Parquet](https://parquet.apache.org/) - a columnar file format

[Comaprison of Avro, ORC, and Parquet](https://www.datanami.com/2018/05/16/big-data-file-formats-demystified/)

*  Ingestion is a key part of your big data pipeline.
*  When ingesting files, you can use a regular expression to specify the path.
*  CSV is more complex than it looks, but a rich set of options allows you to tune the parser.
*  JSON exists in two forms: the one-line form called JSON Lines, and multiline JSON. Spark ingests both forms of JSON (since v2.2.0).
*  All options for file ingestion that are described in this chapter are not casesensitive.
*  Spark can ingest CSV, JSON, and text out of the box.
*  To ingest XML, Spark needs a plugin provided by Databricks.
*  The pattern for ingesting any document is fairly similar: you specify the format and simply read.
*  Traditional file formats, including CSV, JSON, and XML, are not well suited for big data.
*  JSON and XML are not considered splittable file formats.
*  Avro, ORC, and Parquet are popular big data file formats. They embed the data and the schema in a file and compress the data. They are easier and more efficient to manipulate than CSV, JSON, and XML when it comes to big data.
*  ORC and Parquet files are in a columnar format which are more suitable for real-time schema changing, etc.
*  Avro is row-based, which is more suitable for streaming.
*  ORC and Parquet's compression is better than Avro's

# Ingestion from databases

Spark supports a few database dialects:

*  IBM Db2
*  Apache Derby
*  MySQL
*  Microsoft SQL Server
*  Oracle
*  PostgreSQL
*  Teradata Database

Additionally one can implement translator for custom dialect

*  Spark uses a standard connectivity to connect to DD (JDBC drivers). One can use properties or long URLs to connect to the database
*  One can build a dedicated dialect to connect to data sources that are not available, and it's not very difficult.
*  One can filter the ingested data by using the (select statement) table alias syntax instead of the table name.
*  One can perform joins both at the database level, prior to ingesting in Spark, and in Spark.
*  One can automatically assign data from the database in multiple partitions.
*  Ingesting data from Elasticsearch follows the same principle as any other ingestion, connectivity is standard as well (through ES RESTful API). Technically very similar to JSON ingestion

# Ingestion from Data Sources

*  [List of extensions to Spark](https://spark-packages.org/)
*  Direct connection to a data source from Spark offers these benefits: 
(1) temporary files are not needed, (2) data quality/cleansing scripts can be written directly in Spark, and consequently there is no need for JSON/CSV conversion.
*  EXIF is an extension to JPEG (and other graphic formats) to store metadata about the image.
*  A JavaBean is a small class that contains properties and accessor methods.
*  When you develop a new data source, the ingestion code remains similar to generic data sources you've used with files or databases.
*  A data source needs to deliver a schema of the data that is as long as the data.
*  A data source can be identified by a short name (defined in a resource file and an advertiser class) or by a full class name.
*  A schema in Spark is implemented using the StructType object. It contains one or more StructField instances.
*  Data structures and collections may be different between Java and Scala, but conversion methods exist.
*  The RowFactory class offers a method to convert a static array to a Spark Row.

# Ingestion from Structured Streaming

*  [Spark Streaming Programming Guide](https://spark.apache.org/docs/latest/streaming-programming-guide.html)
*  [Structured Streaming Programming Guide](https://spark.apache.org/docs/latest/structured-streaming-programming-guide.html)
*  Starting a Spark session is the same whether you are in batch or streaming mode.
*  Streaming is a different paradigm from batch processing. One way to see streaming, especially in Spark, is as microbatching.
*  You define a stream on a dataframe by using the readStream() method followed by the start() method.
*  You can specify ingestion format by using format() method, and options by using option() method.
*  The streaming query object, StreamingQuery, will help you query the stream.
*  The destination of the data in the query is called a sink. It can be specified by using the format() method or the forEach() method.
*  The streamed data is stored in a result table.
*  To wait for the data to come, you can use awaitTermination(), which is a blocking method, or check whether the query is active by using the isActive() method.
*  You can read from multiple streams at the same time.
*  The forEach() method will require a custom writer that lets you process records in a distributed way.
*  Streaming as it was done in Spark v1.x is called discretized streaming (outdated now).

Spark SQL
==========

*  [Apache Spark SQL](https://spark.apache.org/docs/latest/sql-programming-guide.html) is based on [Apache HiveQL](https://cwiki.apache.org/confluence/display/Hive/LanguageManual) which in its turn is based on SQL-92 
*  Spark supports Structured Query Language (SQL) as a query language to interrogate data.
*  One can mix API and SQL in your application.
*  Data is manipulated through views on top of a dataframe.
*  Views can be local to the session, or global/shared among sessions in the same application. Views are never shared between applications.
*  Because data is immutable, you cannot drop or modify records; you will have to re-create a new dataset.
*  To drop records from the dataframe, however, you can build a new dataframe based on a filtered dataframe (basically delete operations is never occur in the sense that nothing actually got deleted)

Data transformation
=====================

*  Data discovery looks at both the data and its structure.
*  Data mapping builds a map of the data between the origin and the destination of the transformation.
*  To help with data discovery and mapping, a definition of the origin data and structure should be available.
*  Static functions are key to data transformation.
*  The dataframe's cache() method allows caching and can help improve performance.
*  expr() is a convenient function that allows you to compute an SQL-like statement when you are transforming the data.
*  A dataframe can contain arrays of values.
*  Dataframes can be joined together, like tables in a relational database.
*  Spark supports the following kinds of joins: inner, outer, left, right, left-semi, left-anti, and cross (super-set, or Cartesian).
*  When manipulated in dataframes, arrays follow the SQL standard and start their indexing at 1.
*  You can extract a sample of the data from a dataframe by using sample().
*  The sample() method supports the replacement concept from statistics.
*  Data can be sorted in a dataframe by using asc(), asc_nulls_first(), asc_nulls_last(), desc(), desc_nulls_first(), and desc_nulls_last().
*  Spark can join dataframes, two at a time, and supports inner, outer, left, right, left-semi, left-anti, and cross joins.

Structure transformation
==========================

*  Apache Spark is a great tool for building data pipelines using transformations.
*  Spark can be used to flatten JSON documents.
*  One can build nested documents between two (or more) dataframes.
*  Static functions are key to data transformation.

User-defined functions (UDF)
=============================

*  One can extend the Spark function set by using UDFs.
*  Spark executes UDFs on the worker node.
*  To use a UDF is to register your UDF with a unique name, an instance of the class implementing the UDF, and a return type.
*  A UDF can have from 0 to 22 parameters.
*  You can call a UDF via the callUDF() method of the dataframe API or directly in Spark SQL.
*  You can implement a UDF via a Java class that implements an interface, based on the number of parameters, from UDF0 to UDF22, from the org.apache.spark.sql.api.java package.
*  A good practice is to avoid any business logic code in the UDF itself, leaving the implementation in a service: be a good plumber by shielding the service code and leaving the interface between the service and Spark code in the UDF.
*  UDFs are a good way to implement data quality in Spark, because by using a UDF, you avoid manipulating files, use distributed computing, and potentially reuse existing libraries and tools.
*  A UDF implementation must be serializable.
*  A UDF is a black box for Catalyst, Spark's optimizer.
*  UDFs do not support polymorphism.

Data aggregation
=================

*  Aggregations are a way to group data so you can look at the data from a higher, or macro, level.
*  Apache Spark can perform aggregations on dataframes with Spark SQL (by creating a view) or the dataframe API.
*  The groupBy() method is the equivalent of the SQL GROUP BY statement.
*  Data needs to be prepared and cleaned before performing aggregations. Those steps can be done via transformations (chapter 12).
*  Aggregations can be performed by methods chained after the groupBy() method, or by static functions inside the agg() method.
*  Spark's aggregations can be extended by custom user-defined aggregation functions (UDAFs).
*  A UDAF must be registered by name in your Spark session.
*  A UDAF is called using the callUDF() method and the UDAF name.
*  A UDAF is implemented as a class, which should implement several methods.
*  Use the agg() method to perform aggregations on multiple columns at once.
*  You can use the sum() method and static function to calculate a sum of a set.
*  You can use the avg() method and static function to calculate an average of a set.
*  You can use the max() method and static function to extract the maximum value of a set.
*  You can use the min() method and static function to extract the minimum value of a set.
*  Other aggregation functions include many statistical methods, such as these: 
approx_count_distinct(), 
collect_list(), 
collect_set(), 
corr(), 
count(),
countDistinct(), 
covar_pop(), 
covar_samp(), 
first(), 
grouping(), 
grouping
_id(), 
kurtosis(), 
last(), 
mean(), 
skewness(), 
stddev(), 
stddev_pop(),
stddev_samp(), 
sumDistinct(), 
var_pop(), 
var_samp(), 
variance().

Cache and Checkpoints in Spark
===============================

*  [Using caching in Spark data processing (reference)](https://spark.apache.org/docs/latest/rdd-programming-guide.html#rdd-persistence)
*  [Understanding of Spark Memory Management Model](https://www.tutorialdocs.com/article/spark-memory-management.html
*  [Spark documentation on tuning](https://spark.apache.org/docs/latest/tuning.html)
*  [The Internals of Apache Spark](https://books.japila.pl/apache-spark-internals/overview/), with git source at https://github.com/japila-books/apache-spark-internals
*  As one way to increase performance, Spark offers (1) caching, (2a) eagerly checkpoints, and  (2b) non-eagerly (or lazy) checkpointing.
*  Caching keeps the lineage data. You can trigger the cache by using cache() or persist(). Caching offers various levels of storage combining memory and disk.
*  Checkpointing does not keep the lineage and saves the content of the dataframe to disk.
*  Lack of heap memory can become an issue with large datasets; Spark can use the off-heap/permgen space.
*  Caching can use a combination of memory and disk.
*  Checkpointing helps increase performance by saving the data contained in the dataframe to disk. An eager checkpoint performs the operation right away, while a non-eager or lazy operation will wait for an action.
*  A checkpoint directory has to be set in the SparkContext, which you can get from the SparkSession. There is no default value for the checkpoint directory.
*  Performance depends on many factors; there is no one-size-fits-all solution.



Data pipelines
===============

*  [Delta Lake](https://delta.io/)
*  [Apache Spark Cloud Integrations](https://spark.apache.org/docs/latest/cloud-integration.html)
*  [Hadoop-AWS module: Integration with AWS](https://hadoop.apache.org/docs/current/hadoop-aws/tools/hadoop-aws/index.html)
*  Just as Apache Spark can ingest data from multiple formats, it can export data in multiple formats.
*  The key method for writing data from a dataframe to a file or database is write().
*  The write() method behaves similarly to the read() method: you can specify the format() and several options via option().
*  The save() method, which is linked to the dataframe's write() method, is a counterpart to the read() method, which is linked to the dataframe's load() method.
*  One can specify the write mode with the mode() function.
*  Exporting data will export data from each partition, potentially resulting in several files being created
*  Delta Lake is a database that lives in the Spark environment. You can persist your dataframes in Delta Lake (aka Data Lake)
*  You can use the coalesce() method or the repartition() method to reduce the number of partitions.
*  Apache Spark can access data stored in cloud providers including Amazon S3, Google Cloud Storage, IBM Cloud Object Storage, Microsoft Azure Blob Storage, and OVH Object Storage by using the S3 and OpenStack Swift APIs.
*  Spark's static functions offer several ways to manipulate the date and time.
*  You can download a file using Java nio classes and ingest it in Spark


Cluster Deployment
===================

*  The role of the underlying operating system is to manage resources locally. However, when dealing with a cluster, there is need a cluster manager or a resource manager.
*  Spark comes with its own resource manager called standalone mode, allowing to build a cluster without depending on other software components.
*  YARN, a resource manager inherited from the Hadoop world, remains a popular choice, especially in hosted cloud environments.
*  Mesos and Kubernetes are standalone cluster managers, freeing you from the Hadoop dependencies.
*  Support for Kubernetes in Spark has been added in version 2.3 and is constantly improving.
*  All cluster managers support high availability.
*  When dealing with files, all Spark's workers need to have access to the same file or copy of the file.
*  HDFS is one option for large files. Files can be distributed over the cluster by using HDFS, part of Hadoop.
*  Smaller files can be shared via a file server or a file-sharing service such as Box or Dropbox.
*  Object stores like Amazon S3 or IBM COS can also be used to store large files.
*  By default, security is not activated in Spark.
*  For network communication, you can fine-tune the security per component. Most components accept both specific authentication and encryption on the wire.
*  Temporary storage from Spark can be encrypted using the spark.io.encryption.* set of configuration entries.


