package com.example.administrator.demo_dianping;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.demo_dianping.entity.Category;
import com.example.administrator.demo_dianping.utils.DaoCategory;
import com.example.administrator.demo_dianping.utils.MyUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 在首页中, 点击"全部"图标跳转到此Activity
 */

public class AllCategoryActivity extends Activity {
    @ViewInject(R.id.home_nav_all_category)
    private ListView listView;
    @ViewInject(R.id.home_nav_all_back)
    private ImageView backToHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_index_nav_all);
        x.view().inject(this);
        listView.setAdapter(new MyAdapter());
        //执行异步任务
        new CatagoryDataTask().execute();
    }

    /**使用异步任务获取信息, 源码中使用http, 这里从本地获取, 模拟http使用异步任务*/
    public class CatagoryDataTask extends AsyncTask<Void, Void, Void> {

        //后台线程中运行(耗时), 在这里从网络获取数据, 这里直接获取本地数据
        @Override
        protected Void doInBackground(Void... params) {
            //把表中的数据放入数组中
            getCategroyData();
            return null;
        }
        //在UI线程中执行, 把从doInBackgound中获取的数据设置到UI中去
        @Override
        protected void onPostExecute(Void aVoid) {
            MyAdapter adapter = new MyAdapter();
            listView.setAdapter(adapter);
        }
    }

    /**解析category表中的数据*/
    public void getCategroyData(){
        List<Category> datas = new DaoCategory(this).queryCategory();
        for (Category category: datas
                ) {
            //让position等于id字段
            int position = category.getCategoryId();
            //将集合中的category number放置如数组
            MyUtils.allCategoryNumber[position -1] = category.getCateNumber();
        }
    }

    public class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return MyUtils.allCategrayImages.length;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_index_nav_all_item, null);
                x.view().inject(holder, convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.imageView.setImageResource(MyUtils.allCategrayImages[position]);
            holder.textDesc.setText(MyUtils.allCategory[position]);
            holder.textNumber.setText(MyUtils.allCategoryNumber[position] + "");
            return convertView;
        }
    }

    public class ViewHolder{
        @ViewInject(R.id.home_nav_all_item_image)
        public ImageView imageView;
        @ViewInject(R.id.home_nav_all_item_desc)
        public TextView textDesc;
        @ViewInject(R.id.home_nav_all_item_number)
        public TextView textNumber;
    }

    //点击返回
    @Event(value = R.id.home_nav_all_back, type = View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.home_nav_all_back:
                /**关闭当前Activity, 并且返回上一个activity*/
                finish();
                break;
            default:
                break;
        }
    }
}
