package com.trolley;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.trolley.R;
import com.trolley.custom.CustomActivity;

/**
 * The Checkout is the activity class that shows the final Checkout interface.
 * Currently this is launched when ever user select Add to Cart option from Cart
 * screen. You need to write your own logic to show actual price, food names and
 * all other details as per your need.
 */
public class Checkout extends CustomActivity
{
	/* (non-Javadoc)
	 * @see com.food.custom.CustomActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkout);

		getActionBar().setTitle("Checkout");

		setTouchNClick(R.id.btnDone);
		setTouchNClick(R.id.deliver);
		setTouchNClick(R.id.pickup);
	}

	/* (non-Javadoc)
	 * @see com.trolley.custom.CustomActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);
		if (v.getId() == R.id.btnDone)
		{
			startActivity(new Intent(this, MainActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.search, menu);
		return super.onCreateOptionsMenu(menu);
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == android.R.id.home)
		{
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
