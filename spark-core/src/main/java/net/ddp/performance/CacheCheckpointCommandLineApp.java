package net.ddp.performance;

import static org.apache.spark.sql.functions.col;

import java.util.List;

import org.apache.spark.SparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import lombok.extern.slf4j.Slf4j;

/**
 * Measuring performance without cache, with cache, and with checkpoint. Can be
 * run via the command line:
 * 
 * @formatter:off mvn -q exec:exec -Dargs="100000" -DXmx=4G 2>/dev/null 
 *                                          |             | 
 *                                          |             +-- 4GB for Xmx Java memory, 
 *                                          |                 the more records you will 
 *                                          |                process, the more you will need. 
 *                                          | 
 *                                          +-- Number of records to  create.
 * @formatter:on
 * @author akalu
 */
@Slf4j
public class CacheCheckpointCommandLineApp {
    enum Mode {
        NO_CACHE_NO_CHECKPOINT, CACHE, CHECKPOINT, CHECKPOINT_NON_EAGER
    }

    private SparkSession spark;

    /**
     * main() is your entry point to the application.
     * @param args
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        }

        int recordCount = Integer.parseInt(args[0]);
        String master;
        if (args.length > 1) {
            master = args[1];
        } else {
            master = "local[*]";
        }

        CacheCheckpointCommandLineApp app = new CacheCheckpointCommandLineApp();
        app.start(recordCount, master);
    }

    /**
     * The processing code.
     */
    private void start(int recordCount, String master) {
        System.out.printf("-> start(%d, %s)\n", recordCount, master);

        // Creates a session on a local master
        this.spark = SparkSession.builder().appName("Example of cache and checkpoint").master(master)
                .config("spark.executor.memory", "70g")
                .config("spark.driver.memory", "50g")
                .config("spark.memory.offHeap.enabled", true)
                .config("spark.memory.offHeap.size", "16g").getOrCreate();
        SparkContext sc = spark.sparkContext();
        sc.setCheckpointDir("/tmp");

        // Create and process the records without cache or checkpoint
        long t0 = processDataframe(recordCount, Mode.NO_CACHE_NO_CHECKPOINT);

        // Create and process the records with cache
        long t1 = processDataframe(recordCount, Mode.CACHE);

        // Create and process the records with a checkpoint
        long t2 = processDataframe(recordCount, Mode.CHECKPOINT);

        // Create and process the records with a checkpoint
        long t3 = processDataframe(recordCount, Mode.CHECKPOINT_NON_EAGER);
        spark.stop();

        log.info("Processing times");
        log.info("Without cache ............... {} ms ", t0);
        log.info("With cache .................. {} ms", t1);
        log.info("With checkpoint ............. {} ms", t2);
        log.info("With non-eager checkpoint ... {} ms", t3);
    }

    /**
     * @param df
     * @param mode
     * @return
     */
    private long processDataframe(int recordCount, Mode mode) {
        Dataset<Row> df = RecordGeneratorUtils.createDataframe(this.spark, recordCount);

        long t0 = System.currentTimeMillis();
        Dataset<Row> topDf = df.filter(col("rating").equalTo(5));
        switch (mode) {
        case CACHE:
            topDf = topDf.cache();
            break;

        case CHECKPOINT:
            topDf = topDf.checkpoint();
            break;

        case CHECKPOINT_NON_EAGER:
            topDf = topDf.checkpoint();
            break;
        }

        List<Row> langDf = topDf.groupBy("lang").count().orderBy("lang").collectAsList();
        List<Row> yearDf = topDf.groupBy("year").count().orderBy(col("year").desc()).collectAsList();
        long t1 = System.currentTimeMillis();

        log.info("Processing took {} ms", (t1 - t0));

        log.info("Five-star publications per language");
        for (Row r : langDf) {
            log.info("{} -- {}", r.getString(0), r.getLong(1));
        }

        log.info("\nFive-star publications per year");
        for (Row r : yearDf) {
            log.info("{} -- {}", r.getString(0), r.getLong(1));
        }

        return t1 - t0;
    }
}
