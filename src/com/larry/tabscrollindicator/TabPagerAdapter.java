package com.larry.tabscrollindicator;

import android.support.v4.view.PagerAdapter;
import android.view.View;

public abstract class TabPagerAdapter extends PagerAdapter {

	/**
	 * for the tab indicator 
	 * 
	 * @param position
	 * @return
	 * @author ckitterl 
	*/
	abstract View getTitleView(int position); 
}
