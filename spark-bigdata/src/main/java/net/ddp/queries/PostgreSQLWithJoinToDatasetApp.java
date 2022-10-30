package net.ddp.queries;

import java.util.Properties;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import lombok.extern.slf4j.Slf4j;

/**
 * PostgreSQL injection to Spark, using the NorthWind sample database.
 * @author akalu
 */
@Slf4j
public class PostgreSQLWithJoinToDatasetApp {

    /**
     * main() is your entry point to the application.
     * @param args
     */
    public static void main(String[] args) {
        PostgreSQLWithJoinToDatasetApp app = new PostgreSQLWithJoinToDatasetApp();
        app.start();
    }

    /**
     * The processing code.
     */
    private void start() {
        SparkSession spark = SparkSession.builder().appName("PSQL with join to Dataframe using JDBC").master("local").getOrCreate();

        // Using properties
        Properties props = new Properties();
        props.put("driver", "org.postgresql.Driver");
        props.put("user", "postgres");
        props.put("password", "postgres");
        props.put("useSSL", "false");
        props.put("serverTimezone", "EST");

        // Builds the SQL query doing the join operation
        String sqlQuery = 
                "select customers.company_name, customers.country, orders.order_id, orders.ship_address " +
                "from customers, orders " + 
                "where orders.customer_id = customers.customer_id";

        Dataset<Row> df = spark.read().jdbc("jdbc:postgresql://localhost:5432/postgres", "(" + sqlQuery + ") orders", props);

        // Displays the dataframe and some of its metadata
        df.show(5);
        df.printSchema();
        log.info("The dataframe contains " + df.count() + " record(s).");
    }
}
