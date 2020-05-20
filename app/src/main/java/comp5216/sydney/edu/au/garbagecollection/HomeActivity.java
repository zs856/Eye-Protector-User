package comp5216.sydney.edu.au.garbagecollection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * The home page. Contains 4 sectors which links to the main functionality of the application.
 */
public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkUserLoginState();

    }

    void checkUserLoginState() {
        //  if user not logged in, redirect to login page, hard code for testing now
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void onImageClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ib_diagnose:
                intent = new Intent(HomeActivity.this,MakeDiagnoseActivity.class);
                break;
            case R.id.ib_chat:
                intent = new Intent(HomeActivity.this, ChatActivity.class);
                break;
            case R.id.ib_appointment:
                intent = new Intent(HomeActivity.this, MakeAppointmentActivity.class);
                break;
            case R.id.ib_profile:
                intent = new Intent(HomeActivity.this, ProfileActivity.class);
        }
        startActivity(intent);
    }


}
