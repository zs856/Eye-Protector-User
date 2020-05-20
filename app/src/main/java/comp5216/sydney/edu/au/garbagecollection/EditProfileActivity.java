package comp5216.sydney.edu.au.garbagecollection;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = "EditProfileActivity";

    Button btn_save_edit;
    TextView btn_cancel_edit;
    EditText first_name_edit;
    EditText last_name_edit;
    EditText address_edit;
    EditText postcode_edit;
    String local_first_name;
    String local_last_name;
    String local_address;
    String local_postcode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        first_name_edit = findViewById(R.id.first_name_edit);
        last_name_edit = findViewById(R.id.last_name_edit);
        address_edit = findViewById(R.id.address_edit);
        postcode_edit = findViewById(R.id.postcode_edit);
        btn_save_edit = findViewById(R.id.btn_save_edit);
        btn_cancel_edit = findViewById(R.id.btn_cancel_edit);
        btn_save_edit.setEnabled(false);
        loadDataFromDatabase();
        first_name_edit.addTextChangedListener(inputTextWatcher);
        last_name_edit.addTextChangedListener(inputTextWatcher);
        address_edit.addTextChangedListener(inputTextWatcher);
        postcode_edit.addTextChangedListener(inputTextWatcher);

        btn_save_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                local_first_name = first_name_edit.getText().toString().trim();
                local_last_name = last_name_edit.getText().toString().trim();
                local_address = address_edit.getText().toString();
                local_postcode = postcode_edit.getText().toString().trim();
                updateDataToDatabase("first name", local_first_name);
                updateDataToDatabase("last name", local_last_name);
                updateDataToDatabase("address", local_address);
                updateDataToDatabase("postcode", local_postcode);
                onBackPressed();
            }
        });

        btn_cancel_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private TextWatcher inputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            local_first_name = first_name_edit.getText().toString().trim();
            local_last_name = last_name_edit.getText().toString().trim();
            btn_save_edit.setEnabled(
                    local_first_name != null
                            && local_last_name != null
                            && local_address != null
                            && local_postcode != null
                            && !local_first_name.isEmpty()
                            && !local_last_name.isEmpty()
                            && !local_address.isEmpty()
                            && !local_postcode.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void loadDataFromDatabase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        local_first_name = document.getData().get("first name").toString();
                        local_last_name = document.getData().get("last name").toString();
                        Log.d(TAG, "onComplete: local_last_name " + local_last_name);
                        local_address = document.getData().get("address").toString();
                        local_postcode = document.getData().get("postcode").toString();
                        setEditTextDefault();
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void updateDataToDatabase(String key, String value) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(user.getUid());
        docRef
                .update(key, value)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    private void setEditTextDefault() {
        first_name_edit.setText(local_first_name);
        last_name_edit.setText(local_last_name);
        address_edit.setText(local_address);
        postcode_edit.setText(local_postcode);
    }
}
