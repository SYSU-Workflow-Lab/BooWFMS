package cn.edu.sysu.workflow.common.util;

/**
 * Id generator based on Snowflake of BooWFMS
 *
 * @author Skye
 * Created on 2019/12/22
 */
public final class IdUtil {

    /**
     * 起始时间戳：2019/12/22
     */
    private static final long OFFSET = 1576944000000L;

    /**
     * 服务标识限制10位
     */
    private static final long MAX_SERVERIDHASHCODE = ~(-1 << 10);

    /**
     * 自增限制12位
     */
    private static final long MAX_NEXT = ~(-1 << 12);

    /**
     * 服务ID
     */
    private static final String SERVER_ID = "Unknown";

    /**
     * 临时自增量
     */
    private static long offset = 0;

    /**
     * 上次生成ID时间戳
     */
    private static long lastTimestamp = 0;

    /**
     * 41位毫秒级时间戳+10位服务标识+12位自增
     *
     * @return Id
     */
    public static synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        // 防止系统时间回拨
        if (timestamp < lastTimestamp) {
            timestamp = lastTimestamp;
        }
        if (lastTimestamp != timestamp) {
            lastTimestamp = timestamp;

            //新的一秒开始时，重置临时自增量
            offset = 0;
        }
        offset++;
        long next = offset & MAX_NEXT;
        // 自增值超过2^12时，阻塞到下一毫秒
        if (next == 0) {
            lastTimestamp = tilNextMillis();
        }
        long serverIdHashCode = SERVER_ID.hashCode() & MAX_SERVERIDHASHCODE;
        return ((timestamp - OFFSET) << 22) | (serverIdHashCode << 12) | next;
    }

    /**
     * 阻塞到下一毫秒，直到获得新的时间戳
     *
     * @return 当前时间戳
     */
    private static long tilNextMillis() {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
