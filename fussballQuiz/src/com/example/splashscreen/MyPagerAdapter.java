package com.example.splashscreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class MyPagerAdapter extends FragmentPagerAdapter implements
		ViewPager.OnPageChangeListener {

	private MyLinearLayout cur = null;
	private MyLinearLayout next = null;
	private MainActivity2 context;
	private FragmentManager fm;
	private float scale;

	public MyPagerAdapter(MainActivity2 mainActivity2, FragmentManager fm) {
		super(fm);
		this.fm = fm;
		this.context = mainActivity2;
	}

	@Override
	public Fragment getItem(int position) 
	{
        // make the first pager bigger than others
        if (position == MainActivity2.FIRST_PAGE)
        	scale = MainActivity2.BIG_SCALE;     	
        else
        	scale = MainActivity2.SMALL_SCALE;
        
        position = position % MainActivity2.PAGES;
        return MyFragment.newInstance(context, position, scale);
	}

	@Override
	public int getCount()
	{		
		return MainActivity2.PAGES * MainActivity2.LOOPS;
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) 
	{	
		if (positionOffset >= 0f && positionOffset <= 1f)
		{
			cur = getRootView(position);
			next = getRootView(position +1);

			cur.setScaleBoth(MainActivity2.BIG_SCALE 
					- MainActivity2.DIFF_SCALE * positionOffset);
			next.setScaleBoth(MainActivity2.SMALL_SCALE 
					+ MainActivity2.DIFF_SCALE * positionOffset);
		}
	}

	@Override
	public void onPageSelected(int position) {}
	
	@Override
	public void onPageScrollStateChanged(int state) {}
	
	private MyLinearLayout getRootView(int position)
	{
		return (MyLinearLayout) 
				fm.findFragmentByTag(this.getFragmentTag(position))
				.getView().findViewById(R.id.root);
	}
	
	private String getFragmentTag(int position)
	{
	    return "android:switcher:" + context.pager.getId() + ":" + position;
	}
}
