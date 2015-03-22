package com.trolley.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.trolley.MainActivity;
import com.trolley.R;
import com.trolley.custom.CustomFragment;
import com.trolley.model.Data;

/**
 * The Class RestuarantItem is the Fragment class that is linked to ViewPager
 * item and it simply shows a dummy list of Restaurants. You can customize this
 * to display actual contents.
 */
public class RestuarantItem extends CustomFragment
{

	/** The Restaurant list. */
	private ArrayList<Data> rList;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		ListView list = (ListView) inflater.inflate(R.layout.pager_list, null);

		loadDummyRestaurants();
		list.setAdapter(new RestaurantsAdapter());
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				((MainActivity) getActivity()).launchFragment(-2);
			}
		});
		return list;
	}

	/**
	 * Load a dummy list of Restaurants. You need to write your own logic to
	 * load actual list of Restaurants.
	 * 
	 */
	private void loadDummyRestaurants()
	{
		ArrayList<Data> sl = new ArrayList<Data>();
		sl.add(new Data("Sushi Samurai", "110 Reviews", null, R.drawable.img_r1));
		sl.add(new Data("Tasty Find", "341 Reviews", null, R.drawable.img_r2));
		sl.add(new Data("Copehto", "343 Reviews", null, R.drawable.img_r3));
		sl.add(new Data("Bread 2 Bowl", "135 Reviews", null, R.drawable.img_r4));
		sl.add(new Data("Fairways Restaurant", "744 Reviews", null,
				R.drawable.img_r5));

		rList = new ArrayList<Data>(sl);
		rList.addAll(sl);
		rList.addAll(sl);
		rList.addAll(sl);
	}

	/**
	 * The Class RestaurantsAdapter is the adapter class for Restaurants
	 * ListView. The current implementation simply shows dummy contents and you
	 * can customize this class to display actual contents as per your need.
	 */
	private class RestaurantsAdapter extends BaseAdapter
	{

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount()
		{
			return rList.size();
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Data getItem(int position)
		{
			return rList.get(position);
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int position)
		{
			return position;
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
				convertView = getLayoutInflater(null).inflate(
						R.layout.rest_item, null);

			Data d = getItem(position);
			TextView lbl = (TextView) convertView.findViewById(R.id.lbl1);
			lbl.setText(d.getTitle1());

			lbl = (TextView) convertView.findViewById(R.id.lbl2);
			lbl.setText(d.getTitle2());

			ImageView img = (ImageView) convertView.findViewById(R.id.img1);
			img.setImageResource(d.getImage1());

			RatingBar r = (RatingBar) convertView.findViewById(R.id.rating);
			r.setRating((float) Math.min(5, Math.random() * 10));

			return convertView;
		}

	}

}
