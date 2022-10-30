package net.ddp.ingestion;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * Text ingestion in a dataframe. Source of file: Rome & Juliet (Shakespeare) -
 * http://www.gutenberg.org/cache/epub/1777/pg1777.txt
 * @author akalu
 */
public class TextToDataframeApp {

    /**
     * main() is your entry point to the application.
     * @param args
     */
    public static void main(String[] args) {
        TextToDataframeApp app = new TextToDataframeApp();
        app.start();
    }

    /**
     * The processing code.
     */
    private void start() {
        // Creates a session on a local master
        SparkSession spark = SparkSession.builder().appName("Text to Dataframe").master("local").getOrCreate();

        // Reads a Romeo and Juliet (faster than you!), stores it in a dataframe
        Dataset<Row> df = spark.read().format("text").load("data/in_files/romeo-juliet-pg1777.txt");

        // Shows at most 10 rows from the dataframe
        df.show(10);
        df.printSchema();
    }
}
