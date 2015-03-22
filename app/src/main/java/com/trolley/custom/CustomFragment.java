package com.trolley.custom;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.trolley.R;

/**
 * The Class CustomFragment is the base Fragment class. You can extend your
 * Fragment classes with this class in case you want to apply common set of
 * rules for those Fragments.
 */
public class CustomFragment extends Fragment implements OnClickListener
{

	/**
	 * Set the touch and click listener for a View.
	 * 
	 * @param v
	 *            the view
	 * @return the same view
	 */
	public View setTouchNClick(View v)
	{

		v.setOnClickListener(this);
		v.setOnTouchListener(CustomActivity.TOUCH);
		return v;
	}

	/**
	 * Sets the click listener for a view.
	 * 
	 * @param v
	 *            the view
	 * @return the view on which listener is applied
	 */
	public View setClick(View v)
	{

		if (v != null)
			v.setOnClickListener(this);
		return v;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{

	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateOptionsMenu(android.view.Menu, android.view.MenuInflater)
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.search, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

}
