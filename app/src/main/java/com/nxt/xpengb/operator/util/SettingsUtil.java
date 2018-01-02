package com.nxt.xpengb.operator.util;

import com.nxt.xpengb.operator.R;
import com.nxt.xpengb.operator.app.OperatorApp;

/**
 * Created by liyu on 2016/11/18.
 */

public class SettingsUtil {

    public static final String WEATHER_SHARE_TYPE = "weather_share_type";//天气分享形式
    public static final String WEATHER_KEY = "weather_key";//天气key
    public static final String THEME = "theme_color";//主题
    public static final String CLEAR_CACHE = "clean_cache";//清空缓存
    public static final String BUS_REFRESH_FREQ = "bus_refresh_freq";//公交自动刷新频率
    public static final String TTS_VOICE_TYPE = "tts_voice_type";//讯飞语音人声

    public static void setTtsVoiceType(String type) {
        SPUtil.put(OperatorApp.Companion.getContext(), TTS_VOICE_TYPE, type);
    }

    public static String getTtsVoiceType() {
        return (String) SPUtil.get(OperatorApp.Companion.getContext(), TTS_VOICE_TYPE, OperatorApp.Companion.getContext().getResources().getStringArray(R.array.tts_voice_value)[0]);
    }

    public static void setWeatherShareType(String type) {
        SPUtil.put(OperatorApp.Companion.getContext(), WEATHER_SHARE_TYPE, type);
    }

    public static String getWeatherShareType() {
        return (String) SPUtil.get(OperatorApp.Companion.getContext(), WEATHER_SHARE_TYPE, OperatorApp.Companion.getContext().getResources().getStringArray(R.array.share_type)[0]);
    }

    public static void setWeatherKey(String key) {
        SPUtil.put(OperatorApp.Companion.getContext(), WEATHER_KEY, key);
    }

    public static String getWeatherKey() {
        return (String) SPUtil.get(OperatorApp.Companion.getContext(), WEATHER_KEY, "");
    }

    public static void setTheme(int themeIndex) {
        SPUtil.put(OperatorApp.Companion.getContext(), THEME, themeIndex);
    }

    public static int getTheme() {
        return (int) SPUtil.get(OperatorApp.Companion.getContext(), THEME, 0);
    }

    public static void setBusRefreshFreq(int freq) {
        SPUtil.put(OperatorApp.Companion.getContext(), BUS_REFRESH_FREQ, freq);
    }

    public static int getBusRefreshFreq() {
        return (int) SPUtil.get(OperatorApp.Companion.getContext(), BUS_REFRESH_FREQ, 10);
    }

}
