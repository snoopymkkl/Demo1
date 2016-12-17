package com.example.administrator.demo_dianping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;

/**
 * 欢迎页面后的导航页面
 * 3张图滑动(ViewPager), 点击按钮进入应用
 * 实现思路
 *      1. 使用ViewPager, 将三张图片填充入ViewPager, 设置PagerAdapter
 *      2. 在第三张图才显示Button的实现
 *              1. 在Layout中将Button visibility属性设置为gone
 *              2. initViewPager()方法中, 为ViewPager设置监听addOnPageChangeListener()
 *              3. onPageSelected()方法中, 监听滑动到第三张图片时, 将Button visibility设置为visible
 */

public class WelcomeGuideAct extends Activity {
    //使用注解绑定控件
    @ViewInject(R.id.welcome_guide_btn)
    private Button btn;
    @ViewInject(R.id.welcome_pager)
    private ViewPager pager;
    private List<View> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_guide);
        x.view().inject(this);
        initViewPager();
    }

    //跳转到MainActivity
    @Event(type = View.OnClickListener.class, value = R.id.welcome_guide_btn)
    private void click(View view) {
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        //关闭当前Activity
        finish();
    }

    //初始化ViewPager
    public void initViewPager() {
        //填充图片ImageView到List集合中
        list = new ArrayList<>();
        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.guide_01);
        list.add(iv);
        ImageView iv2 = new ImageView(this);
        iv2.setImageResource(R.drawable.guide_02);
        list.add(iv2);
        ImageView iv3 = new ImageView(this);
        iv3.setImageResource(R.drawable.guide_03);
        list.add(iv3);
        //设置适配器
        pager.setAdapter(new MyPagerAdapter());
        //监听viewpager的滑动效果
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //在第三张页卡被选中时, 显示Button按钮
            @Override
            public void onPageSelected(int position) {
                //如果是第三张页面
                if (position == 2) {
                    btn.setVisibility(View.VISIBLE);
                } else {
                    btn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //定义ViewPager的适配器
    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //初始化item实例的方法
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //拿到索引位置的ImageView
            container.addView(list.get(position));
            //以Object对象返回
            return list.get(position);
        }

        //销毁item的方法
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //销毁item
            container.removeView(list.get(position));
        }
    }
}
