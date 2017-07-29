package com.roshine.lstypechoblog.mvp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.mvp.view.activity.base.BaseToolBarActivity;
import com.roshine.lstypechoblog.mvp.view.adapter.FragmentAdapter;
import com.roshine.lstypechoblog.mvp.view.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roshine
 * @date 2017/7/27 16:48
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 主页
 */
public class MainActivity extends BaseToolBarActivity implements BottomNavigationBar.OnTabSelectedListener {
    private BottomNavigationBar bottomNavigationBar;
    private ViewPager mViewPager;
    private List<String> bottomItemTexts = new ArrayList<>();
    private List<Integer> bottomItemIcons = new ArrayList();
    private List<Fragment> fragments = new ArrayList();
    private FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initBottomNavigationBar();
        initViewPager();
    }

    private void initViewPager() {
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(fragmentAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bnb_bottom_bar);
        mViewPager = (ViewPager) findViewById(R.id.vp_main);
    }

    private void initDatas() {
        bottomItemTexts.add(getResources().getString(R.string.mine_text));
        bottomItemTexts.add(getResources().getString(R.string.none_text));
        bottomItemTexts.add(getResources().getString(R.string.setting_text));
        bottomItemIcons.add(R.mipmap.mine_icon);
        bottomItemIcons.add(R.mipmap.add_icon);
        bottomItemIcons.add(R.mipmap.settion_icon);
        for (int i = 0; i < bottomItemTexts.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putString("title", bottomItemTexts.get(i));
            Fragment mineFragment = MineFragment.newInstance(bundle);
            fragments.add(mineFragment);

        }
    }

    private void initBottomNavigationBar() {
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_DEFAULT)//设置底部导航栏模式,默认
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT)//设置底部导航栏背景样式,默认
                .setInActiveColor(R.color.gray_light)//设置底部item未选中的颜色
                .setActiveColor(R.color.colorPrimary)//设置底部item选中时颜色
                .setBackgroundColor(getResources().getColor(R.color.white));//设置底部item背景颜色
        bottomNavigationBar.setAutoHideEnabled(false);//设置底部导航栏不隐藏
        bottomNavigationBar.setTabSelectedListener(this);//设置底部item选中监听
        for (int i = 0; i < bottomItemIcons.size(); i++) {
            bottomNavigationBar.addItem(new BottomNavigationItem(bottomItemIcons.get(i), bottomItemTexts.get(i)));
        }
        bottomNavigationBar.setFirstSelectedPosition(0);
        bottomNavigationBar.initialise();

    }

    //底部item选中时
    @Override
    public void onTabSelected(int position) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(position);
        }
    }

    //底部item未选中时
    @Override
    public void onTabUnselected(int position) {

    }

    //底部item重新选中时
    @Override
    public void onTabReselected(int position) {

    }
}
