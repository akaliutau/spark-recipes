package net.ddp.datasource;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import lombok.extern.slf4j.Slf4j;

/**
 * Ingest metadata from a directory containing photos, make them available as
 * EXIF.
 * @author akalu
 */
@Slf4j
public class PhotoMetadataIngestionApp {
    public static void main(String[] args) {
        PhotoMetadataIngestionApp app = new PhotoMetadataIngestionApp();
        app.start();
    }

    /**
     * Starts the application
     * @return <code>true</code> if all is ok.
     */
    private boolean start() {
        // Get a session
        SparkSession spark = SparkSession.builder().appName("EXIF to Dataset").master("local").getOrCreate();

        // Import directory
        String importDirectory = "data";

        // read the data
        Dataset<Row> df = spark.read().format("net.ddp.datasource.exif.ExifDirectoryDataSourceShortnameAdvertiser")
                .option("recursive", "true")
                .option("limit", "100000")
                .option("extensions", "jpg,jpeg")
                .load(importDirectory);

        log.info("imported {} photos", df.count());
        df.printSchema();
        df.show(5);

        return true;
    }
}
