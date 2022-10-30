package net.ddp.datasource.exif;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.sources.BaseRelation;
import org.apache.spark.sql.sources.TableScan;
import org.apache.spark.sql.types.StructType;
import lombok.extern.slf4j.Slf4j;
import net.ddp.datasource.extlib.ExifUtils;
import net.ddp.datasource.extlib.PhotoMetadata;
import net.ddp.datasource.extlib.RecursiveExtensionFilteredLister;
import net.ddp.datasource.utils.Schema;
import net.ddp.datasource.utils.SparkBeanUtils;

/**
 * Build a relation to return the EXIF data of photos in a directory.
 * 
 * @author akalu
 */
@Slf4j
public class ExifDirectoryRelation extends BaseRelation implements Serializable, TableScan {
    private static final long serialVersionUID = 4598175080399877334L;

    private SQLContext sqlContext;
    private Schema schema = null;
    private RecursiveExtensionFilteredLister photoLister;

    @Override
    public SQLContext sqlContext() {
        return this.sqlContext;
    }

    public void setSqlContext(SQLContext sqlContext) {
        this.sqlContext = sqlContext;
    }

    /**
     * Build and returns the schema as a StructType.
     */
    @Override
    public StructType schema() {
        if (schema == null) {
            schema = SparkBeanUtils.getSchemaFromBean(PhotoMetadata.class);
        }
        return schema.getSparkSchema();
    }

    @Override
    public RDD<Row> buildScan() {
        log.debug("-> buildScan()");
        schema();

        // I have isolated the work to a method to keep the plumbing code as
        // simple
        // as possible.
        List<PhotoMetadata> table = collectData();

        @SuppressWarnings("resource")
        JavaSparkContext sparkContext = new JavaSparkContext(sqlContext.sparkContext());
        JavaRDD<Row> rowRDD = sparkContext.parallelize(table).map(photo -> SparkBeanUtils.getRowFromBean(schema, photo));

        return rowRDD.rdd();
    }

    /**
     * Interface with the real world: the "plumbing" between Spark and existing
     * data, in our case the classes in charge of reading the information from the
     * photos. The list of photos will be "mapped" and transformed into a Row.
     */
    private List<PhotoMetadata> collectData() {
        List<File> photosToProcess = this.photoLister.getFiles();
        List<PhotoMetadata> list = new ArrayList<>();
        PhotoMetadata photo;

        for (File photoToProcess : photosToProcess) {
            photo = ExifUtils.processFromFilename(photoToProcess.getAbsolutePath());
            list.add(photo);
        }
        return list;
    }

    public void setPhotoLister(RecursiveExtensionFilteredLister photoLister) {
        this.photoLister = photoLister;
    }
}
