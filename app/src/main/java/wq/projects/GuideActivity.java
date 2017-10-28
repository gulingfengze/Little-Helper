package wq.projects;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import utils.DensityUtil;
import utils.MyConstants;
import utils.SpTools;

/**
 * 向导界面(采用ViewPager实现界面切换)
 */
public class GuideActivity extends AppCompatActivity {

    private ViewPager vp_guide;
    private LinearLayout ll_points;
    private View v_redPoint;
    private Button bt_start;
    private List<ImageView> guides;
    private MyAdapter adapter;
    private int disPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();//初始化界面
        initData();//初始化数据
        initEvent();//初始化组件事件

    }

    private void initEvent() {
                       /*监听布局完成，触发结果(点随界面滑动移动)*/
        v_redPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                v_redPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);//取消注册--界面变化而发生的回调方法
                System.out.println("distance of points:" + (ll_points.getChildAt(2).getLeft() - ll_points.getChildAt(1).getLeft()));//点移动的距离
                disPoints = (ll_points.getChildAt(2).getLeft() - ll_points.getChildAt(1).getLeft());//计算点与点之间的距离
            }
        });
                      /*ViewPager页面上开始按钮点击事件*/
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1.保存设置的状态
                SpTools.setBoolean(getApplicationContext(), MyConstants.ISSETUP, true);//保存设置完成的状态
                //2.进入主界面
                Intent main = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(main);//进入主界面
            }
        });
                       /*ViewPager页面滑动事件*/
        vp_guide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {// 回调方法,当viewpager滚动时的回调
                // position:当前停留的位置
                // positionOffset:滑动(偏移)的百分比
                // positionOffsetPixels：偏移的距离，滑动的像素
                float leftMargin = disPoints * (position + positionOffset);//计算红点的的左边距
                //设置红点的左边距
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v_redPoint.getLayoutParams();
                layoutParams.leftMargin = Math.round(leftMargin);
                v_redPoint.setLayoutParams(layoutParams);//重新设置布局

            }

            @Override
            public void onPageSelected(int position) {// 回调方法,当viewpager的某个页面选中时的回调
                //当ViewPager显示到第三个页面时候(最后一页)，显示Button按钮
                if (position == guides.size() - 1) {
                    bt_start.setVisibility(View.VISIBLE);
                } else {//不是最后一页，则隐藏Button按钮
                    bt_start.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {// 回调方法,当viewpager的滑动状态改变时的回调

                // * @see ViewPager#SCROLL_STATE_IDLE : 闲置状态
                // * @see ViewPager#SCROLL_STATE_DRAGGING :拖动状态
                // * @see ViewPager#SCROLL_STATE_SETTLING: 固定状态
            }
        });
    }

    private void initData() {
        int[] pics = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};//图片数据
        guides = new ArrayList<ImageView>();//定义ViewPager使用的容器
        for (int i = 0; i < pics.length; i++) {
              /*1.给集合添加ImageView*/
            ImageView iv = new ImageView(getApplicationContext());
            iv.setImageResource(pics[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            guides.add(iv);//添加界面数据


            /*2.给点的容器LinearLayout初始化添加灰色的点*/
            View point = new View(getApplicationContext());//创建View实例
            point.setBackgroundResource(R.drawable.gray_point);//给点设置背景(灰色)
            int dip = 10;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(getApplicationContext(), dip),
                    DensityUtil.dip2px(getApplicationContext(), dip));//设置点的大小(单位是px,不是dp)
            if (i != 0) {//过滤掉第一个点，否则显示的点数会比预设的点数（3个）多一个
                params.leftMargin = 10;//设置点与点之间的空隙(单位是px)
            }
//      else {
//                point.setBackgroundResource(R.drawable.red_point);//设置为红点
//            }
            point.setLayoutParams(params);//设置布局
            ll_points.addView(point, params);//将点添加到线性容器中
        }

        adapter = new MyAdapter();//创建ViewPager适配器实例
        vp_guide.setAdapter(adapter);//加载设置适配器

    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {// 返回的是页面的数量
            if (guides != null) {
                return guides.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {//过滤和缓存作用
            return view == object;//官方推荐写法
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {// 初始化item条目
            //第一个参数container其实就是我们的ViewPager
            View iv = guides.get(position);// 要加载的位置(获取View)
            vp_guide.addView(iv);// 添加要显示的View,也可以写成  container.addView(iv);

            return iv;//返回这个View
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {// 销毁item条目
            //第一个参数container其实就是我们的ViewPager
            View iv = guides.get(position);// 要加载的位置(获取View)
            vp_guide.removeView(iv);// 从ViewPager中移除,也可以写成  container.removeView(iv)；
        }
    }

    private void initView() {
        setContentView(R.layout.activity_guide);
        vp_guide = (ViewPager) findViewById(R.id.vp_guide_pages);//加载ViewPager
        ll_points = (LinearLayout) findViewById(R.id.ll_guide_points);//动态加载点的容器
        v_redPoint = findViewById(R.id.v_guide_redPoint);//加载红点
        bt_start = (Button) findViewById(R.id.bt_guide_start);//加载"开始"按钮
    }
}
