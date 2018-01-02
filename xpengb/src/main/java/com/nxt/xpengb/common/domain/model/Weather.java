package com.nxt.xpengb.common.domain.model;

import java.util.List;

/**
 * Create : xpengb@outlook.com
 * Date   : 2017/6/12
 * Version: V1.0
 * Desc   :
 */

public class Weather {
    /**
     * error : 0
     * status : success
     * date : 2017-06-12
     * results : [{"currentCity":"毕节市","pm25":"34","index":[{"title":"穿衣","zs":"较舒适","tipt":"穿衣指数","des":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。"},{"title":"洗车","zs":"较适宜","tipt":"洗车指数","des":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},{"title":"感冒","zs":"易发","tipt":"感冒指数","des":"天冷空气湿度大，易发生感冒，请注意适当增加衣服，加强自我防护避免感冒。"},{"title":"运动","zs":"较不宜","tipt":"运动指数","des":"阴天，且天气较热，请减少运动时间并降低运动强度。"},{"title":"紫外线强度","zs":"最弱","tipt":"紫外线强度指数","des":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}],"weather_data":[{"date":"周一 06月12日 (实时：18℃)","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/yin.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/yin.png","Weather":"阴","wind":"微风","temperature":"22 ~ 17℃"},{"date":"周二","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/zhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/zhenyu.png","Weather":"阵雨","wind":"微风","temperature":"22 ~ 17℃"},{"date":"周三","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/zhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/zhongyu.png","Weather":"阵雨转中雨","wind":"微风","temperature":"21 ~ 16℃"},{"date":"周四","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/zhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","Weather":"阵雨转多云","wind":"微风","temperature":"19 ~ 15℃"}]}]
     */

    private int error;
    private String status;
    private String date;
    public List<ResultsBean> results;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * currentCity : 毕节市
         * pm25 : 34
         * index : [{"title":"穿衣","zs":"较舒适","tipt":"穿衣指数","des":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。"},{"title":"洗车","zs":"较适宜","tipt":"洗车指数","des":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},{"title":"感冒","zs":"易发","tipt":"感冒指数","des":"天冷空气湿度大，易发生感冒，请注意适当增加衣服，加强自我防护避免感冒。"},{"title":"运动","zs":"较不宜","tipt":"运动指数","des":"阴天，且天气较热，请减少运动时间并降低运动强度。"},{"title":"紫外线强度","zs":"最弱","tipt":"紫外线强度指数","des":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}]
         * weather_data : [{"date":"周一 06月12日 (实时：18℃)","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/yin.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/yin.png","Weather":"阴","wind":"微风","temperature":"22 ~ 17℃"},{"date":"周二","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/zhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/zhenyu.png","Weather":"阵雨","wind":"微风","temperature":"22 ~ 17℃"},{"date":"周三","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/zhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/zhongyu.png","Weather":"阵雨转中雨","wind":"微风","temperature":"21 ~ 16℃"},{"date":"周四","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/zhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","Weather":"阵雨转多云","wind":"微风","temperature":"19 ~ 15℃"}]
         */

        private String currentCity;
        private String pm25;
        private List<IndexBean> index;
        private List<WeatherDataBean> weather_data;

        public String getCurrentCity() {
            return currentCity;
        }

        public void setCurrentCity(String currentCity) {
            this.currentCity = currentCity;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public List<IndexBean> getIndex() {
            return index;
        }

        public void setIndex(List<IndexBean> index) {
            this.index = index;
        }

        public List<WeatherDataBean> getWeather_data() {
            return weather_data;
        }

        public void setWeather_data(List<WeatherDataBean> weather_data) {
            this.weather_data = weather_data;
        }

        public static class IndexBean {
            /**
             * title : 穿衣
             * zs : 较舒适
             * tipt : 穿衣指数
             * des : 建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。
             */

            private String title;
            private String zs;
            private String tipt;
            private String des;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getZs() {
                return zs;
            }

            public void setZs(String zs) {
                this.zs = zs;
            }

            public String getTipt() {
                return tipt;
            }

            public void setTipt(String tipt) {
                this.tipt = tipt;
            }

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }
        }

        public static class WeatherDataBean {
            /**
             * date : 周一 06月12日 (实时：18℃)
             * dayPictureUrl : http://api.map.baidu.com/images/weather/day/yin.png
             * nightPictureUrl : http://api.map.baidu.com/images/weather/night/yin.png
             * Weather : 阴
             * wind : 微风
             * temperature : 22 ~ 17℃
             */

            private String date;
            private String dayPictureUrl;
            private String nightPictureUrl;
            private String weather;
            private String wind;
            private String temperature;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDayPictureUrl() {
                return dayPictureUrl;
            }

            public void setDayPictureUrl(String dayPictureUrl) {
                this.dayPictureUrl = dayPictureUrl;
            }

            public String getNightPictureUrl() {
                return nightPictureUrl;
            }

            public void setNightPictureUrl(String nightPictureUrl) {
                this.nightPictureUrl = nightPictureUrl;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }
        }
    }
}
