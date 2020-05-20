package comp5216.sydney.edu.au.garbagecollection;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import comp5216.sydney.edu.au.garbagecollection.mode.Request;
import comp5216.sydney.edu.au.garbagecollection.util.ConvertUtil;

/**
 * show requests by {@link ListView}
 */
public class RequestsActivity extends AppCompatActivity {


    private ListView listView;

    private FirebaseFirestore db;
    private ItemRequestAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.list_view);
//        radioGroup.setOnCheckedChangeListener(this);
        db = FirebaseFirestore.getInstance();
        listView.setAdapter(mAdapter = new ItemRequestAdapter(this));
//        listView.setOnItemClickListener(this);
//        listView.setOnItemLongClickListener(this);
        fetchData();
    }

    /**
     * fetch data from firebase server
     */
    private void fetchData() {
        // gets the status of the selection
//        String status = radioGroup.getCheckedRadioButtonId() == R.id.uncompleted
//                ? Request.STATUS_UNCOMPLETED : Request.STATUS_COMPLETED;
        // find all requests that match the criteria
        db.collection("bookings")
                // conditions
                .whereEqualTo("user id",  db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // 1. get requests
                            // 2. 2et it to the listview
                            mAdapter.setObjects(ConvertUtil.convertToRequests(task.getResult()));
                        } else {
                            Toast.makeText(RequestsActivity.this, "Error getting documents: "
                                    + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }); //.whereEqualTo("status", status)

    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            //moveTaskToBack(true);
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);

//    }
//    @Override
//    public void onCheckedChanged(RadioGroup radioGroup, int id) {
//        fetchData();

//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        fetchData();

//    }

//    /**
//     * onItemClick of listview
//     * @param adapterView
//     * @param view
//     * @param i
//     * @param l
//     */
//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        // set sSelectedRequest
//        Constant.sSelectedRequest = mAdapter.getItem(i);
//        // go to RequestDetailActivity
//        startActivity(new Intent(this, RequestDetailActivity.class));
//    }

    /**
     * onItemLongClick of listview
     * @param adapterView
     * @param view
     * @param index
     * @param l
     * @return
     */
//    @Override
//    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int index, long l) {
//        // gets the selected request
//        final Request request = mAdapter.getItem(index);
//        // show dialog
//        new AlertDialog.Builder(this)
//                .setTitle("Tip")
//                .setMessage("confirm deleting this item ?")
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        // del request from firebase
//                        db.collection("bookings")
//                                .document(request.getId())
//                                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(RequestsActivity.this, "delete the success.", Toast.LENGTH_SHORT).show();
//                                // get request's user info
//                                request.getUser().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                        if (task.isSuccessful()) {
//                                            // update user's booking id
//                                            ArrayList<String> bookingIds = (ArrayList<String>) task.getResult().get("booking id");
//                                            if (bookingIds != null && !bookingIds.isEmpty()) {
//                                                bookingIds.remove(request.getId());
//                                                request.getUser().update("booking id", bookingIds);
//                                            }
//                                        }
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(RequestsActivity.this,
//                                                "network abnormal, please check network connection.", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                                fetchData();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(RequestsActivity.this, "delete fail, " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                })
//                .setNegativeButton("Cancel", null)
//                // show dialog
//                .show();
//        return true;
//    }
}
