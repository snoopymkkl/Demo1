package com.example.administrator.demo_dianping.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.administrator.demo_dianping.AllCategoryActivity;
import com.example.administrator.demo_dianping.CityActivity;
import com.example.administrator.demo_dianping.R;
import com.example.administrator.demo_dianping.utils.DBHelperCity;
import com.example.administrator.demo_dianping.utils.DaoCategory;
import com.example.administrator.demo_dianping.utils.DaoCity;
import com.example.administrator.demo_dianping.utils.DaoGoods;
import com.example.administrator.demo_dianping.utils.MyUtils;
import com.example.administrator.demo_dianping.utils.SharedUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 首页的Fragment
 *    实现定位功能 - 使用高德SDK获取位置
 *       1. 实现AMapLocationListener接口, 覆写onLocationChanged()方法
 *       2. 声明AMapLocationClientOption, AMapLocationClient
 *       3. 在AMapLocationClientOption对象中设置定位服务的属性, 设置给Client
 *       4. 在覆写onLocationChanged()中获取定位数据, 传递给TextView topCity
 *       5. 在Fragment的onDestroy()中停止定位
 */
public class FragmentHome extends Fragment implements AMapLocationListener {
    @ViewInject(R.id.index_top_city)
    private TextView topCity; //当前城市
    private String cityName; //城市名称
    private LocationManager locationManager; //获取系统定位信息的管理类
    @ViewInject(R.id.home_nav_sort)
    private GridView navSort; //首页的窗格视图

    //声明高德定位的Client和ClientOption
    private AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mlocationClient;

    //创建Fragment的方法
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //将fragment_home.xml布局设置给Fragment
        View view = inflater.inflate(R.layout.fragment_home, null);
        //此处的控件初始化操作传入两个参数handler 与 当前 view
        x.view().inject(this, view);
        //从**定位数据中**获取数据并且显示
        topCity.setText(SharedUtils.getCityName(getActivity()));
        navSort.setAdapter(new NavAdapter());

        //初始化高德定位的Client和Option
        mlocationClient = new AMapLocationClient(getActivity());
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy); //高精度模式
        mLocationOption.setOnceLocation(true); //只定位一次
        mlocationClient.setLocationOption(mLocationOption); //option的选项设置给client
        mlocationClient.setLocationListener(this);//client设置监听
        checkGPSIsOpen(); //检查GPS模块 TODO 这个方法原本是放在onStart()中的, 手工选择城市后又返回定位的地址逻辑

        Log.e("Tag", "执行了FragmentHome类中的onCreateView()方法");
        return view;
    }
    /**网格视图的adapter*/
    public class NavAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return MyUtils.navsSort.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        //item对应的View的数据渲染
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            //在convertView为null的时候将布局转化成View
            if(convertView == null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_index_nav_item, null);
                x.view().inject(holder, convertView);
//                holder.imageView = (ImageView) convertView.findViewById(R.id.home_nav_item_image);
//                holder.textView = (TextView) convertView.findViewById(R.id.home_nav_item_desc);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.imageView.setImageResource(MyUtils.navsSortImages[position]);
            holder.textView.setText(MyUtils.navsSort[position]);
            //点击最后一个图标 "全部" 的时候跳转
            if(position == MyUtils.navsSortImages.length-1){
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), AllCategoryActivity.class);
                        startActivity(intent);
                    }
                });
            }
            return convertView;
        }
    }

    public class ViewHolder {
        @ViewInject(R.id.home_nav_item_image)
        private ImageView imageView;
        @ViewInject(R.id.home_nav_item_desc)
        private TextView textView;
    }

    /**给选择城市TextView设置监听, 必须使用private声明, 否则无效*/
    @Event(value = R.id.index_top_city, type = View.OnClickListener.class)
    private void onClick(View v){
        switch (v.getId()) {
            case R.id.index_top_city:
//                new DaoCity(getActivity()).addCities(); //TODO 添加数据到city表中 已经执行了一次, 保存了数据
//                new DaoCategory(getActivity()).addDataToCatagory(); //TODO 添加数据到category表中 只能执行一次
//                new DaoGoods(getContext()).addDataToGoods();//TODO 添加数据到goods表中 只能执行一次
//                Toast.makeText(getContext(), "添加了数据, 别点了", Toast.LENGTH_SHORT).show();
                //带有返回值的跳转, 启动activity之后, 点击获取数据, 返回之前的Activity并且传入数据
                startActivityForResult(new Intent(getActivity(), CityActivity.class), MyUtils.RequestCityCode);
                break;
            default:
                break;
        }
    }

    /**处理取出setResult()返回的结果获取手动选择城市的名称*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //根据requestCode定位返回给哪个startActivityForResult()方法
        if(requestCode == MyUtils.RequestCityCode && resultCode == Activity.RESULT_OK){
            cityName = data.getStringExtra("cityName"); //取出setResult()传递过来的值
            topCity.setText(cityName); //设置 手动指定 的城市名字
        }
    }

    /**覆写Fragment的onStart方法*/
    @Override
    public void onStart() {
        super.onStart();
        Log.e("Tag", "执行了FragmentHome类中的onStart()方法");
    }

    /**检查GPS是否打开*/
    private void checkGPSIsOpen() {
        //获取当前的LocationManager对象
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //判断GPS是否打开
        boolean isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isOpen) {//没有打开
            //打开GPS设置界面
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, 0); //设置完后返回界面
        }
        //开始定位
        startLocation();
        Log.e("Tag", "执行了FragmentHome类中的checkGPSIsOpen()方法");
    }
    /** 使用gps定位的方法 TODO 开始定位的方法*/
    private void startLocation() {
        //开始定位
        mlocationClient.startLocation();
        Log.e("Tag", "执行了FragmentHome类中的startLocation()方法");
    }

    /** 在这里获取定位信息, 定位城市名字 */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        //更新当前的位置信息 TODO
        updateWithNewLocation(aMapLocation);
    }

    //接收并且处理传入的城市名字
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == 1) {
                //把城市名字设置给TextView
                topCity.setText(cityName);
            }
            return false;
        }
    });

    /** 获取对应位置的经纬度并且定位城市 */
    private void updateWithNewLocation(AMapLocation aMapLocation){
        double lat = 0.0, lng = 0.0;  //定义经纬度数据
        if(aMapLocation != null) {
            lat = aMapLocation.getLatitude();  //获取纬度
            lng = aMapLocation.getLongitude(); //获取经度
            cityName = aMapLocation.getCity();
            Log.e("Tag", "纬度是: "+ lat + " 经度是: " + lng);
            Log.e("Tag", "城市名字:" + cityName);
        } else { //如无法获取位置
            cityName = "无法获取城市位置";
        }
        //拿到城市信息后,发送空消息
        handler.sendEmptyMessage(1);
    }

    //退出Fragment时调用
    @Override
    public void onDestroy() {
        super.onDestroy();
        //保存城市
        SharedUtils.putCityName(getActivity(), cityName);
        //停止定位
        stopMyLocation();
    }
    //停止定位的方法
    private void stopMyLocation(){
        mlocationClient.onDestroy(); //停止定位
    }
}