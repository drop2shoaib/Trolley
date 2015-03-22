package com.trolley.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trolley.Checkout;
import com.trolley.R;
import com.trolley.custom.CustomFragment;

/**
 * The Class AddCart is the Fragment class that is currently launched when the
 * user clicks on Cart option in Left navigation drawer. It simply shows a dummy
 * cart. You need to customize this class to display actual contents for the
 * Cart.
 */
public class AddCart extends CustomFragment
{

	/** The lbl. */
	private TextView lbl;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.add_cart, null);

		setHasOptionsMenu(true);

		setTouchNClick(v.findViewById(R.id.btnDone));
		setTouchNClick(v.findViewById(R.id.plus));
		setTouchNClick(v.findViewById(R.id.minus));

		lbl = (TextView) v.findViewById(R.id.quanity);
		return v;
	}

	/* (non-Javadoc)
	 * @see com.trolley.custom.CustomFragment#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);
		if (v.getId() == R.id.btnDone)
			startActivity(new Intent(getActivity(), Checkout.class));
		else if (v.getId() == R.id.plus || v.getId() == R.id.minus)
		{
			int i = Integer.parseInt(lbl.getText().toString());
			if (v.getId() == R.id.plus)
				i++;
			else
				i--;
			if (i <= 0)
				i = 1;
			lbl.setText(i + "");
		}
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateOptionsMenu(android.view.Menu, android.view.MenuInflater)
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		menu.findItem(R.id.menu_cart).setVisible(true);
		menu.findItem(R.id.menu_search).setVisible(false);
	}
}
