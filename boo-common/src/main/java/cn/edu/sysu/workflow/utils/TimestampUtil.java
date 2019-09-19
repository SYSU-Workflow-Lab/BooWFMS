package cn.edu.sysu.workflow.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Skye on 2019/9/19.
 */
public class TimestampUtil {

    /**
     * Get timestamp in pattern of `yyyy-MM-dd HH:mm:ss`
     *
     * @return timestamp in string
     */
    public static String GetTimestampString() {
        return sdf.format(new Date());
    }

    /**
     * Get current timestamp.
     *
     * @return Timestamp instance of current moment
     */
    public static Timestamp GetCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * Timestamp string formatter.
     */
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
