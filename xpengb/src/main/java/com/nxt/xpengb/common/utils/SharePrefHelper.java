package com.nxt.xpengb.common.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Map;
import java.util.Set;

/**
 * Create : xpengb@outlook.com
 * Date   : 2017/1/4
 * Version: V1.0
 * Desc   : 加强版SharePreferences工具类，可以存储Int、String、boolean、long、float、
 *          Set<String>、JSONObject、JSONArray、byte[]、Serializable、Bitmap、Drawable
 */

public class SharePrefHelper {
    private static final String FILE_NAME = "sharePref";

    private static SharePrefHelper instance = null;
    private static SharedPreferences.Editor editor;
    private static SharedPreferences preferences;

    public SharePrefHelper(Context context){
        preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    private static synchronized void syncInit(Context context){
        if(instance == null){
            instance = new SharePrefHelper(context);
        }
    }

    public static SharePrefHelper getInstance(Context context){
        if(instance == null){
            syncInit(context);
        }
        return instance;
    }

    public static Map<String, ?> getAll(){
        return preferences.getAll();
    }

    public static void release(){
        if (instance != null){
            instance = null;
            System.gc();
        }
    }

    public static boolean remove(String key){
        return editor.remove(key).commit();
    }

    public static boolean clear(){
        return editor.clear().commit();
    }

    public static boolean put(String key, String value) {
        return editor.putString(key, value).commit();
    }

    public static String getString(String key){
        return getString(key, null);
    }

    public static String getString(String key, String def) {
        return preferences.getString(key, def);
    }

    public static boolean put(String key, boolean value) {
        return editor.putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key){
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean def) {
        return preferences.getBoolean(key, def);
    }

    public static boolean put(String key, int value) {
        return editor.putInt(key, value).commit();
    }

    public static int getInt(String key){
        return getInt(key, -1);
    }

    public static int getInt(String key, int def) {
        return preferences.getInt(key, def);
    }

    public static boolean put(String key, long value) {
        return editor.putLong(key, value).commit();
    }

    public static long getLong(String key){
        return getLong(key, -1L);
    }

    public static long getLong(String key, long def) {
        return preferences.getLong(key, def);
    }

    public static boolean put(String key, float value) {
        return editor.putFloat(key, value).commit();
    }

    public static float getFloat(String key){
        return getFloat(key, -1f);
    }

    public static float getFloat(String key, float def) {
        return preferences.getFloat(key, def);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static boolean put(String key, Set<String> value){
        return editor.putStringSet(key, value).commit();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> getStringSet(String key){
        return getStringSet(key, null);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> getStringSet(String key, Set<String> def) {
        return preferences.getStringSet(key, def);
    }

    /**
     * 保存JSONObject数据
     * @param key
     * @param value
     */
    public static void put(String key, JSONObject value) {
        saveObject(key, value);
    }

    public static JSONObject getJSONObject(String key){
        Object object = readObject(key);
        if(object instanceof JSONObject){
            return (JSONObject) object;
        }
        return null;
    }

    public static void put(String key, JSONArray value) {
        saveObject(key, value);
    }

    public static JSONArray getJSONArray(String key) {
        Object object = readObject(key);
        if (object instanceof JSONArray) {
            return (JSONArray) object;
        }
        return null;
    }

    public static void put(String key, byte[] value){
        saveObject(key, value);
    }

    public static byte[] getBinary(String key) {
        Object object = readObject(key);
        if (object instanceof byte[]) {
            return (byte[]) object;
        }
        return null;
    }

    public static void put(String key, Serializable value) {
        saveObject(key, value);
    }

    public static void put(String key, Object value) {
        saveObject(key, value);
    }

    public static Object getObject(String key) {
        Object object = readObject(key);
        if (object instanceof Serializable) {
            return (Serializable) object;
        }
        return null;
    }

    public static void put(String key, Bitmap value) {
        saveObject(key, value);
    }

    public static Bitmap getBitmap(String key) {
        Object object = readObject(key);
        if (object instanceof Bitmap) {
            return (Bitmap) object;
        }
        return null;
    }

    public static void put(String key, Drawable value) {
        saveObject(key, value);
    }

    public static Drawable getDrawable(String key) {
        Object object = readObject(key);
        if (object instanceof Drawable) {
            return (Drawable) object;
        }
        return null;
    }

    private static void saveObject(String key, Object obj){
        try {
            //先将序列化结果写到byte缓存中，其实就是分配一个内存空间
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(obj);
            //将序列化的数据转为16进制保存
            String bytesToHexString =bytesToHexString(bos.toByteArray());
            //保存16进制数组
            editor.putString(key, bytesToHexString);
            editor.commit();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 将数组转为16进制
     * @param byteArray
     * @return
     */
    private static String bytesToHexString(byte[] byteArray){
        if(byteArray == null){
            return null;
        }
        if(byteArray.length == 0){
            return "";
        }
        StringBuffer sb = new StringBuffer(byteArray.length);
        String sTemp;
        for(int i = 0; i<byteArray.length; i++){
            sTemp = Integer.toHexString(0xFF & byteArray[i]);
            if(sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 获取保存的Object对象
     * @param key
     * @return
     */
    private static Object readObject(String key){
        try {
            if(preferences.contains(key)){
                String string= preferences.getString(key, "");
                if (TextUtils.isEmpty(string)){
                    return null;
                }else {
                    //将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    //返回反序列化得到的对象
                    Object readObject = is.readObject();
                    return readObject;
                }
            }
        }catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //所有异常返回null
        return null;
    }

    /**
     * 将16进制的数据转为数组
     * @param data
     * @return
     */
    private static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch3;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch3 = (hex_char1 - 48) * 16;   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch3 = (hex_char1 - 55) * 16;//// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch4;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch4 = (hex_char2 - 48); //// 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch4 = hex_char2 - 55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch3 + int_ch4;
            retData[i / 2] = (byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }
}
