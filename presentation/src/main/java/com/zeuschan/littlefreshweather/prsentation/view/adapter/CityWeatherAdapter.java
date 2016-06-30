package com.zeuschan.littlefreshweather.prsentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;
import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.wrapper.CurWeatherInfoWrapper;
import com.zeuschan.littlefreshweather.prsentation.wrapper.LifeIndexWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxiong on 2016/6/12.
 */
public class CityWeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int VIEW_MAIN = 0;
    public static final int VIEW_FORECAST = 1;
    public static final int VIEW_CURRENT_WEATHER_INFO = 2;
    public static final int VIEW_LIFE_INDEX = 3;

    private WeatherEntity mWeatherEntity = null;
    private Context mContext = null;

    private List<CurWeatherInfoWrapper> mListWeatherInfo = new ArrayList<>();
    private List<LifeIndexWrapper> mListLifeIndex = new ArrayList<>();
    private List<WeatherEntity.Forecast> mListForecasts = new ArrayList<>();

    public CityWeatherAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setWeatherEntity(WeatherEntity mWeatherEntity) {
        mListLifeIndex.clear();
        mListWeatherInfo.clear();
        mListForecasts.clear();

        this.mWeatherEntity = mWeatherEntity;

        mListWeatherInfo.add(new CurWeatherInfoWrapper(mContext.getString(R.string.wind_dirction), mWeatherEntity.getWindDirection()));
        mListWeatherInfo.add(new CurWeatherInfoWrapper(mContext.getString(R.string.wind_scale), mWeatherEntity.getWindScale() + "级"));
        mListWeatherInfo.add(new CurWeatherInfoWrapper(mContext.getString(R.string.felt_temp), mWeatherEntity.getFeltTemperature() + "℃"));
        mListWeatherInfo.add(new CurWeatherInfoWrapper(mContext.getString(R.string.humidity), mWeatherEntity.getHumidity() + "%"));
        mListWeatherInfo.add(new CurWeatherInfoWrapper(mContext.getString(R.string.air_pressure), mWeatherEntity.getAirPressure() + "hpa"));

        mListForecasts.addAll(mWeatherEntity.getForecasts());

        mListLifeIndex.add(new LifeIndexWrapper(mContext.getString(R.string.dress_index), mWeatherEntity.getDressBrief(), mWeatherEntity.getDressDescription()));
        mListLifeIndex.add(new LifeIndexWrapper(mContext.getString(R.string.uv_index), mWeatherEntity.getUvBrief(), mWeatherEntity.getUvDescription()));
        mListLifeIndex.add(new LifeIndexWrapper(mContext.getString(R.string.carwash_index), mWeatherEntity.getCarWashBrief(), mWeatherEntity.getCarWashDescription()));
        mListLifeIndex.add(new LifeIndexWrapper(mContext.getString(R.string.travel_index), mWeatherEntity.getTravelBrief(), mWeatherEntity.getTravelDescription()));
        mListLifeIndex.add(new LifeIndexWrapper(mContext.getString(R.string.flu_index), mWeatherEntity.getFluBrief(), mWeatherEntity.getFluDescription()));
        mListLifeIndex.add(new LifeIndexWrapper(mContext.getString(R.string.sport_index), mWeatherEntity.getSportBrief(), mWeatherEntity.getSportDescription()));

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            return VIEW_MAIN;
        } else if (1 == position) {
            return VIEW_FORECAST;
        } else if (2 == position) {
            return VIEW_CURRENT_WEATHER_INFO;
        } else {
            return VIEW_LIFE_INDEX;
        }
    }

    @Override
    public int getItemCount() {
        return mWeatherEntity != null ? 4 : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (VIEW_MAIN == viewType) {
            return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_city_weather_main, parent, false));
        } else if (VIEW_FORECAST == viewType) {
            return new ForecastViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_city_weather_forecast, parent, false));
        } else if (VIEW_CURRENT_WEATHER_INFO == viewType) {
            return new CurWeatherInfoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_city_weather_cur_weather_info, parent, false));
        } else {
            return new LifeIndexViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_city_weather_life_index, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (null == mWeatherEntity)
            return;

        if (0 == position) {
            MainViewHolder mainViewHolder = (MainViewHolder)holder;
            mainViewHolder.tvAirQualityIndex.setText(mWeatherEntity.getAirQulityIndex());
            mainViewHolder.tvAirQualityType.setText("空气" + mWeatherEntity.getAirQulityType());
            mainViewHolder.tvCurTemp.setText(mWeatherEntity.getCurrentTemperature());
            mainViewHolder.tvUpdateTime.setText(mWeatherEntity.getDataUpdateTime() + " 发布");
            mainViewHolder.tvWeatherDesc.setText(mWeatherEntity.getWeatherDescription());
        } else if (1 == position) {
            ForecastViewHolder forecastViewHolder = (ForecastViewHolder)holder;
            forecastViewHolder.tvTitleName.setText(R.string.forecast_title);
            //forecastViewHolder.lvCityWeatherForecast.setAdapter(mForecastAdapter);

            int index = 0;
            for (WeatherEntity.Forecast forecast : mListForecasts) {
                ++index;
                switch (index) {
                    case 1: {
                        forecastViewHolder.tvDate1.setText(forecast.getDate());
                        forecastViewHolder.tvCentigrade1.setText(forecast.getMinTemperature() + " ~ " + forecast.getMaxTemperature() + "℃");
                        forecastViewHolder.tvDesc1.setText(forecast.getWeatherDescriptionDaytime());
                    } break;
                    case 2: {
                        forecastViewHolder.tvDate2.setText(forecast.getDate());
                        forecastViewHolder.tvCentigrade2.setText(forecast.getMinTemperature() + " ~ " + forecast.getMaxTemperature() + "℃");
                        forecastViewHolder.tvDesc2.setText(forecast.getWeatherDescriptionDaytime());
                    } break;
                    case 3: {
                        forecastViewHolder.tvDate3.setText(forecast.getDate());
                        forecastViewHolder.tvCentigrade3.setText(forecast.getMinTemperature() + " ~ " + forecast.getMaxTemperature() + "℃");
                        forecastViewHolder.tvDesc3.setText(forecast.getWeatherDescriptionDaytime());
                    } break;
                    case 4: {
                        forecastViewHolder.tvDate4.setText(forecast.getDate());
                        forecastViewHolder.tvCentigrade4.setText(forecast.getMinTemperature() + " ~ " + forecast.getMaxTemperature() + "℃");
                        forecastViewHolder.tvDesc4.setText(forecast.getWeatherDescriptionDaytime());
                    } break;
                    case 5: {
                        forecastViewHolder.tvDate5.setText(forecast.getDate());
                        forecastViewHolder.tvCentigrade5.setText(forecast.getMinTemperature() + " ~ " + forecast.getMaxTemperature() + "℃");
                        forecastViewHolder.tvDesc5.setText(forecast.getWeatherDescriptionDaytime());
                    } break;
                    case 6: {
                        forecastViewHolder.tvDate6.setText(forecast.getDate());
                        forecastViewHolder.tvCentigrade6.setText(forecast.getMinTemperature() + " ~ " + forecast.getMaxTemperature() + "℃");
                        forecastViewHolder.tvDesc6.setText(forecast.getWeatherDescriptionDaytime());
                    } break;
                    case 7: {
                        forecastViewHolder.tvDate7.setText(forecast.getDate());
                        forecastViewHolder.tvCentigrade7.setText(forecast.getMinTemperature() + " ~ " + forecast.getMaxTemperature() + "℃");
                        forecastViewHolder.tvDesc7.setText(forecast.getWeatherDescriptionDaytime());
                    } break;
                }
            }

        } else if (2 == position) {
            CurWeatherInfoViewHolder curWeatherInfoViewHolder = (CurWeatherInfoViewHolder)holder;
            curWeatherInfoViewHolder.tvTitleName.setText(R.string.current_weather_info);
            //curWeatherInfoViewHolder.gvCurWeatherInfo.setAdapter(mCurWeatherInfoAdapter);

            int index = 0;
            for (CurWeatherInfoWrapper info: mListWeatherInfo) {
                ++index;
                switch (index) {
                    case 1: {
                        curWeatherInfoViewHolder.tvName1.setText(info.getWeatherInfoName());
                        curWeatherInfoViewHolder.tvValue1.setText(info.getWeatherInfoValue());
                    } break;
                    case 2: {
                        curWeatherInfoViewHolder.tvName2.setText(info.getWeatherInfoName());
                        curWeatherInfoViewHolder.tvValue2.setText(info.getWeatherInfoValue());
                    } break;
                    case 3: {
                        curWeatherInfoViewHolder.tvName3.setText(info.getWeatherInfoName());
                        curWeatherInfoViewHolder.tvValue3.setText(info.getWeatherInfoValue());
                    } break;
                    case 4: {
                        curWeatherInfoViewHolder.tvName4.setText(info.getWeatherInfoName());
                        curWeatherInfoViewHolder.tvValue4.setText(info.getWeatherInfoValue());
                    } break;
                    case 5: {
                        curWeatherInfoViewHolder.tvName5.setText(info.getWeatherInfoName());
                        curWeatherInfoViewHolder.tvValue5.setText(info.getWeatherInfoValue());
                    } break;
                }
            }
        } else {
            LifeIndexViewHolder lifeIndexViewHolder = (LifeIndexViewHolder)holder;
            lifeIndexViewHolder.tvTitleName.setText(R.string.life_index);
            //lifeIndexViewHolder.lvLifeIndex.setAdapter(mLifeIndexAdapter);

            int index = 0;
            for (LifeIndexWrapper lifeIndex : mListLifeIndex) {
                ++index;
                switch (index) {
                    case 1: {
                        lifeIndexViewHolder.tvName1.setText(lifeIndex.getLifeIndexName());
                        lifeIndexViewHolder.tvBrief1.setText(lifeIndex.getLifeIndexBrief());
                        lifeIndexViewHolder.tvDesc1.setText(lifeIndex.getLifeIndexDesc());
                    } break;
                    case 2: {
                        lifeIndexViewHolder.tvName2.setText(lifeIndex.getLifeIndexName());
                        lifeIndexViewHolder.tvBrief2.setText(lifeIndex.getLifeIndexBrief());
                        lifeIndexViewHolder.tvDesc2.setText(lifeIndex.getLifeIndexDesc());
                    } break;
                    case 3: {
                        lifeIndexViewHolder.tvName3.setText(lifeIndex.getLifeIndexName());
                        lifeIndexViewHolder.tvBrief3.setText(lifeIndex.getLifeIndexBrief());
                        lifeIndexViewHolder.tvDesc3.setText(lifeIndex.getLifeIndexDesc());
                    } break;
                    case 4: {
                        lifeIndexViewHolder.tvName4.setText(lifeIndex.getLifeIndexName());
                        lifeIndexViewHolder.tvBrief4.setText(lifeIndex.getLifeIndexBrief());
                        lifeIndexViewHolder.tvDesc4.setText(lifeIndex.getLifeIndexDesc());
                    } break;
                    case 5: {
                        lifeIndexViewHolder.tvName5.setText(lifeIndex.getLifeIndexName());
                        lifeIndexViewHolder.tvBrief5.setText(lifeIndex.getLifeIndexBrief());
                        lifeIndexViewHolder.tvDesc5.setText(lifeIndex.getLifeIndexDesc());
                    } break;
                    case 6: {
                        lifeIndexViewHolder.tvName6.setText(lifeIndex.getLifeIndexName());
                        lifeIndexViewHolder.tvBrief6.setText(lifeIndex.getLifeIndexBrief());
                        lifeIndexViewHolder.tvDesc6.setText(lifeIndex.getLifeIndexDesc());
                    } break;
                }
            }
        }
    }

    public static class LifeIndexViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_cv_title_name) TextView tvTitleName;
        //@BindView(R.id.lv_city_weather_life_index) ListView lvLifeIndex;
        @BindView(R.id.tv_city_weather_life_index_name1) TextView tvName1;
        @BindView(R.id.tv_city_weather_life_index_brief1) TextView tvBrief1;
        @BindView(R.id.tv_city_weather_life_index_desc1) TextView tvDesc1;
        @BindView(R.id.tv_city_weather_life_index_name2) TextView tvName2;
        @BindView(R.id.tv_city_weather_life_index_brief2) TextView tvBrief2;
        @BindView(R.id.tv_city_weather_life_index_desc2) TextView tvDesc2;
        @BindView(R.id.tv_city_weather_life_index_name3) TextView tvName3;
        @BindView(R.id.tv_city_weather_life_index_brief3) TextView tvBrief3;
        @BindView(R.id.tv_city_weather_life_index_desc3) TextView tvDesc3;
        @BindView(R.id.tv_city_weather_life_index_name4) TextView tvName4;
        @BindView(R.id.tv_city_weather_life_index_brief4) TextView tvBrief4;
        @BindView(R.id.tv_city_weather_life_index_desc4) TextView tvDesc4;
        @BindView(R.id.tv_city_weather_life_index_name5) TextView tvName5;
        @BindView(R.id.tv_city_weather_life_index_brief5) TextView tvBrief5;
        @BindView(R.id.tv_city_weather_life_index_desc5) TextView tvDesc5;
        @BindView(R.id.tv_city_weather_life_index_name6) TextView tvName6;
        @BindView(R.id.tv_city_weather_life_index_brief6) TextView tvBrief6;
        @BindView(R.id.tv_city_weather_life_index_desc6) TextView tvDesc6;

        public LifeIndexViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class CurWeatherInfoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_cv_title_name) TextView tvTitleName;
        //@BindView(R.id.gv_city_weather_cur_weather_info) GridView gvCurWeatherInfo;
        @BindView(R.id.tv_city_weather_cur_weather_info_item_name1) TextView tvName1;
        @BindView(R.id.tv_city_weather_cur_weather_info_item_value1) TextView tvValue1;
        @BindView(R.id.tv_city_weather_cur_weather_info_item_name2) TextView tvName2;
        @BindView(R.id.tv_city_weather_cur_weather_info_item_value2) TextView tvValue2;
        @BindView(R.id.tv_city_weather_cur_weather_info_item_name3) TextView tvName3;
        @BindView(R.id.tv_city_weather_cur_weather_info_item_value3) TextView tvValue3;
        @BindView(R.id.tv_city_weather_cur_weather_info_item_name4) TextView tvName4;
        @BindView(R.id.tv_city_weather_cur_weather_info_item_value4) TextView tvValue4;
        @BindView(R.id.tv_city_weather_cur_weather_info_item_name5) TextView tvName5;
        @BindView(R.id.tv_city_weather_cur_weather_info_item_value5) TextView tvValue5;

        public CurWeatherInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ForecastViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_cv_title_name) TextView tvTitleName;
        //@BindView(R.id.lv_city_weather_forecast) ListView lvCityWeatherForecast;
        @BindView(R.id.tv_city_weather_forecast_item_date1) TextView tvDate1;
        @BindView(R.id.tv_city_weather_forecast_item_centigrade1) TextView tvCentigrade1;
        @BindView(R.id.tv_city_weather_forecast_item_weather_desc1) TextView tvDesc1;
        @BindView(R.id.tv_city_weather_forecast_item_date2) TextView tvDate2;
        @BindView(R.id.tv_city_weather_forecast_item_centigrade2) TextView tvCentigrade2;
        @BindView(R.id.tv_city_weather_forecast_item_weather_desc2) TextView tvDesc2;
        @BindView(R.id.tv_city_weather_forecast_item_date3) TextView tvDate3;
        @BindView(R.id.tv_city_weather_forecast_item_centigrade3) TextView tvCentigrade3;
        @BindView(R.id.tv_city_weather_forecast_item_weather_desc3) TextView tvDesc3;
        @BindView(R.id.tv_city_weather_forecast_item_date4) TextView tvDate4;
        @BindView(R.id.tv_city_weather_forecast_item_centigrade4) TextView tvCentigrade4;
        @BindView(R.id.tv_city_weather_forecast_item_weather_desc4) TextView tvDesc4;
        @BindView(R.id.tv_city_weather_forecast_item_date5) TextView tvDate5;
        @BindView(R.id.tv_city_weather_forecast_item_centigrade5) TextView tvCentigrade5;
        @BindView(R.id.tv_city_weather_forecast_item_weather_desc5) TextView tvDesc5;
        @BindView(R.id.tv_city_weather_forecast_item_date6) TextView tvDate6;
        @BindView(R.id.tv_city_weather_forecast_item_centigrade6) TextView tvCentigrade6;
        @BindView(R.id.tv_city_weather_forecast_item_weather_desc6) TextView tvDesc6;
        @BindView(R.id.tv_city_weather_forecast_item_date7) TextView tvDate7;
        @BindView(R.id.tv_city_weather_forecast_item_centigrade7) TextView tvCentigrade7;
        @BindView(R.id.tv_city_weather_forecast_item_weather_desc7) TextView tvDesc7;

        public ForecastViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_city_weather_main_air_quality_index) TextView tvAirQualityIndex;
        @BindView(R.id.tv_city_weather_main_air_quality_type) TextView tvAirQualityType;
        @BindView(R.id.tv_city_weather_main_weather_desc) TextView tvWeatherDesc;
        @BindView(R.id.tv_city_weather_main_cur_temp) TextView tvCurTemp;
        @BindView(R.id.tv_city_weather_main_update_time) TextView tvUpdateTime;

        public MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}