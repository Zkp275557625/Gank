package com.zkp.androidwan.module.main.activity.weather.fragment;

import com.zkp.androidwan.base.presenter.IPresenter;
import com.zkp.androidwan.base.view.IView;
import com.zkp.androidwan.bean.CurrentWetaherBean;
import com.zkp.androidwan.bean.DailyWeatherBean;
import com.zkp.androidwan.bean.HourlyWeatherBean;
import com.zkp.androidwan.db.entity.Address;

/**
 * @author: zkp
 * @project: Gank
 * @package: com.zkp.gank.module.main.activity.weather.fragment
 * @time: 2019/5/14 17:17
 * @description:
 */
public class WeatherFragmentContract {

    public interface View extends IView {

        /**
         * 获取实时天气成功
         *
         * @param data CurrentWetaherBean
         */
        void getCurrentJsonSucess(CurrentWetaherBean data);

        /**
         * 获取实时天气失败
         *
         * @param errMsg String
         */
        void getCurrentJsonErrror(String errMsg);

        /**
         * 获取天级天气预报成功
         *
         * @param data DailyWeatherBean
         */
        void getDailyJsonSuccess(DailyWeatherBean data);

        /**
         * 获取天级天气预报失败
         *
         * @param errMsg String
         */
        void getDailyJsonError(String errMsg);

        /**
         * 获取小时级天气预报成功
         *
         * @param data HourlyWeatherBean
         */
        void getHouelyJsonSucess(HourlyWeatherBean data);

        /**
         * 获取小时级天气预报失败
         *
         * @param errMsg String
         */
        void getHouelyJsonError(String errMsg);

        /**
         * 更新Address成功
         *
         * @param address
         */
        void updateAddressSuccess(Address address);

    }

    public interface Presenter extends IPresenter<View> {

        /**
         * 获取实时天气
         *
         * @param longitude 经度
         * @param latitude  纬度
         */
        void getCurrentJson(String longitude, String latitude);

        /**
         * 获取天气
         *
         * @param skyCon
         * @return
         */
        String getWeather(String skyCon);

        /**
         * 获取天气质量等级
         *
         * @param aqi
         * @return
         */
        String getApiLeve(int aqi);

        /**
         * 获取风力等级
         *
         * @param speed
         * @return
         */
        String getWindSpeed(double speed);

        /**
         * 获取风向信息
         *
         * @param direction
         * @return
         */
        String getWindDirection(double direction);

        /**
         * 获取小时级天气预报
         *
         * @param longitude 经度
         * @param latitude  纬度
         */
        void getHouelyJson(String longitude, String latitude);

        /**
         * 获取小时级天气预报
         *
         * @param longitude 经度
         * @param latitude  纬度
         */
        void getDailyJson(String longitude, String latitude);

        /**
         * 计算昼长
         *
         * @param sunRise 日出时间 05：12
         * @param sunSet  日落时间 18:56
         * @return
         */
        String getDayLong(String sunRise, String sunSet);

        /**
         * 获取星期几
         *
         * @param date  日期
         * @param index 索引 index=0返回今日
         * @return
         */
        String getWeek(String date, int index);

        /**
         * 获取天气id
         *
         * @param weather
         * @return
         */
        int getWeatherId(String weather);

        /**
         * 更新城市/地区
         *
         * @param address
         */
        void updateAddress(Address address);
    }

}
