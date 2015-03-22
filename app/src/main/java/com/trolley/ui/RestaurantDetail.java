package com.trolley.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.trolley.MainActivity;
import com.trolley.R;
import com.trolley.custom.CustomFragment;
import com.trolley.model.Data;

/**
 * The Class RestaurantDetail is the Fragment class that is launched when the
 * user select a Restaurant in Restaurant listing screen . It simply shows a
 * dummy list of Restaurant food items. You can customize this class to display
 * actual Food listing.
 */
public class RestaurantDetail extends CustomFragment
{

	/** The list of food items for category 1. */
	private ArrayList<Data> fList1;

	/** The list of food items for category 2. */
	private ArrayList<Data> fList2;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.restaurant_detail, null);
		setHasOptionsMenu(true);

		setFoodList(v);
		return v;
	}

	/**
	 * Setup and initialize the Food list view.
	 * 
	 * @param v
	 *            the root view
	 */
	private void setFoodList(View v)
	{
		loadDummyFoods();

		ExpandableListView list = (ExpandableListView) v
				.findViewById(R.id.list);
		list.setAdapter(new FoodAdapter());
		list.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id)
			{
				((MainActivity) getActivity()).launchFragment(-3);
				return true;
			}
		});

		list.expandGroup(0);
		list.expandGroup(1);

	}

	/**
	 * Load a dummy list of foods. You need to write your own logic to load
	 * actual list of food items.
	 * 
	 */
	private void loadDummyFoods()
	{
		ArrayList<Data> sl = new ArrayList<Data>();
		sl.add(new Data("Appetizer Combo with French Fries", "$13.00",
				"4 pcs buffalo wings, poppers and cheese", R.drawable.img_d1));
		sl.add(new Data("Chicken Fingers", "$9.00", "6 pcs with chips",
				R.drawable.img_d2));
		sl.add(new Data("Garlic Bread with Cheese", "$8.55",
				"4 pcs buffalo wings, poppers and cheese ", R.drawable.img_d3));

		fList1 = new ArrayList<Data>(sl);

		sl = new ArrayList<Data>();
		sl.add(new Data("Fettuccine with Meat Sauce", "$20.00",
				"Meatballs with Soda", R.drawable.img_d4));
		sl.add(new Data("Spaghetti", "$15.35",
				"Meat sauce and meatballs with soda", R.drawable.img_d5));

		fList2 = new ArrayList<Data>(sl);
		fList2.addAll(sl);
	}

	/**
	 * The Class FoodAdapter is the adapter class for Food ListView. The current
	 * implementation simply shows dummy contents and you can customize this
	 * class to display actual contents as per your need.
	 */
	private class FoodAdapter extends BaseExpandableListAdapter
	{

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getGroupCount()
		 */
		@Override
		public int getGroupCount()
		{
			return 2;
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
		 */
		@Override
		public int getChildrenCount(int groupPosition)
		{
			return (groupPosition == 0 ? fList1 : fList2).size();
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getGroup(int)
		 */
		@Override
		public String getGroup(int groupPosition)
		{
			return groupPosition == 0 ? "Appitizers" : "Pasta";
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getChild(int, int)
		 */
		@Override
		public Data getChild(int groupPosition, int childPosition)
		{
			return (groupPosition == 0 ? fList1 : fList2).get(childPosition);
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getGroupId(int)
		 */
		@Override
		public long getGroupId(int groupPosition)
		{
			return groupPosition;
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getChildId(int, int)
		 */
		@Override
		public long getChildId(int groupPosition, int childPosition)
		{
			return childPosition;
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#hasStableIds()
		 */
		@Override
		public boolean hasStableIds()
		{
			return false;
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent)
		{
			if (convertView == null)
				convertView = getLayoutInflater(null).inflate(
						R.layout.rest_detail_item1, null);
			((TextView) convertView).setText(getGroup(groupPosition));
			return convertView;
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent)
		{

			if (convertView == null)
				convertView = getLayoutInflater(null).inflate(
						R.layout.rest_detail_item2, null);

			Data d = getChild(groupPosition, childPosition);
			TextView lbl = (TextView) convertView.findViewById(R.id.lbl1);
			lbl.setText(d.getTitle1());

			lbl = (TextView) convertView.findViewById(R.id.lbl2);
			lbl.setText(d.getTitle2());

			lbl = (TextView) convertView.findViewById(R.id.lbl3);
			lbl.setText(d.getDesc());

			ImageView img = (ImageView) convertView.findViewById(R.id.img1);
			img.setImageResource(d.getImage1());

			return convertView;
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
		 */
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition)
		{
			return true;
		}

	}

}
