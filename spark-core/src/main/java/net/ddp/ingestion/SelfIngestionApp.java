package net.ddp.ingestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import lombok.extern.slf4j.Slf4j;

/**
 * Simple ingestion followed by map and reduce operations.
 * @author akalu
 */
@Slf4j
public class SelfIngestionApp {

    /**
     * main() is your entry point to the application.
     * @param args
     */
    public static void main(String[] args) {
        SelfIngestionApp app = new SelfIngestionApp();
        app.start();
    }

    /**
     * The processing code.
     */
    private void start() {
        // Creates a session on a local master
        SparkSession spark = SparkSession.builder().appName("Self ingestion").master("local[*]").getOrCreate();

        Dataset<Row> df = createDataframe(spark);
        df.show(false);

        // SQL-like
        long totalLinesL = df.selectExpr("sum(*)").first().getLong(0);
        log.info("total lines: {}", totalLinesL);
    }

    private static Dataset<Row> createDataframe(SparkSession spark) {
        StructType schema = DataTypes.createStructType(
                new StructField[] { 
                        DataTypes.createStructField("i", DataTypes.IntegerType, false) 
                    }
                );

        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Row> rows = new ArrayList<>();
        for (int i : data) {
            rows.add(RowFactory.create(i));
        }

        return spark.createDataFrame(rows, schema);
    }
}
