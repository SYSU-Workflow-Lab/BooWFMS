package cn.edu.sysu.workflow.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Skye on 2019/9/19.
 */
public class SerializationUtil {
    /**
     * Jsonify an object.
     *
     * @param serializable object to be converted to json
     * @return json string
     */
    public static String JsonSerialization(Object serializable) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(serializable);
        } catch (Exception ex) {
//            LogUtil.Log("When json serialization exception occurred, " + ex.toString(),
//                    SerializationUtil.class.getName(), LogUtil.LogLevelType.ERROR, "");
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
    public static <Ty> Ty JsonDeserialization(String serialized, Class<Ty> type) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(serialized, type);
        } catch (Exception ex) {
//            LogUtil.Log("When un serialization exception occurred, " + ex.toString(),
//                    SerializationUtil.class.getName(), LogUtil.LogLevelType.ERROR, "");
            return null;
        }
    }
}
