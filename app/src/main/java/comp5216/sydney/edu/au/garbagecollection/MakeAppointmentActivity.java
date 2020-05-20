package comp5216.sydney.edu.au.garbagecollection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Activity for user to send a request to book a appointment time.
 * User can edit Contact detail and appointment time.
 */
public class MakeAppointmentActivity extends AppCompatActivity {


    TextView mNameView;
    TextView mPhoneView;
    TextView mAddressView;
    TextView mPostcodeView;
    TextView mTimeView;
    TextView mDateView;
    LinearLayout mContactLayout;
    LinearLayout mCollectionLayout;

    final String PREFERENCES = "user info";

    String name;
    String phone;
    String address;
    String postcode;
    String time;
    String date;

    String userId;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);

        mContactLayout = findViewById(R.id.tv_contact_layout);
        mCollectionLayout = findViewById(R.id.tv_collection_layout);
        mTimeView = findViewById(R.id.tv_confirm_request_time_value);
        mDateView = findViewById(R.id.tv_confirm_request_day_value);
        time = mTimeView.getText().toString();
        date = mDateView.getText().toString();

        updateUserFields();
        setupListeners();
    }

    void setupListeners(){
        mContactLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new PopUpUtil().editFormPopUp(MakeAppointmentActivity.this,
                        name, phone, address, postcode, mNameView, mPhoneView,mAddressView,
                        mPostcodeView,mContactLayout);


            }
        });

        mCollectionLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new PopUpUtil().collectionFormPopUp(MakeAppointmentActivity.this, mTimeView, mDateView, mContactLayout);

            }
        });

    }

    void updateUserFields(){
        mNameView = findViewById(R.id.tv_username_value);
        mPhoneView = findViewById(R.id.tv_phone_value);
        mAddressView = findViewById(R.id.tv_address_value);
        mPostcodeView = findViewById(R.id.tv_postcode_value);
        sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        name = sharedPreferences.getString("firstname","")+" " + sharedPreferences.getString("lastname","");
        phone = sharedPreferences.getString("phone", "");
        address = sharedPreferences.getString("address","");
        postcode = sharedPreferences.getString("postcode","");
        userId = sharedPreferences.getString("id","");

        mNameView.setText(name);
        mPhoneView.setText(phone);
        mAddressView.setText(address);
        mPostcodeView.setText(postcode);
    }


    public void onSubmit(View view){
        time = mTimeView.getText().toString();
        date = mDateView.getText().toString();
        phone = mPhoneView.getText().toString();
        address = mAddressView.getText().toString();
        postcode = mPostcodeView.getText().toString();
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String schedule = date+" "+time+":00";
        Date date = new Date();
        try{
            date=formatter.parse(schedule);
        }catch(ParseException e){
            e.printStackTrace();
        }

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference uid = db.collection("users").document(userId);
        Map<String, Object> docData = new HashMap<>();

        docData.put("address", address+ ", "+ postcode);
        docData.put("created time", new Timestamp(new Date()));
        docData.put("scheduled time", new Timestamp(date));
        docData.put("first name", sharedPreferences.getString("firstname",""));
        docData.put("last name", sharedPreferences.getString("lastname", ""));
        docData.put("phone number", phone);
        docData.put("status", "uncompleted");
        docData.put("user id",uid);

        if(phone.equals("")){
            Toast.makeText(MakeAppointmentActivity.this, "Please enter phone number", Toast.LENGTH_LONG).show();
        }else if(address.equals("")){
            Toast.makeText(MakeAppointmentActivity.this, "Please enter address", Toast.LENGTH_LONG).show();
        }else if(postcode.equals("")){
            Toast.makeText(MakeAppointmentActivity.this, "Please enter Post Code", Toast.LENGTH_LONG).show();
        }else if(name.equals("")){
            Toast.makeText(MakeAppointmentActivity.this, "Please enter Contact name", Toast.LENGTH_LONG).show();
        }else{
           db.collection("bookings")
                    .add(docData)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            //Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                             Toast.makeText(MakeAppointmentActivity.this, "Request has been sent", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Log.w(TAG, "Error adding document", e);
                        }
                    });
        }
    }
}
