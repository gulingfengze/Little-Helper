package wq.projects;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import utils.SharedAPPUtils;

/**
 * Created by Lenovo on 2016/9/9.
 * Description 新闻详情界面.
 */
public class NewsDetailActivity extends Activity {
    private ImageButton ib_back;
    private ImageButton ib_share;
    private WebView wv_news;
    private ProgressBar pb_loadingnews;
    private WebSettings wv_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
         /*1.给WebView添加监听事件，当页面新闻信息加载完成时候，进度条消失*/
        wv_news.setWebViewClient(new WebViewClient() {
            /* (non-Javadoc)
             * @see android.webkit.WebViewClient#onPageFinished(android.webkit.WebView, java.lang.String)
             * 页面加载完成的事件处理
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                //隐藏进度条
                pb_loadingnews.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
          /* 2.给返回键和分享键添加事件*/
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//关闭当前即可
            }
        });
        ib_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedAPPUtils.showShare(getApplicationContext());
            }
        });
    }


    private void initData() {
        //获取数据
        String url = getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(getApplicationContext(), "连接失败。。。", Toast.LENGTH_SHORT).show();
        } else {
            //有新闻
            //加载新闻
            wv_news.loadUrl(url);
        }
    }

    private void initView() {
        setContentView(R.layout.news_detail);

        //返回的按钮
        ib_back = (ImageButton) findViewById(R.id.ib_base_content_back);

        //分享
        ib_share = (ImageButton) findViewById(R.id.ib_base_content_share);


        //显示新闻
        wv_news = (WebView) findViewById(R.id.wv_newsDetail);
        //控制WebView的显示设置
        wv_setting = wv_news.getSettings();
        wv_setting.setJavaScriptEnabled(true);//可以去编译javaScript脚本


        //加载新闻的进度
        pb_loadingnews = (ProgressBar) findViewById(R.id.pb_loading);
    }
}
