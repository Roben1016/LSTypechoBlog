package com.roshine.lstypechoblog.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.mvp.view.activity.base.BaseToolBarActivity;
import com.roshine.lstypechoblog.mvp.view.adapter.FragmentAdapter;
import com.roshine.lstypechoblog.mvp.view.fragment.MineFragment;
import com.roshine.lstypechoblog.mvp.view.fragment.SettingFragment;
import com.roshine.lstypechoblog.utils.ActivityUtil;
import com.roshine.lstypechoblog.utils.ThemeColorUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Roshine
 * @date 2017/7/27 16:48
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 主页
 */
public class MainActivity extends BaseToolBarActivity implements BottomNavigationBar.OnTabSelectedListener, ViewPager.OnPageChangeListener {
    public static final String ENTER_TYPE_MAIN = "mainActivity";
    @BindView(R.id.bnb_bottom_bar)
    BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.vp_main)
    ViewPager mViewPager;
    private List<String> bottomItemTexts = new ArrayList<>();
    private List<Integer> bottomItemIcons = new ArrayList();
    private List<Fragment> fragments = new ArrayList();
    private FragmentAdapter fragmentAdapter;
    private SettingFragment settingFragment;
    private long firstime;
    private int lastPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initViewPager() {
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initViewData(Bundle savedInstanceState) {
        initDatas();
        initBottomNavigationBar();
        initViewPager();
    }

    private void initDatas() {
        bottomItemTexts.add(getResources().getString(R.string.mine_text));
        bottomItemTexts.add(getResources().getString(R.string.none_text));
        bottomItemTexts.add(getResources().getString(R.string.setting_text));
        bottomItemIcons.add(R.drawable.mine_icon);
        bottomItemIcons.add(R.drawable.add_icon);
        bottomItemIcons.add(R.drawable.settion_icon);
        Bundle bundle = new Bundle();
        bundle.putString("title", bottomItemTexts.get(0));
        Fragment mineFragment = MineFragment.newInstance(bundle);
        fragments.add(mineFragment);
        settingFragment = new SettingFragment();
        fragments.add(settingFragment);
    }

    private void initBottomNavigationBar() {
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_DEFAULT)//设置底部导航栏模式,默认
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT)//设置底部导航栏背景样式,默认
                .setInActiveColor(R.color.gray_light)//设置底部item未选中的颜色
                .setActiveColor(ThemeColorUtil.getThemeColor())//设置底部item选中时颜色
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
        switch (position) {
            case 0:
                lastPosition = 0;
                mViewPager.setCurrentItem(0);
        	    break;
            case 1:
                Intent intent = new Intent(this,EditBlogActivity.class);
                intent.putExtra("enterType",ENTER_TYPE_MAIN);
                startActivity(intent);
                bottomNavigationBar.selectTab(lastPosition);
                break;
            case 2:
                lastPosition = 2;
                mViewPager.setCurrentItem(1);
                break;

            default:
        	    break;
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                bottomNavigationBar.selectTab(0);
                break;
            case 1:
                bottomNavigationBar.selectTab(2);
                break;

            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondtime = System.currentTimeMillis();
            if (secondtime - firstime > 3000) {
                toast(getResources().getString(R.string.exit_twice_click));
                firstime = System.currentTimeMillis();
                return true;
            } else {
                ActivityUtil.getInstance().appExit();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
