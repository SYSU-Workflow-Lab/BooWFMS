package cn.edu.sysu.workflow.engine.util;

import cn.edu.sysu.workflow.engine.core.BOInstance;
import cn.edu.sysu.workflow.engine.core.model.SCXML;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Skye
 * Created on 2019/12/29
 */
public final class SerializationUtil {

    private static final Logger log = LoggerFactory.getLogger(SerializationUtil.class);

    /**
     * Serialize SCXML instance to a string.
     *
     * @param scxml {@code SCXML} instance
     * @return serialized string
     */
    public static byte[] SerializationSCXMLToByteArray(SCXML scxml) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream);
            out.writeObject(scxml);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception ex) {
            log.error("[" + scxml.getId() + "]When SerializationSCXMLToString exception occurred, " + ex.toString());
            return null;
        }
    }

    /**
     * Deserialize string to SCXML instance.
     *
     * @param serializedSCXML string to be deserialized
     * @return {@code SCXML} instance.
     */
    public static SCXML DeserializationSCXMLByByteArray(byte[] serializedSCXML) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedSCXML);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (SCXML) objectInputStream.readObject();
        } catch (Exception ex) {
            log.error("When DeSerializationSCXML exception occurred, " + ex.toString());

            return null;
        }
    }

    /**
     * Serialize BO instance to a string.
     *
     * @param instance {@code BOInstance} instance
     * @return serialized string
     */
    public static byte[] SerializationBOInstanceToByteArray(BOInstance instance) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream);
            out.writeObject(instance);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception ex) {
            log.error("[" + instance.Rtid + "]When SerializationBOInstanceToString exception occurred, " + ex.toString());
            return null;
        }
    }

    /**
     * Deserialize string to BO instance.
     *
     * @param serialized string to be deserialized
     * @return {@code SCXML} instance.
     */
    public static BOInstance DeserializationBOInstanceByByteArray(byte[] serialized) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serialized);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (BOInstance) objectInputStream.readObject();
        } catch (Exception ex) {
            log.error("When DeSerializationBOInstance exception occurred, " + ex.toString());
            return null;
        }
    }

}
