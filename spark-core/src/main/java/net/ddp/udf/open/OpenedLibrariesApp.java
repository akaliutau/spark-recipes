package net.ddp.udf.open;

import static org.apache.spark.sql.functions.callUDF;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.lit;
import static org.apache.spark.sql.functions.to_timestamp;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

/**
 * Custom UDF to check if in range.
 * @author akalu
 */
public class OpenedLibrariesApp {

    /**
     * main() is your entry point to the application.
     * @param args
     */
    public static void main(String[] args) {
        OpenedLibrariesApp app = new OpenedLibrariesApp();
        app.start();
    }

    /**
     * The processing code.
     */
    private void start() {
        // Creates a session on a local master
        SparkSession spark = SparkSession.builder().appName("Custom UDF to check if in range").master("local[*]").getOrCreate();
        spark.udf().register("isOpen", new IsOpenUdf(), DataTypes.BooleanType);

        Dataset<Row> librariesDf = spark.read().format("csv")
                .option("header", true).option("inferSchema", true).option("encoding", "cp1252")
                .load("data/south_dublin_libraries/sdlibraries.csv")
                .drop("Administrative_Authority").drop("Address1").drop("Address2").drop("Town").drop("Postcode")
                .drop("County").drop("Phone").drop("Email").drop("Website").drop("Image").drop("WGS84_Latitude").drop("WGS84_Longitude");
        librariesDf.show(false);
        librariesDf.printSchema();

        Dataset<Row> dateTimeDf = createDataframe(spark);
        dateTimeDf.show(false);
        dateTimeDf.printSchema();

        Dataset<Row> df = librariesDf.crossJoin(dateTimeDf);
        df.show(false);

        // Using the dataframe API
        Dataset<Row> finalDf = df
                .withColumn("open",
                        callUDF("isOpen", 
                                col("Opening_Hours_Monday"), 
                                col("Opening_Hours_Tuesday"), 
                                col("Opening_Hours_Wednesday"), 
                                col("Opening_Hours_Thursday"), 
                                col("Opening_Hours_Friday"), 
                                col("Opening_Hours_Saturday"), 
                                lit("Closed"), 
                                col("date")
                 ))
                .drop("Opening_Hours_Monday").drop("Opening_Hours_Tuesday").drop("Opening_Hours_Wednesday").drop("Opening_Hours_Thursday").drop("Opening_Hours_Friday").drop("Opening_Hours_Saturday");
        finalDf.show();

        // Using SQL

    }

    private static Dataset<Row> createDataframe(SparkSession spark) {
        StructType schema = DataTypes.createStructType(new StructField[] { DataTypes.createStructField("date_str", DataTypes.StringType, false) });

        List<Row> rows = new ArrayList<>();
        rows.add(RowFactory.create("2019-03-11 14:30:00"));
        rows.add(RowFactory.create("2019-04-27 16:00:00"));
        rows.add(RowFactory.create("2020-01-26 05:00:00"));

        return spark.createDataFrame(rows, schema).withColumn("date", to_timestamp(col("date_str"))).drop("date_str");
    }
}
