package com.trolley.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.trolley.R;
import com.trolley.Services.UserServices;
import com.trolley.custom.CustomFragment;
import com.trolley.model.User;
import com.trolley.utils.InputValidation;

/**
 * The Class SignUp is the Fragment class that is launched when the user clicks on
 * SignUp option in Left navigation drawer and this is also used as a default
 * fragment for MainActivity. It simply shows an Edittext to enter the address.
 * You need to write actual code for searching the contents based on the address
 * entered by user.
 */
public class SignUp extends CustomFragment {

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */

    private EditText nameElement, emailElement, passwordElement, phoneElement, flatElement, apartmentElement, localityElement;

    private void initializeVariables(View view) {
        this.nameElement = (EditText) view.findViewById(R.id.signup_name);
        this.phoneElement = (EditText) view.findViewById(R.id.signup_phoneNo);
        this.passwordElement = (EditText) view.findViewById(R.id.signup_password);
        this.emailElement = (EditText) view.findViewById(R.id.signup_emailAddress);
        this.flatElement = (EditText) view.findViewById(R.id.signup_flat);
        this.apartmentElement = (EditText) view.findViewById(R.id.signup_Apartment);
        this.localityElement = (EditText) view.findViewById(R.id.signup_Locality);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup, null);
        this.initializeVariables(view);
        setTouchNClick(view.findViewById(R.id.signup_register));
        setTouchNClick(view.findViewById(R.id.signup_sign));
        return view;
    }

    /* (non-Javadoc)
     * @see com.trolley.custom.CustomFragment#onClick(android.view.View)
     */
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.signup_register:
                try {
                    this.validateInputValues();
                    User user = this.createUser();
                    UserServices userServices = new UserServices();
                    userServices.addUserAccount(user, getActivity());
                } catch (InputValidation.ValidationException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.signup_sign:
                startActivity(new Intent(getActivity(), LogIn.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }


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


}
