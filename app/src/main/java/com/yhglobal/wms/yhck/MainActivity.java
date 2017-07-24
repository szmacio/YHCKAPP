package com.yhglobal.wms.yhck;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import com.yhglobal.wms.yhck.Mfragment.*;
import com.yhglobal.wms.yhck.Madapter.*;
import com.yhglobal.wms.yhck.MyTransform.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;
import android.support.v4.app.Fragment;



public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener {
    List<Fragment> fragmentList = new ArrayList<Fragment>() ;
    private FragmentTabHost mTabHost;
    private LayoutInflater layoutInflater;
    private  ViewPager viewPager;
    private Class fragmentArray[] = { IndexFragment.class,ReportFragment.class,MemberFragment.class };
    private int imageViewArray[] = { R.drawable.tab_home_btn,R.drawable.tab_view_btn,R.drawable.tab_home_btn};
    private String textViewArray[] = { "首页","报表","我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       //viewPager.addOnPageChangeListener(this);
//        viewPager.setCurrentItem(0);
//        viewPager.addOnPageChangeListener(this);
//        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(),fragmentList));
//        viewPager.setPageTransformer(true,new DepthPageTransformer());

        initView();//初始化控件
        initPage();//初始化页面
        }

    private void initPage() {

        IndexFragment indexFragment = new IndexFragment();
        ReportFragment reportFragment =new ReportFragment();
        MemberFragment memberFragment = new MemberFragment();
        fragmentList.add(indexFragment);
        fragmentList.add(reportFragment);
        fragmentList.add(memberFragment);

        //绑定Fragment适配器
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), fragmentList));
        mTabHost.getTabWidget().setDividerDrawable(null);
    }

    private void initView() {
         viewPager = (ViewPager) findViewById(R.id.pager);
         /*实现OnPageChangeListener接口,目的是监听Tab选项卡的变化，然后通知ViewPager适配器切换界面*/
        /*简单来说,是为了让ViewPager滑动的时候能够带着底部菜单联动*/
        viewPager.addOnPageChangeListener(this);//设置页面切换时的监听器
        layoutInflater = LayoutInflater.from(this);//加载布局管理器
        /*实例化FragmentTabHost对象并进行绑定*/
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);//绑定tahost
        mTabHost.setup(this, getSupportFragmentManager(), R.id.pager);//绑定viewpager
        /*实现setOnTabChangedListener接口,目的是为监听界面切换），然后实现TabHost里面图片文字的选中状态切换*/
        /*简单来说,是为了当点击下面菜单时,上面的ViewPager能滑动到对应的Fragment*/
        mTabHost.setOnTabChangedListener(this);
        int count = textViewArray.length;

        /*新建Tabspec选项卡并设置Tab菜单栏的内容和绑定对应的Fragment*/
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置标签、图标和文字
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(textViewArray[i])
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中，并绑定Fragment
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            mTabHost.setTag(i);
            mTabHost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.selector_tab_background);//设置Tab被选中的时候颜色改变
        }
    }
    private View getTabItemView(int i) {
        //将xml布局转换为view对象
        View view = layoutInflater.inflate(R.layout.tab_content, null);
        //利用view对象，找到布局中的组件,并设置内容，然后返回视图
        ImageView mImageView = (ImageView) view
                .findViewById(R.id.tab_imageview);
        TextView mTextView = (TextView) view.findViewById(R.id.tab_textview);
        mImageView.setBackgroundResource(imageViewArray[i]);
        mTextView.setText(textViewArray[i]);
        return view;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d("TEX","onPageScrolled");
    }

    @Override
    public void onPageSelected(int position) {
        TabWidget widget = mTabHost.getTabWidget();
        int oldFocusability = widget.getDescendantFocusability();
        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);//设置View覆盖子类控件而直接获得焦点
        mTabHost.setCurrentTab(position);//根据位置Postion设置当前的Tab
        widget.setDescendantFocusability(oldFocusability);//设置取消分割线

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d("TEX","onPageScrollStateChanged");

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        Log.d("TEX","onPageScrollStateChanged");

    }

    @Override
    public void onTabChanged(String s) {


        int position = mTabHost.getCurrentTab();
        viewPager.setCurrentItem(position);//把选中的Tab的位置赋给适配器，让它控制页面切换

        ;
    }
}
