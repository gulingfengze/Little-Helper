package wq.projects;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import utils.MyConstants;
import utils.SpTools;

/**
 * 描述：Splash界面   (1.原型开发模式  2.MVC模式)
 */
public class SplashActivity extends AppCompatActivity {
    private ImageView mIvSplash;
    private AnimationSet as;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();//初始化界面
        startAnimation();//开始动画
        initEvent();//初始化事件
    }

    private void initEvent() {
        /*播放动画之后进入下一个界面
         * 分析:需要监听动画播放完事件，再判断进入向导界面还是主界面
         */
                        /*监听动画播放完的事件*/
        as.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                   /*判断进入向导界面还是主界面*/
                if (SpTools.getBoolean(getApplicationContext(), MyConstants.ISSETUP, false)) { //true,设置过,直接进入主界面
                    Intent main = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(main);//启动主界面
                } else {//false 没设置过,进入设置向导界面
                    Intent guide = new Intent(SplashActivity.this, GuideActivity.class);
                    startActivity(guide);//启动设置向导界面
                }
                finish();//关闭自己
            }
        });


    }

    /**
     * 开始动画：包括旋转、渐变、缩放
     */
    private void startAnimation() {
        as = new AnimationSet(false);
        /*1.旋转动画--需要设置锚点*/
        RotateAnimation ra = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(2000);//设置动画播放时间为2S
        ra.setFillAfter(true);//动画播放完之后停留在当前状态
        as.addAnimation(ra);//添加到动画集
        /*2.渐变动画*/
        AlphaAnimation aa = new AlphaAnimation(0, 1);//有完全透明到不透明
        aa.setDuration(2000);//设置动画播放时间为2S
        aa.setFillAfter(true);//动画播放完之后停留在当前状态
        as.addAnimation(aa);//添加到动画集
        /*3.缩放动画/比例动画*/
        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(2000);//设置动画播放时间为2S
        sa.setFillAfter(true);//动画播放完之后停留在当前状态
        as.addAnimation(sa);//添加到动画集

        mIvSplash.startAnimation(as);//播放动画

        /*播放动画之后进入下一个界面(initEvent())
         * 分析:首先需要监听动画播放完事件，之后再判断进入向导界面还是主界面
         */

    }

    private void initView() {
        setContentView(R.layout.activity_splash);
        mIvSplash = (ImageView) findViewById(R.id.iv_splash_view);
    }
}
