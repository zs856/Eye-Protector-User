package comp5216.sydney.edu.au.garbagecollection;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;

public class EditContactForm extends LinearLayout {
    EditText mName;
    EditText mPhone;
    EditText mAddress;
    EditText mPostcode;


    public EditContactForm(Context context, AttributeSet attrs, String name, String phone, String address, String postcode){
        super(context, attrs);
        inflate(context, R.layout.form_contact, this);

        mName = findViewById(R.id.username_value);
        mPhone = findViewById(R.id.phone_value);
        mAddress = findViewById(R.id.address_value);
        mPostcode = findViewById(R.id.postcode_value);

        mName.setText(name);
        mPhone.setText(phone);
        mAddress.setText(address);
        mPostcode.setText(postcode);

    }

}
