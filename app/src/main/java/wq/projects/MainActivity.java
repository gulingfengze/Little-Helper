package wq.projects;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

import fragment.FiveFragment;
import fragment.FourFragment;
import fragment.OneFragment;
import fragment.ThreeFragment;
import fragment.TwoFragment;
import utils.SharedAPPUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private OneFragment mOneFragment;
    private TwoFragment mTwoFragment;
    private ThreeFragment mThreeFragment;
    private FourFragment mFourFragment;
    private FiveFragment mFiveFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
               /*初始化侧滑和导航组件*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
           /*替换掉content_main.xml文件中的FrameLayout布局内容*/
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mOneFragment = new OneFragment();//默认显示第一个fragment
        transaction.add(R.id.fragment_container, mOneFragment);
        transaction.commit();
                       /*进行Fragment界面切换*/
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_content_radios);
                   /*设置默认的选中界面（按钮颜色状态）*/
        radioGroup.check(R.id.rb_main_content_one);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                // 五个单选按钮对应五个界面
                switch (i) {// 是哪个单选按钮点击的
                    case R.id.rb_main_content_one:// 界面1
                        if (mOneFragment == null) {
                            mOneFragment = new OneFragment();
                        }
                        transaction.replace(R.id.fragment_container, mOneFragment);
                        break;
                    case R.id.rb_main_content_two:// 界面2
                        if (mTwoFragment == null) {
                            mTwoFragment = new TwoFragment();
                        }
                        transaction.replace(R.id.fragment_container, mTwoFragment);
                        break;
                    case R.id.rb_main_content_three:// 界面3
                        if (mThreeFragment == null) {
                            mThreeFragment = new ThreeFragment();
                        }
                        transaction.replace(R.id.fragment_container, mThreeFragment);
                        break;
                    case R.id.rb_main_content_four:// 界面4
                        if (mFourFragment == null) {
                            mFourFragment = new FourFragment();
                        }
                        transaction.replace(R.id.fragment_container, mFourFragment);
                        break;
                    case R.id.rb_main_content_five:// 界面5
                        if (mFiveFragment == null) {
                            mFiveFragment = new FiveFragment();
                        }
                        transaction.replace(R.id.fragment_container, mFiveFragment);
                        break;
                    default:
                        break;
                }
                transaction.commit();
            }
        });
    }


    /*返回键*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*菜单按钮*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:

                break;
            case R.id.action_share://分享
                SharedAPPUtils.showShare(getApplication());
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /*导航菜单*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {//天气查询
           // Intent i=new Intent(MainActivity.this, WeatherActivity.class);
            //startActivity(i);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
