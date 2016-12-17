package com.example.administrator.demo_dianping;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.demo_dianping.fragment.FragmentHome;
import com.example.administrator.demo_dianping.fragment.FragmentMy;
import com.example.administrator.demo_dianping.fragment.FragmentSearch;
import com.example.administrator.demo_dianping.fragment.FragmentTuan;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 继承FragmentActivity, 使用Fragment
 * 实现OnCheckedChangeListener接口, 监听RadioButton状态改变, 切换不同的选项卡
 */

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.main_bottom_tabs)
    private RadioGroup group;  //底部导航栏
    @ViewInject(R.id.main_home)
    private RadioButton main_home; //主页Button
    private FragmentManager fragmentManager; //管理Fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        x.view().inject(this);
        //初始化fragmentManager
        fragmentManager = getSupportFragmentManager();
        //设置默认选中首页Button
        main_home.setChecked(true);
        //给group中的四个Button设置监听--这里传入this是因为此类实现了OnCheckedChangeListener接口
        group.setOnCheckedChangeListener(this);
        //切换不同的Fragment
        changeFragment(new FragmentHome(), false);
    }
    /**
     * 切换Radio button时调用. When the selection is cleared, checkedId is -1.</p>
     *
     * @param group the group in which the checked radio button has changed
     * @param checkedId 新被选中的radio button id
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.main_home://首页
                changeFragment(new FragmentHome(), true); //设置为当前Fragment
                break;
            case R.id.main_my://我的
                changeFragment(new FragmentMy(), true);
                break;
            case R.id.main_search://搜索
                changeFragment(new FragmentSearch(), true);
                break;
            case R.id.main_tuan://团购
                changeFragment(new FragmentTuan(), true);
                break;
            default:
                break;
        }
    }

    /**
     * 通过FragmentTransaction切换Fragment界面
     *
     * @param fragment 需要切换到的fragment
     * @param isInit 是否是第一次加载
     */
    public void changeFragment(Fragment fragment, boolean isInit){
        //开启Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //将传入的fragment填充到帧布局main_content中去
        transaction.replace(R.id.main_content, fragment);
        if(!isInit){//如果不是第一次加载  TODO?????视频说解决重影效果???
            //将Fragment加入返回栈, 当被从栈内弹出时, 回复到之前的状态
            //参数为 可选的 返回栈名, 可以为null
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
