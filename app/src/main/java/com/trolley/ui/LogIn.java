package com.trolley.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.trolley.R;
import com.trolley.custom.CustomFragment;
import com.trolley.utils.CONSTS;

/**
 * Created by sunny on 22/3/2015.
 */
public class LogIn extends CustomFragment {
    private EditText emailAddress, password;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signin, null);
        this.initializeVariables(view);
        setTouchNClick(view.findViewById(R.id.signin_signit));
        return view;
    }

    private void initializeVariables(View view) {
        this.emailAddress = (EditText) view.findViewById(R.id.signin_email);
        this.password = (EditText) view.findViewById(R.id.signin_password);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.signin_signit:
                ParseUser.logInInBackground(this.emailAddress.getText().toString(), this.password.getText().toString(), (user, exception) -> {
                    if (user != null) {
                        Toast.makeText(this.getActivity(), CONSTS.LOGGED_IN_SUCCESSFULLY,Toast.LENGTH_LONG);
                    } else {

//                        switch (exception.getCode()){
//                            case ParseException.OBJECT_NOT_FOUND
//                        }
//                        exception.getCode()== ParseException.
                        Toast.makeText(this.getActivity(),CONSTS.LOGGING_FAILED,Toast.LENGTH_LONG);
                        // Signup failed. Look at the ParseException to see what happened.
                    }
                });
                break;
        }
    }
}
