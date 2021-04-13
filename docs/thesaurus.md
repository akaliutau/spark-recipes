# Application 
Your program that is built on and for Spark. Consists of a driver program and executors on the cluster.

# Application JAR 
A Java archive (JAR) file containing your Spark application. It can be an uber JAR including all the dependencies. 

# Cluster manager 
An external service for acquiring resources on the cluster. It can be the Spark built-in cluster manager. 

# Deploy mode 
Defines the place where the driver process runs. 
In cluster mode, the framework launches the driver inside the cluster. 
In client mode, the submitter launches the driver outside the cluster. 

You can find out which mode you are in by calling the deployMode() method. This method returns a read-only property.

# Driver program 
The process running the main() function of the application and creating the SparkContext. This is the entry point to Spark data processing app.

# Executor 
A process launched for an application on a worker node. The executor runs tasks and keeps data in memory or in disk storage across them. 
Each application has its own executors.

# Job 
A parallel computation consisting of multiple tasks that gets spawned in response to a Spark action (for example, save() or collect())

# Stage 
Each job gets divided into smaller sets of tasks, called stages, that depend on each other (similar to the map and reduce stages in MapReduce).

# Task 
A unit of work that will be sent to one executor. Technically this is just serialized code to be executed on each available node.

# Worker node 
Any node that can run application code in the cluster, including that which developer sits on. 



