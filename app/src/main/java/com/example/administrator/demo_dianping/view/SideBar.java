package com.example.administrator.demo_dianping.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.example.administrator.demo_dianping.R;

/**
 * 选择城市时, 右侧按字母排序的, 自定义View
 * 绘制对应的应为字母
 */

public class SideBar extends View {

    /**在使用代码创建View对象的时候调用*/
    public SideBar(Context context) {
        super(context);
    }

    /**
     * 从XML文件创建View控件对象的时候调用
     * @attrs 在XML中定义的属性
     */
    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private OnTouchingLetterChangedListener letterChangedListener;
    private int choose;
    private Paint paint = new Paint(); //画笔
    /**26个字母*/
    public static String [] sideBar = {"热门", "A","B","C","D","E","F","G","H","I","J","K","L",
            "M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    /**此方法绘制自定义界面sideBar*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.GRAY); //画笔灰色
        paint.setTypeface(Typeface.DEFAULT_BOLD); //字体样式, 粗体
        paint.setTextSize(20);
        //获取自定义View的宽和高
        int height = getHeight();
        int width = getWidth();
        //设定每一个字母所在控件的高度(总高度除以个数)
        int each_height = height/sideBar.length;
        //绘制每一个字母
        for (int i = 0; i < sideBar.length; i++) {
            //字体所在X轴的偏移量
            float x = width/2 - paint.measureText(sideBar[i])/2;
            //字体所在Y轴的偏移量
            float y = (1+i)*each_height;
            canvas.drawText(sideBar[i], x, y, paint);
        }
    }
    //定义监听事件接口
    public interface OnTouchingLetterChangedListener{
        //传递进来对应字母
        void onTouchingLetterChanged(String s); //根据滑动位置的索引进行处理
    }
    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener){
        this.letterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 分发对应的touch监听
     *
     * @param event The motion event to be dispatched.
     * @return True if the event was handled by the view, false otherwise.
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction(); //获取对应的动作
        final float y = event.getY(); //点击的Y坐标
        final OnTouchingLetterChangedListener listener = letterChangedListener; //点击的字母
        //获取点击y轴坐标所占总高度的比例*数组的长度  就是当前字母的索引
        final int c = (int) (y/getHeight()*sideBar.length);
        switch (action){
            case MotionEvent.ACTION_UP://手指抬起
                setBackgroundResource(android.R.color.transparent); //设置控件背景色透明
                invalidate();  //刷新View, 使以上修改生效
                break;
            default:
                setBackgroundResource(R.drawable.sidebar_background); //默认设置背景
                if(c > 0 && c < sideBar.length) { //索引位置
                    if(listener != null) {
                        listener.onTouchingLetterChanged(sideBar[c]); //自定义接口传入当前选择的字母
                    }
                    choose = c;
                    invalidate(); //刷新view
                }
                break;
        }
        return true;
    }
}
