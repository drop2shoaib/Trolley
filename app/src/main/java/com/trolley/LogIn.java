package com.trolley;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.trolley.utils.CONSTS;

/**
 * Created by sunny on 22/3/2015.
 */
public class LogIn extends Activity implements View.OnClickListener {
    private EditText emailAddress, password;
    private Button signIn;
    private TextView signUp;
    private ProgressBar spinner;
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
        this.signIn = (Button) view.findViewById(R.id.signin_signit);
        this.signIn.setOnClickListener(this);
        this.signUp = (TextView) view.findViewById(R.id.signin_signup);
        this.signUp.setOnClickListener(this);
        this.spinner= (ProgressBar) view.findViewById(R.id.login_Progress_bar);
        stopSpinner();
    }

    private void startSpinner(){
       this.spinner.setVisibility(View.VISIBLE);
    }
    private void stopSpinner(){
        this.spinner.setVisibility(View.GONE);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin_signit:
                startSpinner();
                ParseUser.logInInBackground(this.emailAddress.getText().toString(), this.password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException exception) {
                        if (user != null) {
//                            Toast.makeText(LogIn.this, CONSTS.LOGGED_IN_SUCCESSFULLY, Toast.LENGTH_LONG);
                            startActivity(new Intent(LogIn.this, Home.class));
                            finish();
                        } else {
                            Toast.makeText(LogIn.this, CONSTS.LOGGING_FAILED, Toast.LENGTH_LONG).show();
                        }
                        stopSpinner();
                    }
                });
                break;
            case R.id.signin_signup:
                startActivity(new Intent(LogIn.this, SignUp.class));
                finish();
                break;
        }
    }

}
