package Top.DouJiang.Util.Redis;

/**
 * Created by NicoNicoNi on 2017/8/6 0006.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SerializeUtil {

    /**
     * serialize Object
     *
     * @param object
     * @return byte[]
     */
    public static byte[] serialize(final Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * unserialize byte[]
     *
     * @param bytes
     * @return Object
     */
    public static Object unserialize(final byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * serialize hash Map<Object, Object>
     *
     * @param hash
     * @return Map<byte[], byte[]>
     */
    public static Map<byte[], byte[]> serializehmoo2mbb(Map<Object, Object> hash) {
        Map<byte[], byte[]> result = new HashMap<byte[], byte[]>();
        try {
            Set<Object> keys = hash.keySet();
            if (keys != null && keys.size() > 0) {
                for (Object key : keys) {
                    result.put(serialize(key), serialize(hash.get(key)));
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * unserialize hash Map<byte[], byte[]>
     *
     * @param hash
     * @return Map<Object, Object>
     */
    public static Map<Object, Object> unserializehmbb2moo(final Map<byte[], byte[]> hash) {
        Map<Object, Object> result = new HashMap<Object, Object>();
        try {
            Set<byte[]> keys = hash.keySet();
            if (keys != null && keys.size() > 0) {
                for (byte[] key : keys) {
                    result.put(unserialize(key), unserialize(hash.get(key)));
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * serialize hash Map<String, Object>
     *
     * @param hash
     * @return Map<byte[], byte[]>
     */
    public static Map<byte[], byte[]> serializehmso2mbb(final Map<String, Object> hash) {
        Map<byte[], byte[]> result = new HashMap<byte[], byte[]>();
        try {
            Set<String> keys = hash.keySet();
            if (keys != null && keys.size() > 0) {
                for (String key : keys) {
                    result.put(serialize(key), serialize(hash.get(key)));
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * unserialize hash Map<byte[], byte[]>
     *
     * @param hash
     * @return Map<String, Object>
     */
    public static Map<String, Object> unserializehmbb2mso(final Map<byte[], byte[]> hash) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Set<byte[]> keys = hash.keySet();
            if (keys != null && keys.size() > 0) {
                for (byte[] key : keys) {
                    result.put(unserialize(key).toString(), unserialize(hash.get(key)));
                }
            }
        } catch (Exception e) {
        }
        return result;
    }
}