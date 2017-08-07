package Top.DouJiang.Tool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.util.*;

/**
 * Created by NicoNicoNi on 2017/8/5 0005.
 */
public class SocketTools {
    /*
    判断字符串是否为数字
     */
    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
    /*
    随机生成String
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    /*
    加密Base64
     */
    public static String Base64Encryption(String str){
        byte[] encodeBytes = Base64.encodeBase64(str.getBytes());
        return new String(encodeBytes);
    }
    /*
    解密Base64
     */
    public static String Base64Decrypt(String str){
        byte[] decodeBytes = Base64.decodeBase64(str);
        return new String(decodeBytes);
    }
    /*
    转Json为Map<String,String>
     */
    public static Map<String, String> JsonToMap(String data){
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        Map<String, String> map = g.fromJson(data, new TypeToken<Map<String, String>>() {}.getType());
        return map;
    }
    /*
    转Map<String,String>为Json
     */
    public static String MapToJson(Map<String,String> map){
        Gson g=new GsonBuilder().enableComplexMapKeySerialization().create();
        return g.toJson(map);
    }
    /*
    public static Map<String, String> TurnToMap(String s) {
        Map<String, String> map = new HashMap<>();
        String[] ss = s.split("\\,");
        for (String str : ss) {
            String[] ss2 = str.split("\\=");
            map.put(ss2[0], ss2[1]);
        }
        return map;
    }

    public static String TurnToString(Map<String, String> map) {
        String str = "[";
        for (String s : map.keySet()) {
            str = str + s + "=" + map.get(s) + ",";
        }
        str = str + "]";
        return str;
    }
    */
    /*
    防止粘包
     */

    public static List<String> TurnToList(String s) {
        List<String> list = new ArrayList<>();
        char[] chars = s.toCharArray();
        boolean start = false;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '[') {
                start = true;
            }
            if (c == ']') {
                start = false;
                list.add(sb.toString());
                //   sb=null;
                sb = new StringBuffer();
            }
            if (start) {
                if (c != '[') {
                    sb.append(c);
                }
            }
        }
        return list;
    }
    /*
    进行MD5加密
     */
    public static String StringToMD5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            //System.out.println(e.toString());
            //e.printStackTrace();
            return null;
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }


}
