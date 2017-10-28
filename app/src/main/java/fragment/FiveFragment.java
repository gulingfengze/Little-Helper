package fragment;

import android.os.Bundle;
import android.view.View;

import base.BaseFragment;
import wq.projects.R;

/**
 * Created by Lenovo on 2016/8/8.
 * Description 第五个Fragment.
 */
public class FiveFragment extends BaseFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View initView() {
        View root=View.inflate(mainActivity, R.layout.fragment_five,null);
        return root;
    }


}
