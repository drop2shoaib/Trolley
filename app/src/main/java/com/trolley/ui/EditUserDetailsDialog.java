package com.trolley.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseUser;
import com.trolley.R;
import com.trolley.model.DeliveryAddress;
import com.trolley.model.User;
import com.trolley.utils.CONSTS;
import com.trolley.utils.InputValidation;

/**
 * Created by sunny on 29/3/2015.
 */

public class EditUserDetailsDialog extends DialogFragment implements View.OnClickListener {


    @Override
    public void onClick(View v) {
        try {
            this.validateInputValues();
            EditUserDetailDialogListener activity = (EditUserDetailDialogListener) getActivity();
            activity.onFinishEditDialog(createUser(),createDeliveryAddress());
            this.dismiss();
        } catch (InputValidation.ValidationException e) {
            Toast.makeText(this.getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    public interface EditUserDetailDialogListener {
        void onFinishEditDialog(User user, DeliveryAddress deliveryAddress);
    }

    private EditText nameElement, flatElement, apartmentElement, localityElement, phoneElement;
    private Button updateElement;

    public EditUserDetailsDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_user_info_dialog, container);
        nameElement = (EditText) view.findViewById(R.id.edit_user_name);
        nameElement.setText(ParseUser.getCurrentUser().get("name").toString());

        flatElement = (EditText) view.findViewById(R.id.edit_user_flat);
        flatElement.setText(getArguments().get("flat").toString());

        apartmentElement = (EditText) view.findViewById(R.id.edit_user_apartment);
        apartmentElement.setText(getArguments().get("apartment").toString());

        phoneElement = (EditText) view.findViewById(R.id.edit_user_phone);
        phoneElement.setText(ParseUser.getCurrentUser().get("phone").toString());

        localityElement = (EditText) view.findViewById(R.id.edit_user_locality);
        localityElement.setText(getArguments().get("locality").toString());

        updateElement = (Button) view.findViewById(R.id.edit_user_update);
        getDialog().setTitle(CONSTS.EDIT_USER_INFO_TITLE);

        // Show soft keyboard automatically
        updateElement.setOnClickListener(this);

        return view;
    }
    private DeliveryAddress createDeliveryAddress() {
        String apartment = this.apartmentElement.getText().toString();
        String locality = this.localityElement.getText().toString();
        String flatNumber = this.flatElement.getText().toString();
        return new DeliveryAddress(locality, apartment, flatNumber);

    }
    private User createUser() {
        String userName = this.nameElement.getText().toString();
        String phone = this.phoneElement.getText().toString();
        return new User(userName, phone, "", "", true);
    }
    private boolean validateInputValues() throws InputValidation.ValidationException {

        if (InputValidation.validateName(this.nameElement.getText().toString())
                && InputValidation.validatePhone(this.phoneElement.getText().toString())
                && InputValidation.validateFlat(this.flatElement.getText().toString())
                && InputValidation.validateApartment(this.apartmentElement.getText().toString())
                && InputValidation.validateLocality(this.localityElement.getText().toString())) {
            return true;
        }
        return false;
    }
}