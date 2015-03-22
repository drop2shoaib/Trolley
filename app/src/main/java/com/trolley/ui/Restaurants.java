package com.trolley.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trolley.R;
import com.trolley.custom.CustomFragment;

/**
 * The Class Restaurants is the Fragment class that is launched when the user
 * clicks on Favorites option in Left navigation drawer or when user click Order
 * now button on SignUp screen. It simply shows a dummy list of Restaurants. You
 * can customize this class to display actual Restaurants listing.
 */
public class Restaurants extends CustomFragment
{

	/** The tab. */
	private View tab;

	/** The pager. */
	private ViewPager pager;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.restaurants, null);
		setHasOptionsMenu(true);

		tab = setClick(v.findViewById(R.id.tab1));
		tab.setTag("0");
		setClick(v.findViewById(R.id.tab2)).setTag("1");
		setClick(v.findViewById(R.id.tab3)).setTag("2");
		setClick(v.findViewById(R.id.tab4)).setTag("3");
		setClick(v.findViewById(R.id.tab5)).setTag("4");

		setupPager(v);
		return v;
	}

	/**
	 * Setup the view pager.
	 * 
	 * @param v
	 *            the root view
	 */
	private void setupPager(final View v)
	{
		pager = (ViewPager) v.findViewById(R.id.pager);
		pager.setAdapter(new PageAdapter(getFragmentManager()));
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int pos)
			{
				tab.setEnabled(true);
				tab = v.findViewWithTag(pos + "");
				tab.setEnabled(false);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{
			}
		});
	}

	/**
	 * The Class PageAdapter is the adapter class for ViewPager that shows a few
	 * list of Restaurants for each page based on the tabs shown at top.
	 */
	private class PageAdapter extends FragmentStatePagerAdapter
	{

		/**
		 * Instantiates a new page adapter.
		 * 
		 * @param fm
		 *            the fm
		 */
		public PageAdapter(FragmentManager fm)
		{
			super(fm);
		}

		/* (non-Javadoc)
		 * @see android.support.v4.app.FragmentStatePagerAdapter#getItem(int)
		 */
		@Override
		public Fragment getItem(int pos)
		{
			return new RestuarantItem();
		}

		/* (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#getCount()
		 */
		@Override
		public int getCount()
		{
			return 5;
		}

	}

	/* (non-Javadoc)
	 * @see com.trolley.custom.CustomFragment#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);
		if (v.getId() == R.id.tab1 || v.getId() == R.id.tab2
				|| v.getId() == R.id.tab3 || v.getId() == R.id.tab4
				|| v.getId() == R.id.tab5)
		{
			/*tab.setEnabled(true);
			tab = v;
			tab.setEnabled(false);*/

			int page = Integer.parseInt(v.getTag() + "");
			pager.setCurrentItem(page, true);
		}
	}

}
