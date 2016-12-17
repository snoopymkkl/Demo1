package com.example.administrator.demo_dianping.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.demo_dianping.R;
import com.example.administrator.demo_dianping.entity.Goods;
import com.example.administrator.demo_dianping.entity.ResponseObject;
import com.example.administrator.demo_dianping.utils.DaoGoods;
import com.squareup.picasso.Picasso;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 团购 的Fragment
 */

public class FragmentTuan extends Fragment {
    @ViewInject(R.id.index_list_goods)
    private ListView listGoods;
    private DaoGoods daoGoods;
    private List<Goods> list;
    private MyAdapter adapter;
    @ViewInject(R.id.tuan_ptr)
    PtrClassicFrameLayout ptrClassicFrame;

//    private boolean isLastRow = false; // 是否为最后一行
//    private boolean isLoading = false; // 是否正在加载数据
//    private boolean isMore = true; // 是否还有更多数据

    //创建Fragment的方法
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //将fragment_home.xml布局设置给Fragment
        View view = inflater.inflate(R.layout.fragment_tuan, null);
        x.view().inject(this, view);

        list = new ArrayList<>();
        initPtr();
        daoGoods = new DaoGoods(getContext());
        adapter = new MyAdapter();
        listGoods.setAdapter(adapter);
        return view;
    }


    private void initPtr(){
        //初始化ptrClassicFrame设置
        ptrClassicFrame.setResistance(1.7f); //阻尼
        ptrClassicFrame.setRatioOfHeaderHeightToRefresh(1.2f); //刷新位移比例
        ptrClassicFrame.setDurationToClose(200); //回弹延时
        ptrClassicFrame.setDurationToCloseHeader(1000); //头部回弹延时
        ptrClassicFrame.disableWhenHorizontalMove(true); //横向冲突
        ptrClassicFrame.setEnabledNextPtrAtOnce(false); //立即刷新
        ptrClassicFrame.setPullToRefresh(false); //释放刷新
        ptrClassicFrame.setKeepHeaderWhenRefresh(true); //保持头部

        ptrClassicFrame.setPtrHandler(new PtrHandler() {
            //判断是否可以刷新, 默认代码即可
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, listGoods, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadDatas();
            }
        });
    }

    private void loadDatas(){

        list = daoGoods.queryGoods();
        adapter.notifyDataSetChanged();
    }



    public class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            if(convertView == null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tuan_goods_list_item, null);
                x.view().inject(holder, convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Goods goods = list.get(position);
            //picasso框架避免图片出现oom, 以及图片错位
            Picasso.with(parent.getContext()).load(R.drawable.ic_tuan_image).placeholder(R.drawable.ic_empty_dish).into(holder.imageView );
            StringBuffer sbf = new StringBuffer("$" + goods.getValue());
            //添加中划线
            SpannableString spannable = new SpannableString(sbf);
            spannable.setSpan(new StrikethroughSpan(), 0, sbf.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            holder.tvValue.setText(spannable);
            holder.tvTitle.setText(goods.getTitle());
            holder.tvConTitle.setText(goods.getSortTitle());
            holder.tvPrice.setText("$" + goods.getPrice());
            holder.tvCount.setText(goods.getSoldOut() + "份");
            return convertView;
        }
    }
    class ViewHolder{
        private @ViewInject(R.id.index_gl_item_image)
        ImageView imageView;
        @ViewInject(R.id.index_gl_item_title)
        private TextView tvTitle;
        @ViewInject(R.id.index_gl_item_titlecontent)
        private TextView tvConTitle;
        @ViewInject(R.id.index_gl_item_price)
        private TextView tvPrice;
        @ViewInject(R.id.index_gl_item_value)
        private TextView tvValue;
        @ViewInject(R.id.index_gl_item_area)
        private TextView tvCount;
    }
}
