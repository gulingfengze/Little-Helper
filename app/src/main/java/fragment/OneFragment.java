package fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import base.BaseFragment;
import bean.NewsCenterData;
import newspage.NewsPage;
import utils.LogUtils;
import utils.MyConstants;
import utils.SpTools;
import wq.projects.R;

/**
 * Created by Lenovo on 2016/8/8.
 * Description 第一个Fragment.新闻中心内容界面(使用ViewPagerIndicator开源框架).
 */
public class OneFragment extends BaseFragment {
    @ViewInject(R.id.newcenter_vp)
    private ViewPager mViewPager;
    @ViewInject(R.id.newcenter_tpi)
    private TabPageIndicator tpi_newscenter;

    @OnClick(R.id.newcenter_ib_nextpage)
    public void next(View v) {//切换到下一个标签界面
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    private NewsCenterData newsCenterData;
    private Gson gson;
    private List<NewsCenterData.NewsData.ViewTagData> viewTagDatas = new ArrayList<NewsCenterData.NewsData.ViewTagData>(); // 页签的数据


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    protected View initView() {
        View root = View.inflate(mainActivity, R.layout.fragment_one, null);
        ViewUtils.inject(this, root);// xUtils 动态注入view(获取控件方式)
        return root;
    }

    @Override
    public void initData() {
/*-------------start--------------获取并解析新闻数据----------------------------*/

                       //获取本地数据（缓存机制）
        String jsonCache = SpTools.getString(mainActivity, MyConstants.NEWSURL, "");
        if (!TextUtils.isEmpty(jsonCache)) {
            pasrseJSONData(jsonCache);//从本地读取数据
        }

        // 1.获取网络数据--使用xUtils工具获取网络数据
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, MyConstants.NEWSURL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {//访问成功
                String jsonData = responseInfo.result;
                SpTools.putString(mainActivity, MyConstants.NEWSURL, jsonData); //将数据保存到本地（一份）
                // 2.解析数据
                pasrseJSONData(jsonData);
            }

            @Override
            public void onFailure(HttpException e, String s) {//访问失败
                LogUtils.d("OneFragment网络请求失败:" + e);
            }
        });
        /*-------------end--------------获取并解析新闻数据----------------------------*/


          /*设置ViewPager的适配器并将其和TabPagerIndicator进行关联*/
        mViewPager.setAdapter(new MyAdapter()); // 设置ViewPager的适配器
        tpi_newscenter.setViewPager(mViewPager);// 把ViewPager和TabPagerIndicator关联

        super.initData();
    }


    /**
     * 解析json数据
     *
     * @param jsonData 从网络获取到的json数据
     */

    private void pasrseJSONData(String jsonData) {
        if (gson == null)
            gson = new Gson();
        newsCenterData = gson.fromJson(jsonData, NewsCenterData.class);
       //newsCenterData = gson.fromJson(jsonData, new TypeToken<NewsCenterData>(){}.getType());

        LogUtils.d(newsCenterData.data.get(0).children.get(5).title);
        viewTagDatas = newsCenterData.data.get(0).children;//将解析出的数据（这里定为标签数据）传递给数据集！！！！

    }

    @Override
    public void initEvent() {
        //viewPagerIndicator标签点击事件
        tpi_newscenter.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        super.initEvent();
    }

/*------------------------ViewPager适配器---------------------------------*/

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return viewTagDatas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            /*要展示的内容：新闻内容的界面       viewTagDatas.get(position)---数据 */
            NewsPage newsPage=new NewsPage(mainActivity,viewTagDatas.get(position));//将新闻数据传到 NewsPage 中
            View rootView = newsPage.getRootView();//新闻内容页面获取布局
            container.addView(rootView);//将页面加载到该容器中用于显示
            return rootView;

           /* TextView tv = new TextView(mainActivity);
            tv.setText(viewTagDatas.get(position).title);
            //tv.setText(viewTagDatas.get(position).realtype);
            tv.setTextSize(25);
            tv.setGravity(Gravity.CENTER);
            container.addView(tv);
            return tv;*/
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            LogUtils.d("destroyItem" + position);
            container.removeView((View) object);

        }

        /**
         * 页签显示数据调用该方法
         *
         * @param position
         * @return
         */

        @Override
        public CharSequence getPageTitle(int position) {
            LogUtils.d(viewTagDatas.get(position).title);
            //获取页签的数据
            return viewTagDatas.get(position).title;
        }
    }
}
