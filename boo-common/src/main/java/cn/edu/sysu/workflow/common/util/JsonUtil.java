package cn.edu.sysu.workflow.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Json Serialization and Deserialization
 *
 * @author Skye
 * Created on 2019/9/19
 */
public final class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * Jsonify an object.
     *
     * @param serialization object to be converted to json
     * @return json string
     */
    public static String jsonSerialization(Object serialization) {
        return jsonSerialization(serialization, "");
    }

    /**
     * Jsonify an object.
     *
     * @param serialization object to be converted to json
     * @param processInstanceId
     * @return
     */
    public static String jsonSerialization(Object serialization, String processInstanceId) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(serialization);
        } catch (Exception ex) {
            log.error("[" + processInstanceId + "]When json serialization exception occurred, " + ex.toString());
            return null;
        }
    }

    /**
     * Un jsonify an object.
     *
     * @param serialized json serialized string
     * @param type       return class type
     * @return class instance of type
     */
    public static <T> T jsonDeserialization(String serialized, Class<T> type) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(serialized, type);
        } catch (Exception ex) {
            log.error("When deserialization exception occurred, " + ex.toString());
            return null;
        }
    }
}
