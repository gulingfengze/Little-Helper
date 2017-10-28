package newspage;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import bean.NewsCenterData;
import bean.TPINewsData;
import utils.BitmapCacheUtils;
import utils.MyConstants;
import utils.SpTools;
import wq.projects.MainActivity;
import wq.projects.R;

/**
 * Created by Lenovo on 2016/8/11.
 * Description 新闻的页签的对应内容部分的界面.
 */
public class NewsPage {
    private final BitmapUtils bitmapUtils;
    @ViewInject(R.id.lv)
    private ListView lv_listnews;// 显示列表新闻的组件
    private MainActivity mainActivity;
    private Gson gson;
    private View root;
    private NewsCenterData.NewsData.ViewTagData viewTagData;//每个页签对应的数据


    //新闻列表的数据
    private List<TPINewsData.TPINewsData_Data.TPINewsData_Data_ListNewsData> listNews = new ArrayList<TPINewsData.TPINewsData_Data.TPINewsData_Data_ListNewsData>();
    private ListNewsAdapter listNewsAdapter;
    private final BitmapCacheUtils mBitmapCacheUtils;

    public NewsPage(MainActivity mainActivity, NewsCenterData.NewsData.ViewTagData viewTagData) {
        this.mainActivity = mainActivity;
        this.viewTagData = viewTagData;
        gson = new Gson();
        /*xUtils bitmag 组件*/
        bitmapUtils = new BitmapUtils(mainActivity);
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.ARGB_4444);

        mBitmapCacheUtils = new BitmapCacheUtils(mainActivity);


        initView();
        initData();
        initEvent();
    }

    private void initEvent() {

    }

    private void initData() {
        //新闻列表的适配器
        listNewsAdapter = new ListNewsAdapter();
        //设置新闻列表适配
        lv_listnews.setAdapter(listNewsAdapter);
        //从本地获取数据
        String jsonCache = SpTools.getString(mainActivity, viewTagData.url, "");
        if (!TextUtils.isEmpty(jsonCache)) {
            TPINewsData newsData = parseJson(jsonCache);
        }
        getDataFromNet();//从网络获取数据
    }

    private void initView() {
        root = View.inflate(mainActivity, R.layout.news_page, null);//新闻叶签对应页面根布局

        ViewUtils.inject(this, root);//注入方式添加控件
    }

    public View getRootView() {

        return root;
    }

    public void getDataFromNet() {
        //httpUtils
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, MyConstants.SERVERURL + viewTagData.url, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //请求数据成功
                String jsonData = responseInfo.result;
                //保存数据到本地
                SpTools.putString(mainActivity, viewTagData.url, jsonData);
                //解析数据
                TPINewsData newsData = parseJson(jsonData);
                //处理数据
                processData(newsData);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                //请求数据失败
            }
        });
    }
    private void processData(TPINewsData newsData) {
        //新闻列表的数据
        setListViewNews(newsData);
    }
    private void setListViewNews(TPINewsData newsData) {
        listNews = newsData.data.news;
        //更新界面
        listNewsAdapter.notifyDataSetChanged();
    }
    private TPINewsData parseJson(String jsonData) {
        //解析json数据
        TPINewsData tpiNewsData = gson.fromJson(jsonData, TPINewsData.class);
        return tpiNewsData;
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
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mainActivity, R.layout.news_listview_item1, null);
                holder = new ViewHolder();
                holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_tpi_news_listview_item_icon);
                holder.iv_newspic = (ImageView) convertView.findViewById(R.id.iv_tpi_news_listview_item_pic);
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_tpi_news_listview_item_title);
                holder.tv_time = (TextView) convertView.findViewById(R.id.tv_tpi_news_listview_item_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //设置数据!!!!
            TPINewsData.TPINewsData_Data.TPINewsData_Data_ListNewsData tpiNewsData_Data_ListNewsData = listNews.get(position);

            //设置标题
            holder.tv_title.setText(tpiNewsData_Data_ListNewsData.title);
            //设置时间
            holder.tv_time.setText(tpiNewsData_Data_ListNewsData.pubdate);
            //设置图片----在这里实现图片三级缓存(使用BitmapCacheUtils工具类)
           // bitmapUtils.display(holder.iv_newspic, tpiNewsData_Data_ListNewsData.listimage);
            mBitmapCacheUtils.display(holder.iv_newspic, tpiNewsData_Data_ListNewsData.listimage);

            return convertView;
        }
    }

    private class ViewHolder {
        ImageView iv_newspic;
        TextView tv_title;
        TextView tv_time;
        ImageView iv_icon;
    }
}
