package net.ddp.udf.range;

import org.apache.spark.sql.api.java.UDF2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InRangeUdf implements UDF2<String, Integer, Boolean> {
    private static final long serialVersionUID = -21621751L;

    @Override
    public Boolean call(String range, Integer event) throws Exception {
        log.debug("-> call({}, {})", range, event);
        String[] ranges = range.split(";");
        for (int i = 0; i < ranges.length; i++) {
            log.debug("Processing range #{}: {}", i, ranges[i]);
            String[] hours = ranges[i].split("-");
            int start = Integer.valueOf(hours[0].substring(0, 2)) * 3600 + Integer.valueOf(hours[0].substring(3)) * 60;
            int end = Integer.valueOf(hours[1].substring(0, 2)) * 3600 + Integer.valueOf(hours[1].substring(3)) * 60;
            log.debug("Checking between {} and {}", start, end);
            if (event >= start && event <= end) {
                return true;
            }
        }
        return false;
    }

}
