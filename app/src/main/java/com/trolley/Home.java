package com.trolley;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.trolley.utils.CONSTS;

import java.util.List;

/**
 * Created by sunny on 22/3/2015.
 */
public class Home extends ActionBarActivity implements View.OnClickListener {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                return true;
            case R.id.action_about:
                showInfo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private LinearLayout requestTrolleyLayout, orderConfirmedLayout;
    private Button requestTrolleyButton, homeUserDetails, cancelOrder, userEditButton;
    private ParseObject addressParseObject = null;
    private TextView userFullName, userAddress;
    private ProgressBar userDetailsProgressBar;
    private LinearLayout orderProgressLinearLayout;
    private ParseObject orderObject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        this.initializeVariables();
    }

    private void initializeVariables() {
        this.requestTrolleyLayout = (LinearLayout) findViewById(R.id.requestTrolley);
        this.orderConfirmedLayout = (LinearLayout) findViewById(R.id.orderConfirmed);

        this.requestTrolleyButton = (Button) findViewById(R.id.requestTrolleyButton);

        this.userFullName = (TextView) findViewById(R.id.user_fullName);
        this.userAddress = (TextView) findViewById(R.id.user_address);
        this.userDetailsProgressBar = (ProgressBar) findViewById(R.id.home_details_progress_bar);
        this.orderProgressLinearLayout = (LinearLayout) findViewById(R.id.home_order_progress_bar);
        this.cancelOrder = (Button) findViewById(R.id.home_cancel_order);
        this.userEditButton = (Button) findViewById(R.id.home_user_edit);
        this.userEditButton.setOnClickListener(this);
        this.requestTrolleyButton.setOnClickListener(this);
        this.cancelOrder.setOnClickListener(this);
        this.fillUserDetailsInBackground();
        this.fillOrderStatusInBackground();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.requestTrolleyButton:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setTitle(CONSTS.CONFIRM_ORDER_TEXT)
                        .setMessage(CONSTS.ORDER_WILL_BE_DELIVERED + this.getAddressAsString())
                        .setPositiveButton(CONSTS.YES, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Home.this.createOrder();
                            }


                        })
                        .setNegativeButton(CONSTS.No, null)
                        .show();
                break;
            case R.id.home_cancel_order:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setTitle(CONSTS.CONFIRM_CANCEL_TEXT)
                        .setPositiveButton(CONSTS.YES, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Home.this.cancelOrder();
                            }


                        })
                        .setNegativeButton(CONSTS.No, null)
                        .show();
                break;
            case R.id.home_user_edit:

                break;
        }

    }


    private void showOrderConfirmed() {
        this.requestTrolleyLayout.setVisibility(View.GONE);
        this.orderConfirmedLayout.setVisibility(View.VISIBLE);
        this.orderProgressLinearLayout.setVisibility(View.GONE);
    }

    private void showRequestTrolley() {

        this.requestTrolleyLayout.setVisibility(View.VISIBLE);
        this.orderConfirmedLayout.setVisibility(View.GONE);
        this.orderProgressLinearLayout.setVisibility(View.GONE);
    }

    private void loadingOrderStatus() {
        this.requestTrolleyLayout.setVisibility(View.GONE);
        this.orderConfirmedLayout.setVisibility(View.GONE);
        this.orderProgressLinearLayout.setVisibility(View.VISIBLE);
    }

    private void cancelOrder() {
        this.loadingOrderStatus();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("orders");
        // Retrieve the object by id
        query.getInBackground(this.orderObject.getObjectId(), new GetCallback<ParseObject>() {
            public void done(ParseObject order, ParseException e) {
                if (e == null) {
                    order.put("status", CONSTS.ORDER_CANCELLED);
                    order.saveInBackground();
                    Home.this.showRequestTrolley();
                    Toast.makeText(Home.this, CONSTS.ORDER_CANCELLED_TEXT, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Home.this, CONSTS.ORDER_FAILED_TO_CANCELLED, Toast.LENGTH_LONG).show();
                    Home.this.showOrderConfirmed();

                }
            }
        });
    }


    private void createOrder() {
        if (this.addressParseObject == null) {
            Toast.makeText(this, CONSTS.DELIVERY_ADDRESS_EMPTY, Toast.LENGTH_LONG);
            return;
        }
        this.loadingOrderStatus();
        ParseObject object = new ParseObject("orders");
        object.put("delivery_address", this.addressParseObject);
        object.put("user", ParseUser.getCurrentUser());
        object.put("status", CONSTS.ORDER_PLACED);
        this.orderObject = object;
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException exception) {
                if (exception != null) {
                    Toast.makeText(Home.this, CONSTS.FAILED_TO_PLACE_ORDER, Toast.LENGTH_LONG).show();
                    Home.this.showRequestTrolley();

                } else {
                    Home.this.showOrderConfirmed();
                }

            }
        });
    }


    private void fillOrderStatusInBackground() {
        this.loadingOrderStatus();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("orders");
        query.whereEqualTo("status", CONSTS.ORDER_PLACED);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null && parseObjects.size() > 0) {

                    Home.this.orderObject = parseObjects.get(0);
                    Home.this.showOrderConfirmed();
                } else {
                    Home.this.showRequestTrolley();
                }
            }
        });
    }

    private void showUserDetails() {
        this.userFullName.setVisibility(View.VISIBLE);
        this.userAddress.setVisibility(View.VISIBLE);
        this.userDetailsProgressBar.setVisibility(View.GONE);
    }

    ;

    private void loadingUserDetails() {

        this.userFullName.setVisibility(View.GONE);
        this.userAddress.setVisibility(View.GONE);
        this.userDetailsProgressBar.setVisibility(View.VISIBLE);
    }

    private void fillUserDetailsInBackground() {
        this.loadingUserDetails();
        this.setUserDetails(ParseUser.getCurrentUser());
        ParseQuery<ParseObject> addressQuery = ParseQuery.getQuery("DeliveryAddress");
        addressQuery.whereEqualTo("user", ParseUser.getCurrentUser());
        addressQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException exception) {
                if (exception == null) {
                    Home.this.setAddressDetails(parseObjects.get(0));
                } else {
                    Toast.makeText(Home.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                }
                Home.this.showUserDetails();
            }

        });
    }

    private void setAddressDetails(ParseObject parseObject) {
        this.addressParseObject = parseObject;
        this.userAddress.setText(this.getAddressAsString());
    }

    private String getAddressAsString() {
        String location = (String) addressParseObject.get("location");
        String apartment = (String) addressParseObject.get("apartment");
        String flat = (String) addressParseObject.get("flat");
        String address = flat + "," + apartment + "," + location;
        return address;
    }

    private void setUserDetails(ParseUser parseUser) {
        String name = (String) parseUser.get("name");
        this.userFullName.setText(name);
    }

    private void showInfo() {

    }

    private void logout() {
        ParseUser.logOut();
        startActivity(new Intent(this, LogIn.class));
        finish();
    }
    
}
