package com.trolley.Services;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.trolley.model.DeliveryAddress;
import com.trolley.utils.CONSTS;

/**
 * Created by sunny on 22/3/2015.
 */
public class DeliveryAddressServices {
    private Activity activity;

    public boolean addDeliveryAddress(DeliveryAddress address, Activity activity) {

        this.activity = activity;
        ConnectivityManager connMgr = (ConnectivityManager)
                this.activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            ParseObject deliveryObject = new ParseObject("DeliveryAddress");
            deliveryObject.put("user", ParseUser.getCurrentUser());
            deliveryObject.put("flat", address.getFlat());
            deliveryObject.put("location",address.getLocality());
            deliveryObject.put("apartment",address.getApartment());
            deliveryObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException exception) {
                    if (exception == null) {
                        Toast.makeText(DeliveryAddressServices.this.activity, CONSTS.DELIVERY_ADDRESS_ADDED, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DeliveryAddressServices.this.activity, CONSTS.DELIVERY_ADDRESS_FAILED_TO_ADD, Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Toast.makeText(this.activity, CONSTS.NO_INTERNET_AVAILABLE, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }
}
