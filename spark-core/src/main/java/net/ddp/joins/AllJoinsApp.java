package net.ddp.joins;

import java.util.ArrayList;
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
 * All joins in a single app, inspired by
 * https://stackoverflow.com/questions/45990633/what-are-the-various-join-types-in-spark
 * @author akalu
 */
@Slf4j
public class AllJoinsApp {

    /**
     * main() is your entry point to the application.
     * @param args
     */
    public static void main(String[] args) {
        AllJoinsApp app = new AllJoinsApp();
        app.start();
    }

    /**
     * The processing code.
     */
    private void start() {
        // Creates a session on a local master
        SparkSession spark = SparkSession.builder().appName("All joins demo").master("local").getOrCreate();

        StructType schema = DataTypes.createStructType(new StructField[] { DataTypes.createStructField("id", DataTypes.IntegerType, false), DataTypes.createStructField("value", DataTypes.StringType, false) });

        List<Row> rows = new ArrayList<Row>();
        rows.add(RowFactory.create(1, "Value 1"));
        rows.add(RowFactory.create(2, "Value 2"));
        rows.add(RowFactory.create(3, "Value 3"));
        rows.add(RowFactory.create(4, "Value 4"));
        Dataset<Row> dfLeft = spark.createDataFrame(rows, schema);
        dfLeft.show();

        rows = new ArrayList<Row>();
        rows.add(RowFactory.create(3, "Value 3"));
        rows.add(RowFactory.create(4, "Value 4"));
        rows.add(RowFactory.create(4, "Value 4_1"));
        rows.add(RowFactory.create(5, "Value 5"));
        rows.add(RowFactory.create(6, "Value 6"));
        Dataset<Row> dfRight = spark.createDataFrame(rows, schema);
        dfRight.show();

        String[] joinTypes = new String[] { "inner", // v2.0.0. default

                "outer", // v2.0.0
                "full", // v2.1.1
                "full_outer", // v2.1.1

                "left", // v2.1.1
                "left_outer", // v2.0.0

                "right", // v2.1.1
                "right_outer", // v2.0.0

                "left_semi", // v2.0.0, was leftsemi before v2.1.1

                "left_anti", // v2.1.1

                "cross" // with a column, v2.2.0
        };

        for (String joinType : joinTypes) {
            log.info(joinType.toUpperCase() + " JOIN");
            Dataset<Row> df = dfLeft.join(dfRight, dfLeft.col("id").equalTo(dfRight.col("id")), joinType);
            df.orderBy(dfLeft.col("id")).show();
        }

        log.info("CROSS JOIN (without a column");
        Dataset<Row> df = dfLeft.crossJoin(dfRight);
        df.orderBy(dfLeft.col("id")).show();
    }
}
