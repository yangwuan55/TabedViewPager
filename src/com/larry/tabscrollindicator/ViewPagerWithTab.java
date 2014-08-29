package com.larry.tabscrollindicator;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.larry.tabscrollindicator.TabIndicator.OnTabSelectionChanged;

public class ViewPagerWithTab extends LinearLayout implements
		OnPageChangeListener {
	private OnPageChangeListener mOnPageChangeListener;
	private ViewPager mViewPager;
	private TabIndicator mTabIndicator;
	private int mCurrentIndex = -1;
	private int mTabCount = 0;
	private TabPagerAdapter mAdapter;

	public ViewPagerWithTab(Context context) {
		super(context);
		setOrientation(LinearLayout.VERTICAL);
		mViewPager = new ViewPager(context);
		mViewPager.setOnPageChangeListener(this);
		mTabIndicator = new TabIndicator(context);
		addView(mTabIndicator, new LayoutParams(LayoutParams.MATCH_PARENT, 80));
		addView(mViewPager, new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));
		
	}

	public void setAdapter(TabPagerAdapter adapter) {
		mViewPager.setAdapter(adapter);
		mAdapter = adapter;
		setup();
	}

	public void setOnPageChangeListener(OnPageChangeListener listener) {
		mOnPageChangeListener = listener;
	}

	private void setup() {
		mTabCount = mAdapter.getCount();
		for (int i = 0; i < mTabCount; i++) {
			View titleView = mAdapter.getTitleView(i);
			mTabIndicator.addTab(titleView);
		}
		mTabIndicator.setOnTabSelectionChange(new OnTabSelectionChanged() {

			@Override
			public void onTabSelectionChanged(int tabIndex) {
				if (mCurrentIndex != tabIndex) {
					mViewPager.setCurrentItem(tabIndex, true);
					mCurrentIndex = tabIndex;
				}
			}
		});
	}

	@Override
	public void onPageScrollStateChanged(int position) {
		if (mOnPageChangeListener != null) {
			mOnPageChangeListener.onPageScrollStateChanged(position);
		}
		mTabIndicator.onPageScrollStateChanged(position);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		if (mOnPageChangeListener != null) {
			mOnPageChangeListener.onPageScrolled(position, positionOffset,
					positionOffsetPixels);
		}
		mTabIndicator.onPageScrolled(position, positionOffset,
				positionOffsetPixels);
	}

	@Override
	public void onPageSelected(int position) {
		if (mOnPageChangeListener != null) {
			mOnPageChangeListener.onPageSelected(position);
		}
		mTabIndicator.onPageSelected(position);
		mCurrentIndex = position;
	}
}
