package com.trolley;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.trolley.custom.CustomFragment;
import com.trolley.utils.CONSTS;

/**
 * Created by sunny on 22/3/2015.
 */
public class LogIn extends Activity implements View.OnClickListener {
    private EditText emailAddress, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.signin);
        this.initializeVariables(this);
        findViewById(R.id.signin_signit).setOnClickListener(this);
    }


    private void initializeVariables(Activity view) {
        this.emailAddress = (EditText) view.findViewById(R.id.signin_email);
        this.password = (EditText) view.findViewById(R.id.signin_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin_signit:
                ParseUser.logInInBackground(this.emailAddress.getText().toString(), this.password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException exception) {
                        if (user != null) {
                            Toast.makeText(LogIn.this, CONSTS.LOGGED_IN_SUCCESSFULLY, Toast.LENGTH_LONG);
                        } else {
                            Toast.makeText(LogIn.this, CONSTS.LOGGING_FAILED, Toast.LENGTH_LONG);
                        }
                    }
                });
                break;
        }
    }
}
