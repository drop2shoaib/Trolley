package com.trolley;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.trolley.utils.CONSTS;

/**
 * Created by sunny on 28/3/2015.
 */
public class MyNetworkInfo {
    public static boolean checkConnection(Activity activity) {
        ConnectivityManager connMgr = (ConnectivityManager)
                activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        Toast.makeText(activity, CONSTS.NO_INTERNET_AVAILABLE,Toast.LENGTH_LONG).show();
        return false;
    }
}
