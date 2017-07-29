package com.roshine.lstypechoblog.mvp.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * FragmentAdapter
 * 
 * @author Administrator
 * @extends FragmentPagerAdapter
 */
public class FragmentAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;
	FragmentManager fm;
	public FragmentAdapter(FragmentManager fm) {
		super(fm);
		this.fm = fm;
	}

	public FragmentAdapter(FragmentManager fm, List<Fragment> list) {
		super(fm);
		this.fragments = list;
		this.fm = fm;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override  
	public int getItemPosition(Object object) {  
	   return POSITION_NONE;  
	}  
}
