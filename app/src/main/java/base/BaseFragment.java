package base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wq.projects.MainActivity;


/**
 * Created by Lenovo on 2016/8/9.
 * Description Fragment基类.
 */
public abstract class BaseFragment extends Fragment {
    protected MainActivity mainActivity;//上下文
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();//获取Fragment所在的Activity
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = initView();//View
        return root;
    }

    /**
     * 必须覆盖此方法来完成界面的显示
     *
     * @return
     */
    protected abstract View initView();


    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化事件和数据
        initData();//初始化数据
        initEvent();//初始化事件
    }

    /**
     * 子类覆盖此方法来完成数据的初始化
     */
    public void initData() {

    }

    /**
     * 子类覆盖此方法来完成事件的添加
     */
    public void initEvent() {

    }
}
