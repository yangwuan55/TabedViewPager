package com.larry.tabscrollindicator;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String[] ITEMS = new String[] { "Home", "Paper", "Setting" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewPagerWithTab view = new ViewPagerWithTab(this);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		view.setAdapter(new SimplePagerAdapter());
		setContentView(view);
	}
	
	public class SimplePagerAdapter extends TabPagerAdapter {

		@Override
		public int getCount() {
			return ITEMS.length;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = new View(MainActivity.this);
			if (position%2 == 1) {
				view.setBackgroundColor(Color.GRAY);
			} else {
				view.setBackgroundColor(Color.BLUE);
			}
			container.addView(view);
			return view;
		}

		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public void destroyItem(View container, int position, Object view) {
			((ViewPager) container).removeView((View) view);
		}

		@Override
		View getTitleView(int position) {
			TextView view = new TextView(MainActivity.this);
			view.setGravity(Gravity.CENTER);
			view.setText(ITEMS[position]);
			return view;
		}
		
	}

}
