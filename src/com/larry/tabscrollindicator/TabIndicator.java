package com.larry.tabscrollindicator;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.TabHost;

public class TabIndicator extends LinearLayout implements OnPageChangeListener {
	private View mUnderLineView;
	private LinearLayout mTabIndicatorContain;
	private LinearLayout mTabIndicatorLine;
	private int mUnderLineWidth;
	private int mTabCount = 0;
	
	private OnTabSelectionChanged mOnTabSelectionChanged;

	public TabIndicator(Context context) {
		super(context);
		setOrientation(LinearLayout.VERTICAL);
		// indicator contain
		mTabIndicatorContain = new LinearLayout(context);
		mTabIndicatorContain.setOrientation(LinearLayout.HORIZONTAL);

		// indicator line
		mTabIndicatorLine = new LinearLayout(context);
		mTabIndicatorLine.setOrientation(LinearLayout.HORIZONTAL);
		mUnderLineView = new View(context);
		mUnderLineView.setBackgroundColor(Color.RED);
		mTabIndicatorLine.addView(mUnderLineView, new LayoutParams(0, LayoutParams.MATCH_PARENT));
		
		// add two part to parent
		addView(mTabIndicatorContain, new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));
		addView(mTabIndicatorLine, new LayoutParams(LayoutParams.MATCH_PARENT, 5));
	}
	
	public void addTab(View view) {
		mTabIndicatorContain.addView(view, new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
		view.setOnClickListener(new onTabClickListener(mTabCount));
		mTabCount++;
	}
	
	public void setOnTabSelectionChange(OnTabSelectionChanged l) {
		mOnTabSelectionChanged = l;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		LayoutParams layoutParams = (LinearLayout.LayoutParams) mUnderLineView.getLayoutParams();
		mUnderLineWidth = getMeasuredWidth() / mTabCount; 
		layoutParams.width = mUnderLineWidth;
		mUnderLineView.setLayoutParams(layoutParams);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	public void onPageScrollStateChanged(int position) {
		
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		mTabIndicatorLine.scrollTo(-((int)(mUnderLineWidth*positionOffset) + mUnderLineWidth*position), 0);
		Log.d("TabIndicator", "scrollTo: " + (positionOffsetPixels+mUnderLineWidth*position));
		Log.d("TabIndicator", "position: " + position + " | positionOffset: " + positionOffsetPixels);
	}

	@Override
	public void onPageSelected(int position) {

	}
	
//	final class SmoothScrollRunnable implements Runnable {
//		private final Interpolator mInterpolator;
//		private final int mScrollToY;
//		private final int mScrollFromY;
//		private final long mDuration;
//		private boolean mContinueRunning = true;
//		private long mStartTime = -1;
//		private int mCurrentY = -1;
//
//		public SmoothScrollRunnable(int fromY, int toY, long duration) {
//			mScrollFromY = fromY;
//			mScrollToY = toY;
//			mInterpolator = mScrollAnimationInterpolator;
//			mDuration = duration;
//		}
//
//		@Override
//		public void run() {
//
//			/**
//			 * Only set mStartTime if this is the first time we're starting,
//			 * else actually calculate the Y delta
//			 */
//			if (mStartTime == -1) {
//				mStartTime = System.currentTimeMillis();
//			} else {
//
//				/**
//				 * We do do all calculations in long to reduce software float
//				 * calculations. We use 1000 as it gives us good accuracy and
//				 * small rounding errors
//				 */
//				long normalizedTime = (1000 * (System.currentTimeMillis() - mStartTime)) / mDuration;
//				normalizedTime = Math.max(Math.min(normalizedTime, 1000), 0);
//
//				final int deltaY = Math.round((mScrollFromY - mScrollToY)
//						* mInterpolator.getInterpolation(normalizedTime / 1000f));
//				mCurrentY = mScrollFromY - deltaY;
//				setHeaderScroll(mCurrentY);
//			}
//
//			// If we're not at the target Y, keep going...
//			if (mContinueRunning && mScrollToY != mCurrentY) {
//				ViewCompat.postOnAnimation(mTabIndicatorLine, this);
//			} 
//		}
//
//		public void stop() {
//			mContinueRunning = false;
//			removeCallbacks(this);
//		}
//	}

	private class onTabClickListener implements View.OnClickListener {
		private int tabIndex;
		public onTabClickListener(int tabIndex) {
			this.tabIndex = tabIndex;
		}
		
		@Override
		public void onClick(View v) {
			if (mOnTabSelectionChanged != null) {
				mOnTabSelectionChanged.onTabSelectionChanged(tabIndex);
			}
		}
	}
	
	/**
	 * Let {@link TabHost} know that the user clicked on a tab indicator.
	 */
	static interface OnTabSelectionChanged {
		/**
		 * Informs the TabHost which tab was selected. It also indicates if the
		 * tab was clicked/pressed or just focused into.
		 * 
		 * @param tabIndex
		 *            index of the tab that was selected
		 */
		void onTabSelectionChanged(int tabIndex);
	}
	

}
