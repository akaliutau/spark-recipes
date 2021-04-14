About
=======

These are notes covering the most important aspects of Spark ecosystem, including architecture, best practice and limitations

What is it?
===========

*  Spark is an analytics distributed system; one can use it to process workloads and algorithms in a distributed way. And it’s not only good for analytics: once can use Spark for data transfer, massive data transformation, log analysis, and more.
*  Spark supports SQL (natively SQL-92), Java, Scala, R, and Python (through PySpark) as a programming interface. The core is written in Scala.
*  Spark’s internal main data storage is the dataframe. The dataframe combines storage capacity with an API. The most close analog of dataframce could be data structure in BigColumn databases. From the API point of view due to its proximity to Java JDBC development, one can find similarities with a JDBC ResultSet.
*  In Java, a dataframe is implemented as a Dataset<Row>.
*  One can quickly set up Spark with Maven and Eclipse. Spark does not need to be installed.
*  Spark is not limited to the MapReduce algorithm: its API allows a lot of algorithms to be applied to data.
*  Streaming is used more and more frequently in enterprises, as businesses want access to real-time analytics. Spark supports streaming.
*  Analytics have evolved from simple joins and aggregations. Enterprises want computers to think for us; hence Spark supports machine learning and deep learning.
*  Graphs are a special use case of analytics supported by Spark.

Basics
=======

*  The initial application built on the basis of Spark framework is the driver. Data may not have to come to the driver; it can be driven remotely.
*  The driver connects to a master and gets a session. Data will be attached to this session; the session defines the life cycle of the data on the worker’s nodes.
*  The master can be local (on local machine) or a remote cluster. Using the local mode will not require you to build a cluster, making life much easier during development phase.
*  Data is partitioned and processed within the partition. Partitions are in memory.
*  Spark can easily read from CSV files read/write data from/to relational databases, other sources could be JSON files, binary files, etc.
*  Spark is lazy: it will work only when you ask it to do so via an action.
*  Spark’s APIs rely heavily on method chaining due to its functional fundamentals (Spark is written in functional language, Scala)

Dataframe
==========

*  A dataframe is an immutable distributed collection of data, organized into named columns. Dataframe is an RDD with a schema.
*  A dataframe is implemented as a dataset of rows—or in code: Dataset<Row>.
*  A dataset is implemented as a dataset of anything except rows—or in code: Dataset<String>, Dataset<Book>, or Dataset<Pojo>.
*  Dataframes can store columnar information, like a CSV file, and nested fields and arrays, like a JSON file. Regardless of input - CSV files, JSON files, or other formats, - the dataframe API remains the same.
*  In a JSON document, you can access nested fields by using a dot (.).
*  [The API for the dataframe ](https://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/Dataset.html).
*  [The API for the static methods ](https://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/functions.html)
*  If you do not care about column names when you union two dataframes, use union(), overwise use unionByName().
*  One can reuse POJOs directly in a dataset in Spark.
*  An object must be serializable if you want to have it as part of a dataset.
*  The dataset’s drop() method removes a column in the dataframe.
*  The dataset’s col() method returns a dataset’s column based on its name.
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

Spark is efficiently lazy: it will build the list of transformations as a directed acyclic graph (DAG), which it will optimize using Catalyst, Spark’s built-in optimizer.

*  The first article on Catalyst: [Spark SQL: Relational Data Processing in Spark ](http://people.csail.mit.edu/matei/papers/2015/sigmod_spark_sql.pdf)
*  [Understanding your Apache Spark Application Through Visualization ](https://databricks.com/blog/2015/06/22/understanding-your-spark-application-through-visualization.html)
*  When you apply a transformation on a dataframe, the data is not modified.
*  When you apply an action on a dataframe, all the transformations are executed, and, if it needs to be, the data will be modified.
*  Modification of the schema is a natural operation within Spark. One can create columns as placeholders and perform operations on them.
*  Spark works at the column level; there is no need to iterate over the data.
*  Transformations can be done using the built-in functions, lower-level functions, dataframe methods, and UDFs
*  One can print the query plan by using the dataframe’s explain() method (which is mostly used for debugging)

Driver
========

*  Spark can work without ingesting data; it can generate its own data.
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
*  Spark’s map and reduce operations can use classes or lambda functions.
*  Spark provides a web interface to analyze the execution of jobs and applications.
*  Interactive mode allows you to type Scala, Python, or R commands directly in a shell. Interaction can also be achieved with the help of notebooks such as Jupyter or Zeppelin. 

Ingestion
==========

Spark allows to ingest data from files. Alone with well-known file formats the specialized ones are supported such as:

[Apache Avro](https://github.com/databricks/spark-avro) - was designed for remote procedure calls (RPCs) in a similar way as Protocol Buffers (Protobuf), a popular method for transferring serializable data developed and open sourced by Google; learn more at https://developers.google.com/protocol-buffers/.

[Apache Optimized Row Columnar (ORC)](https://orc.apache.org/) - a columnar file format

[Apache Parquet](https://parquet.apache.org/) - a columnar file format

[Comaprison of Avro, ORC, and Parquet](https://www.datanami.com/2018/05/16/big-data-file-formats-demystified/)

*  Ingestion is a key part of your big data pipeline.
*  When ingesting files, you can use a regular expression (regex) to specify the path.
*  CSV is more complex than it looks, but a rich set of options allows you to tune the parser.
*  JSON exists in two forms: the one-line form called JSON Lines, and multiline JSON. Spark ingests both forms of JSON (since v2.2.0).
*  All options for file ingestion that are described in this chapter are not casesensitive.
*  Spark can ingest CSV, JSON, and text out of the box.
*  To ingest XML, Spark needs a plugin provided by Databricks.
*  The pattern for ingesting any document is fairly similar: you specify the format and simply read.
*  Traditional file formats, including CSV, JSON, and XML, are not well suited for big data.
*  JSON and XML are not considered splittable file formats.
*  Avro, ORC, and Parquet are popular big data file formats. They embed the data and the schema in a file and compress the data. They are easier and more efficient to manipulate than CSV, JSON, and XML when it comes to big data.
*  ORC and Parquet files are in a columnar format.
*  Avro is row-based, which is more suitable for streaming.
*  ORC and Parquet’s compression is better than Avro’s
