package fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import base.BaseFragment;
import bean.TouTiao;
import utils.MyConstants;
import utils.SpTools;
import wq.projects.NewsDetailActivity;
import wq.projects.R;

/**
 * Created by Lenovo on 2016/8/8.
 * Description 第二个Fragment.
 */
public class TwoFragment extends BaseFragment {
    private static final String AGT = "TwoFragment";
    /*@ViewInject(R.id.lv)
        private ListView lv;// 显示列表新闻的组件*/
    private ListNewsAdapter listNewsAdapter;
    private Gson gson;
    private ListView mLv;
    private BitmapUtils bitmapUtils;
    private TouTiao touTiao;
    //新闻列表的数据
    private List<TouTiao.NewsCenterData_Data.NewData> listNews = new ArrayList<TouTiao.NewsCenterData_Data.NewData>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(AGT, "Test");
    }

    @Override
    protected View initView() {
        View root = View.inflate(mainActivity, R.layout.fragment_two, null);

        // ViewUtils.inject(this, root);//  xUtils 动态注入view(获取控件方式)
        return root;

    }

    @Override
    public void initData() {

          /*xUtils bitmag 组件*/
        bitmapUtils = new BitmapUtils(mainActivity);
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.ARGB_4444);
        mLv = (ListView) mainActivity.findViewById(R.id.lv);
        //新闻列表的适配器
        listNewsAdapter = new ListNewsAdapter();
        //设置新闻列表适配
        mLv.setAdapter(listNewsAdapter);

        //从本地获取数据
        String jsonCache = SpTools.getString(mainActivity, MyConstants.NEWSTOUTIAOURL, "");
        if (!TextUtils.isEmpty(jsonCache)) {
            TouTiao touTiao = parseJson(jsonCache);

        }
        getDataFromNet();//从网络获取数据
        super.initData();
    }

    private void getDataFromNet() {
        //httpUtils
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, MyConstants.NEWSTOUTIAOURL, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //请求数据成功
                String jsonData = responseInfo.result;
                //保存数据到本地
                SpTools.putString(mainActivity, MyConstants.NEWSTOUTIAOURL, jsonData);
                //解析数据
                touTiao = parseJson(jsonData);

                //处理数据
                processData(touTiao);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                //请求数据失败
            }
        });
    }

    private TouTiao parseJson(String jsonData) {

        //if (gson == null)
        gson = new Gson();
        touTiao = gson.fromJson(jsonData, TouTiao.class);
        //NewsCenterData newsCenterData= gson.fromJson(jsonData, new TypeToken<NewsCenterData>(){}.getType());
        // newsCenterData=gson.fromJson(jsonData, new TypeToken<NewsCenterData<>>().getType());

        return touTiao;

    }

    private void processData(TouTiao touTiao) {
        //新闻列表的数据
        setListViewNews(touTiao);
    }

    private void setListViewNews(TouTiao touTiao) {

        listNews = touTiao.result.data;
        //更新界面
        listNewsAdapter.notifyDataSetChanged();
    }

    @Override
    public void initEvent() {
                                   /*新闻列表点击事件---进入详情页面*/
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long uniquekey) {
                //获取点击当前新闻的链接
                TouTiao.NewsCenterData_Data.NewData newData = listNews.get(position);
                String url = newData.url;
                //获取新闻的 uniquekey  可以用于实现对已经度过的新闻做出标记
                String key = newData.uniquekey;

                       /*跳转到新闻页面显示新闻*/
                Intent newsActivity = new Intent(mainActivity, NewsDetailActivity.class);
                newsActivity.putExtra("url", url);
                mainActivity.startActivity(newsActivity);
            }
        });
        super.initEvent();
    }

    private class ListNewsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listNews.size();
        }


        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            //return 0;
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mainActivity, R.layout.news_listview_item2, null);
                holder = new ViewHolder();
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                holder.tv_solid = (TextView) convertView.findViewById(R.id.tv_solid);
                holder.tv_source = (TextView) convertView.findViewById(R.id.tv_source);
                holder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //设置数据!!!!
            TouTiao.NewsCenterData_Data.NewData newData = listNews.get(position);

            holder.tv_title.setText(newData.title);
            holder.tv_time.setText(newData.date);
            holder.tv_source.setText(newData.author_name);
            bitmapUtils.display(holder.iv_pic, newData.thumbnail_pic_s);
            return convertView;
        }
    }

    private class ViewHolder {
        ImageView iv_pic;
        TextView tv_title;
        TextView tv_time;
        TextView tv_solid;
        TextView tv_source;
    }
}
