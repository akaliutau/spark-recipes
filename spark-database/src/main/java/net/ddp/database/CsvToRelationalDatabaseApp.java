package net.ddp.database;

import static org.apache.spark.sql.functions.concat;
import static org.apache.spark.sql.functions.lit;

import java.util.Properties;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import lombok.extern.log4j.Log4j;

/**
 * CSV to a relational database.
 */
@Log4j
public class CsvToRelationalDatabaseApp {

    /**
     * main() is your entry point to the application.
     * @param args
     */
    public static void main(String[] args) {
        CsvToRelationalDatabaseApp app = new CsvToRelationalDatabaseApp();
        app.start();
    }

    /**
     * The processing code.
     */
    private void start() {
        // Creates a session on a local master
        SparkSession spark = SparkSession.builder().appName("CSV to DB").master("local").getOrCreate();

        // Step 1: Ingestion
        // ---------

        // Reads a CSV file with header, called authors.csv, stores it in a
        // dataframe
        Dataset<Row> df = spark.read().format("csv").option("header", "true").load("data/authors.csv");

        // Step 2: Transform
        // ---------

        // Creates a new column called "name" as the concatenation of lname, a
        // virtual column containing ", " and the fname column
        df = df.withColumn("name", concat(df.col("last_name"), lit(", "), df.col("first_name")));

        // Step 3: Save
        // ---------

        // The connection URL, assuming your PostgreSQL instance runs locally on
        // the
        // default port, and the database we use is "spark_labs"
        String dbConnectionUrl = "jdbc:postgresql://localhost/spark_db";

        // Properties to connect to the database, the JDBC driver is part of our
        // pom.xml
        Properties prop = new Properties();
        prop.setProperty("driver", "org.postgresql.Driver");
        prop.setProperty("user", "tester");
        prop.setProperty("password", "spark3");

        // Write in a table called books
        df.write().mode(SaveMode.Overwrite).jdbc(dbConnectionUrl, "books", prop);

        log.info("Process complete");
    }
}
