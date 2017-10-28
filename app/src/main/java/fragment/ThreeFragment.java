package fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import base.BaseFragment;
import wq.projects.R;
import wq.projects.WeatherActivity;

/**
 * Created by Lenovo on 2016/8/8.
 * Description 第三个Fragment.
 */
public class ThreeFragment extends BaseFragment {
    //获取
    private Button btn_get;
    //城市
    private EditText et_city;
    //图片
    private ImageView today_img, tomorrow_img, arter_img;
    //日期
    private TextView today_date, tomorrow_date, after_date;
    //天气
    private TextView today_weather, tomorrow_weather, after_weather;
    //温度
    private TextView today_tmp, tomorrow_tmp, after_tmp;
    // 日期
    private List<String> dateList = new ArrayList<String>();
    // 天气
    private List<String> weatherList = new ArrayList<String>();
    // 温度
    private List<String> TmpList = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        today_img = (ImageView) mainActivity.findViewById(R.id.today_img);
        tomorrow_img = (ImageView) mainActivity.findViewById(R.id.tomorrow_img);
        arter_img = (ImageView) mainActivity.findViewById(R.id.arter_img);

        today_date = (TextView) mainActivity.findViewById(R.id.today_date);
        today_weather = (TextView) mainActivity.findViewById(R.id.today_weather);
        today_tmp = (TextView) mainActivity.findViewById(R.id.today_tmp);

        tomorrow_date = (TextView) mainActivity.findViewById(R.id.tomorrow_date);
        tomorrow_weather = (TextView) mainActivity.findViewById(R.id.tomorrow_weather);
        tomorrow_tmp = (TextView) mainActivity.findViewById(R.id.tomorrow_tmp);

        after_date = (TextView) mainActivity.findViewById(R.id.after_date);
        after_weather = (TextView) mainActivity.findViewById(R.id.after_weather);
        after_tmp = (TextView) mainActivity.findViewById(R.id.after_tmp);

        et_city = (EditText) mainActivity.findViewById(R.id.et_city);
        btn_get = (Button) mainActivity.findViewById(R.id.btn_get);
        super.initData();
    }

    @Override
    public void initEvent() {
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(et_city.getText().toString())) {

                    getWeather(et_city.getText().toString());
                }else {
                    Toast.makeText(mainActivity, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected View initView() {
        View root=View.inflate(mainActivity, R.layout.fragment_three,null);

        return root;
    }
    /**
     * 获取城市天气
     * @param city
     */
    private void getWeather(String city) {
        String url="https://free-api.heweather.com/s6/weather/forecast?location="+ city +"&key=99c1d39ab7d048e3978a8a9dadb50e63";
        RequestQueue queue= Volley.newRequestQueue(mainActivity);
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            // 成功
            @Override
            public void onResponse(String json) {
                Log.i("json", json);
                Volley_Json(json);
            }
        }, new Response.ErrorListener() {
            // 失败
            @Override
            public void onErrorResponse(VolleyError errorLog) {
                Log.e("Error", errorLog.toString());
            }
        });
        queue.add(request);
    }

    /**
     * 解析json数据
     * @param json
     */
    private void Volley_Json(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jArray = jsonObject
                    //.getJSONArray("HeWeather data service 3.0");
                    .getJSONArray("HeWeather6");
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jb1 = (JSONObject) jArray.get(i);
                JSONArray jr1 = jb1.getJSONArray("daily_forecast");
                for (int j = 0; j < jr1.length(); j++) {
                    JSONObject jb2 = jr1.getJSONObject(j);
                    String max=jb2.getString("tmp_max");
                    String min=jb2.getString("tmp_min");
                    String date=jb2.getString("date");
                    String cond=jb2.getString("cond_txt_n");
                    dateList.add(date);
                    weatherList.add(cond);
                    TmpList.add(min + "-"
                            + max);
                }
            }

            // 设置参数
            today_date.setText(dateList.get(0));
            today_weather.setText(weatherList.get(0));
            today_tmp.setText(TmpList.get(0));

            tomorrow_date.setText(dateList.get(1));
            tomorrow_weather.setText(weatherList.get(1));
            tomorrow_tmp.setText(TmpList.get(1));

            after_date.setText(dateList.get(2));
            after_weather.setText(weatherList.get(2));
            after_tmp.setText(TmpList.get(2));

            //设置图片
            if (weatherList.get(0).equals("晴")) {
                today_img.setImageResource(R.drawable.sun);
            } else if (weatherList.get(0).equals("多云")) {
                today_img.setImageResource(R.drawable.cloudy);
            } else {
                today_img.setImageResource(R.drawable.rain);
            }

            if (weatherList.get(1).equals("晴")) {
                tomorrow_img.setImageResource(R.drawable.sun);
            } else if (weatherList.get(1).equals("多云")) {
                tomorrow_img.setImageResource(R.drawable.cloudy);
            } else {
                tomorrow_img.setImageResource(R.drawable.rain);
            }

            if (weatherList.get(2).equals("晴")) {
                arter_img.setImageResource(R.drawable.sun);
            } else if (weatherList.get(2).equals("多云")) {
                arter_img.setImageResource(R.drawable.cloudy);
            } else {
                arter_img.setImageResource(R.drawable.rain);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //清空数据
        dateList.clear();
        weatherList.clear();
        TmpList.clear();
    }

}
