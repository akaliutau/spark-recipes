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

*  Spark is not limited to the MapReduce algorithm: its API allows a lot of algorithms to be applied to data.

*  Streaming is used more and more frequently in enterprises, as businesses want access to real-time analytics. Spark supports streaming.

*  Analytics have evolved from simple joins and aggregations. Enterprises want computers to think for us; hence Spark supports machine learning and deep learning.

*  Graphs are a special use case of analytics supported by Spark.

Basics, Level 1
================

*  The initial application built on the basis of Spark framework is the driver. Data may not have to come to the driver; it can be driven remotely.

*  The driver connects to a master and gets a session. Data will be attached to this session; the session defines the life cycle of the data on the worker’s nodes.

*  The master can be local (your local machine) or a remote cluster. Using the local mode will not require you to build a cluster, making life much easier during development phase.

*  Data is partitioned and processed within the partition. Partitions are in memory.

*  Spark can easily read from CSV files read/write data from/to relational databases, other sources could be JSON files, binary files, etc.

*  Spark is lazy: it will work only when you ask it to do so via an action.

*  Spark’s APIs rely heavily on method chaining due to its functional fundamentals (Spark is written in functional language, Scala)


