package com.example.administrator.demo_dianping;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.demo_dianping.entity.Category;
import com.example.administrator.demo_dianping.entity.City;
import com.example.administrator.demo_dianping.utils.DaoCategory;
import com.example.administrator.demo_dianping.utils.DaoCity;
import com.example.administrator.demo_dianping.utils.MyUtils;
import com.example.administrator.demo_dianping.view.SideBar;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页选择城市的Activity - ListView
 * <p>
 * 继承的 OnTouchingLetterChangedListener 实在 {@link SideBar } 中自定义的的接口
 */

public class CityActivity extends Activity implements SideBar.OnTouchingLetterChangedListener {
    @ViewInject(R.id.city_list)
    private ListView listDatas;  //城市列表选择view
    private List<City> cityList = new ArrayList<>();
    private DaoCity daoCity;
    @ViewInject(R.id.city_side_bar)
    private SideBar sideBar; //字母索引

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_city_list);
        //初始化View
        x.view().inject(this);
        //给listView添加头部信息
        View view = LayoutInflater.from(this).inflate(R.layout.home_city_search, null);
        listDatas.addHeaderView(view);
        //从city表中取出数据, 放入list集合中
        daoCity = new DaoCity(this);
        cityList.addAll(0, daoCity.queryCity());
        //打印集合长度
        Log.e("Tag-CityActivity", "city表中中城市的个数是:" + cityList.size());
        listDatas.setAdapter(new MyAdapter(cityList));
        //给自定义View SideBar设置自定义的touch监听
        sideBar.setOnTouchingLetterChangedListener(this);
    }

    /**
     * 设置itemClickListener点击设置城市名
     * 获取显示名字的TextView对象city_list_item_name
     * 从TextView对象中getText()取出城市名
     * 使用putExtra将城市名存入intent
     * setResult将城市名字返会上一个Activity
     */
    @Event(value = R.id.city_list, type = AdapterView.OnItemClickListener.class)
    private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        TextView textView = (TextView) view.findViewById(R.id.city_list_item_name);
        intent.putExtra("cityName", textView.getText().toString());
        setResult(RESULT_OK, intent);
        finish(); //关闭activity
    }


    private StringBuffer buffer = new StringBuffer(); //用来第一次保存首字母的索引
    private List<String> firstList = new ArrayList<>(); //用来保存索引对象的城市名称

    /**
     * 自定义OnTouchingLetterChangedListener接口中的方法
     *
     * @param s 选中的的字母
     */
    @Override
    public void onTouchingLetterChanged(String s) {
        //找到ListerView中显示的索引位置
        //设置当前被选中的item, 在touch模式下item实际不会被选中,而是确定其位置
        listDatas.setSelection(findIndex(cityList, s));
    }

    //根据s找到s的位置
    public int findIndex(List<City> list, String s) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                City city = list.get(i);
                //根据city中的sortKey进行比较
                if (s.equals(city.getSortKey())) { //S == 当前city的sortKey
                    return i;  //返回符合条件的city索引
                }
            }
        } else {
            Toast.makeText(this, "暂无信息", Toast.LENGTH_SHORT).show();
        }
        return -1;
    }



    //listView的适配器
    class MyAdapter extends BaseAdapter {

        private List<City> listCityData;

        public MyAdapter() {
        }

        private MyAdapter(List<City> listCityDatas) {
            this.listCityData = listCityDatas;
        }

        @Override
        public int getCount() {
            return listCityData.size();
        }

        @Override
        public Object getItem(int position) {
            return listCityData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                //调用父布局ListView的context, 因为这个方法实际上是在设置itemView
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_city_list_item, null);
                //进行注解的注册 TODO 不太懂
                x.view().inject(holder, convertView);
                convertView.setTag(holder);

            } else {
                //注意这里要强转为holder
                holder = (ViewHolder) convertView.getTag();
            }
            //取出当前索引的city数据
            City city = listCityData.get(position);
            String sort = city.getSortKey();
            String name = city.getName();
            /** indexOf返回指定字符串的首字母的索引, -1表示当前字符串不存在
             *  此处的逻辑: 例如A开头的城市名, sort都是A, 所以String sort = city.getSortKey() 返回的都是A
             *             第一个返回的是A, 将A设置给buffer, 将第一个城市名存入firstList
             *             第二个返回的也是A, 所以此if不在执行, 继续执行下一个if
             *             如果当前firstList中包含了第一个城市, 则将其的keySort索引设置可见
             *             将其他的索引设置为不可见
             *             cityName的设置始终执行
             * */
            if (buffer.indexOf(sort) == -1) {
                buffer.append(sort); //添加当前索引位置的city对象城市首字母索引
                firstList.add(name); //将当前索引位置city对象的名字放入list
            }
            if (firstList.contains(name)) {
                holder.keySort.setText(sort);
                holder.keySort.setVisibility(View.VISIBLE);//包含对应的城市, 就让索引显示
            } else {
                holder.keySort.setVisibility(View.GONE); //否则将索引设置不可见
            }
            holder.cityName.setText(name); //设置城市名称
            return convertView;
        }

        /**
         * 创建Holder绑定itemView中的两个textView
         */
        class ViewHolder {
            @ViewInject(R.id.city_list_item_sort)
            private TextView keySort;
            @ViewInject(R.id.city_list_item_name)
            private TextView cityName;
        }
    }
}
