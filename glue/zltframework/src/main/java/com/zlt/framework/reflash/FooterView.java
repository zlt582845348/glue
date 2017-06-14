package com.zlt.framework.reflash;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zlt.framework.R;
import com.zlt.framework.util.DisplayUtil;

/**
 * Created by Administrator on 2017/6/6.
 */

public class FooterView extends RelativeLayout implements IRefreshView {

    public boolean haveMore = true;
    private Context context;
    private ImageView imageView;
    private TextView textView;

    public FooterView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public FooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView()
    {
        setBackgroundColor(Color.parseColor("#eeeeee"));

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setPadding(DisplayUtil.dip2px(5,context),DisplayUtil.dip2px(5,context)
                ,DisplayUtil.dip2px(5,context),DisplayUtil.dip2px(5,context));
        LayoutParams layoutParams_layout = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams_layout.addRule(CENTER_IN_PARENT);
        addView(linearLayout,layoutParams_layout);

        imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.ic_progress);
        LayoutParams layoutParams_img = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams_img.setMargins(0,0,DisplayUtil.dip2px(10,context),0);
        linearLayout.addView(imageView,layoutParams_img);

        textView = new TextView(context);
        textView.setText("加载中");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        linearLayout.addView(textView);

    }

    @Override
    public void drag() {
        RotateAnimation animation =new RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(1000);//设置动画持续时间
        animation.setInterpolator(new LinearInterpolator());
        /** 常用方法 */
        animation.setRepeatCount(-1);//设置重复次数
        animation.setFillAfter(false);//动画执行完后是否停留在执行完的状态
        animation.setStartOffset(0);//执行前的等待时间
        imageView.setAnimation(animation);
        animation.startNow();
    }

    @Override
    public void loading() {

    }

    @Override
    public void end() {
        if(imageView.getAnimation()!=null)
        {
            imageView.getAnimation().cancel();
        }
        imageView.clearAnimation();
    }

    @Override
    public void setHaveMore(boolean have) {
        haveMore = have;
        if(have)
        {
            imageView.setVisibility(VISIBLE);
            textView.setText("加载中");
            setVisibility(GONE);
        }else{
            end();
            setVisibility(VISIBLE);
            imageView.setVisibility(GONE);
            textView.setText("已经到底了哦");
        }
    }

    @Override
    public boolean haveMore() {
        return haveMore;
    }


}
