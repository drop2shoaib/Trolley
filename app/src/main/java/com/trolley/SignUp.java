package com.trolley;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.trolley.Services.DeliveryAddressServices;
import com.trolley.Services.UserServices;
import com.trolley.model.DeliveryAddress;
import com.trolley.model.User;
import com.trolley.utils.CONSTS;
import com.trolley.utils.InputValidation;

/**
 * The Class SignUp is the Fragment class that is launched when the user clicks on
 * SignUp option in Left navigation drawer and this is also used as a default
 * fragment for MainActivity. It simply shows an Edittext to enter the address.
 * You need to write actual code for searching the contents based on the address
 * entered by user.
 */
public class SignUp extends Activity implements View.OnClickListener {

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */

    private EditText nameElement, emailElement, passwordElement, phoneElement, flatElement, apartmentElement, localityElement;
    private ProgressBar signUpProgressBar;

    private void initializeVariables(Activity view) {
        this.nameElement = (EditText) view.findViewById(R.id.signup_name);
        this.phoneElement = (EditText) view.findViewById(R.id.signup_phoneNo);
        this.passwordElement = (EditText) view.findViewById(R.id.signup_password);
        this.emailElement = (EditText) view.findViewById(R.id.signup_emailAddress);
        this.flatElement = (EditText) view.findViewById(R.id.signup_flat);
        this.apartmentElement = (EditText) view.findViewById(R.id.signup_apartment);
        this.localityElement = (EditText) view.findViewById(R.id.signup_locality);
        this.signUpProgressBar = (ProgressBar) view.findViewById(R.id.signup_progress_bar);
        stopSpinner();
    }

    private void stopSpinner() {
        this.signUpProgressBar.setVisibility(View.GONE);
    }

    private void startSpinner() {
        this.signUpProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.signup);
        this.initializeVariables(this);
        findViewById(R.id.signup_register).setOnClickListener(this);
        findViewById(R.id.signup_sign).setOnClickListener(this);
    }

    /* (non-Javadoc)
     * @see com.trolley.custom.CustomFragment#onClick(android.view.View)
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_register:
                try {
                    this.validateInputValues();

                } catch (InputValidation.ValidationException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                this.addUserAccount(this.createUser());
                this.addDeliveryAddress(this.createDeliveryAddress());

                break;
            case R.id.signup_sign:
                startActivity(new Intent(getActivity(), LogIn.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
        }


    }

    private DeliveryAddress createDeliveryAddress() {
        String apartment = this.apartmentElement.getText().toString();
        String locality = this.localityElement.getText().toString();
        String flatNumber = this.flatElement.getText().toString();
        return new DeliveryAddress(locality, apartment, flatNumber);

    }

    private boolean validateInputValues() throws InputValidation.ValidationException {

        if (InputValidation.validateName(this.nameElement.getText().toString()) && InputValidation.validateEmailPassword(this.emailElement.getText().toString())
                && InputValidation.validatePassword(this.passwordElement.getText().toString()) && InputValidation.validatePhone(this.phoneElement.getText().toString())
                && InputValidation.validateFlat(this.flatElement.getText().toString()) && InputValidation.validateApartment(this.apartmentElement.getText().toString())
                && InputValidation.validateLocality(this.localityElement.getText().toString())) {
            return true;
        }
        return false;
    }


    private User createUser() {
        String userName = this.nameElement.getText().toString();
        String email = this.emailElement.getText().toString();
        String password = this.passwordElement.getText().toString();
        String phone = this.phoneElement.getText().toString();
        return new User(userName, phone, email, password, true);
    }

    public void addUserAccount(User user) {
        //            object.put("emailVerified", user.isEmailVerified());
        ConnectivityManager connMgr = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            ParseUser userOBJ = new ParseUser();
            userOBJ.setUsername(user.getEmail());
            userOBJ.setPassword(user.getPassword());
            userOBJ.setEmail(user.getEmail());
            userOBJ.put("name", user.getName());
            userOBJ.put("phone", user.getPhone());
            this.startSpinner();
            userOBJ.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException exception) {
                    if (exception == null) {
//                        Toast.makeText(SignUp.this, "SuccessFul", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(SignUp.this, CONSTS.SIGNUP_FAILED, Toast.LENGTH_LONG).show();
                    }
                }

            });
        } else {
            Toast.makeText(SignUp.this, CONSTS.NO_INTERNET_AVAILABLE, Toast.LENGTH_LONG).show();
        }

    }

    public void addDeliveryAddress(DeliveryAddress address) {

        ConnectivityManager connMgr = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (ParseUser.getCurrentUser() == null) {
//                Toast.makeText(SignUp.this, CONSTS., Toast.LENGTH_LONG).show();
                return;
            }
            ParseObject deliveryObject = new ParseObject("DeliveryAddress");
            deliveryObject.put("user", ParseUser.getCurrentUser());
            deliveryObject.put("flat", address.getFlat());
            deliveryObject.put("location", address.getLocality());
            deliveryObject.put("apartment", address.getApartment());
            deliveryObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException exception) {
                    if (exception == null) {
//                        Toast.makeText(SignUp.this, CONSTS.DELIVERY_ADDRESS_ADDED, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getActivity(), Home.class));
                        finish();
                    } else {
//                        Toast.makeText(SignUp.this, CONSTS.DELIVERY_ADDRESS_FAILED_TO_ADD, Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, CONSTS.NO_INTERNET_AVAILABLE, Toast.LENGTH_LONG).show();
        }

    }

    public Activity getActivity() {
        return this;
    }
}
