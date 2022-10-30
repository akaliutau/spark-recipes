package net.ddp.ingestion;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import lombok.extern.slf4j.Slf4j;

/**
 * Parquet ingestion in a dataframe.
 * 
 * Source of file: Apache Parquet project -
 * https://github.com/apache/parquet-testing
 * 
 * @author akalu
 */
@Slf4j
public class ParquetToDataframeApp {

  /**
   * main() is your entry point to the application.
   * 
   * @param args
   */
  public static void main(String[] args) {
    ParquetToDataframeApp app = new ParquetToDataframeApp();
    app.start();
  }

  /**
   * The processing code.
   */
  private void start() {
    // Creates a session on a local master
    SparkSession spark = SparkSession.builder()
        .appName("Parquet to Dataframe")
        .master("local")
        .getOrCreate();

    // Reads a Parquet file, stores it in a dataframe
    Dataset<Row> df = spark.read()
        .format("parquet")
        .load("data/in_files/alltypes_plain.parquet");

    // Shows at most 10 rows from the dataframe
    df.show(10);
    df.printSchema();
    log.info("The dataframe has {} rows.", df.count());
  }
}
