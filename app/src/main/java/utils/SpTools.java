package utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lenovo on 2016/7/16.
 * Description    SharedPreferences存储相关数据（密码等信息）.
 */
public class SpTools {
    /*String类型*/
    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE, context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();//提交保存数据
    }

    public static String getString(Context context, String key, String defvalue) {
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE, context.MODE_PRIVATE);
        return sp.getString(key, defvalue);//获取数据
    }

    /*Boolean类型*/
    public static void setBoolean(Context context, String key, Boolean value) {
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE, context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();//提交保存数据
    }

    public static Boolean getBoolean(Context context, String key, Boolean defvalue) {
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE, context.MODE_PRIVATE);
        return sp.getBoolean(key, defvalue);//获取数据
    }
}
