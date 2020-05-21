package comp5216.sydney.edu.au.garbagecollection;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import comp5216.sydney.edu.au.garbagecollection.util.ConvertUtil;

public class ResultsActivity extends AppCompatActivity {
    private ListView listView;

    private FirebaseFirestore db;
    private ItemResultAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose_results);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.results_list_view);
        db = FirebaseFirestore.getInstance();
        listView.setAdapter(mAdapter = new ItemResultAdapter(this));
        fetchData();
    }

    /**
     * fetch data from firebase server
     */
    private void fetchData() {
        // find all requests that match the criteria
        db.collection("diagnosisResults")
                // conditions
                .whereEqualTo("user id",  db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // 1. get requests
                            // 2. 2et it to the listview
                            mAdapter.setObjects(ConvertUtil.convertToResults(task.getResult()));
                        } else {
                            Toast.makeText(ResultsActivity.this, "Error getting documents: "
                                    + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }); //.whereEqualTo("status", status)

    }
}
