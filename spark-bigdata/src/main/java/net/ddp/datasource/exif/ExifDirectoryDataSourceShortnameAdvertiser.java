package net.ddp.datasource.exif;

import org.apache.spark.sql.sources.DataSourceRegister;

/**
 * Defines the "short name" for the data source
 * @author akalu
 */
public class ExifDirectoryDataSourceShortnameAdvertiser extends ExifDirectoryDataSource implements DataSourceRegister {

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.spark.sql.sources.DataSourceRegister#shortName()
     */
    @Override
    public String shortName() {
        return "exif";
    }

}
