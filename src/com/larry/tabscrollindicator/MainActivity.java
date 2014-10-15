package com.larry.tabscrollindicator;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String[] ITEMS = new String[] { "Home", "Paper", "Setting" };

    private static final String[] CONTENT = new String[] { "Calendar", "Camera", "Alarms", "Location" };
    private static final int[] ICONS = new int[] {
            R.drawable.perm_group_calendar,
            R.drawable.perm_group_camera,
            R.drawable.perm_group_device_alarms,
            R.drawable.perm_group_location,
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);

		pager.setAdapter(new SimplePagerAdapter());

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
	}
	
	public class SimplePagerAdapter extends PagerAdapter implements IconPagerAdapter {

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
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getIconResId(int index) {
            return ICONS[index];
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }

	}

}
