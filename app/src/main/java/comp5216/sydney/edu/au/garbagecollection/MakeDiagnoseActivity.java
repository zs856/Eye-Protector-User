package comp5216.sydney.edu.au.garbagecollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import comp5216.sydney.edu.au.garbagecollection.util.Classifier;
import comp5216.sydney.edu.au.garbagecollection.util.Utils;

public class MakeDiagnoseActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 7;
    int cameraRequestCode = 001;
    int REQUEST_CODE_PICK_IMAGE =002;
    Classifier classifier;
    ImageView imageView;
    TextView textView;
    public final String APP_TAG = "CS53";


    String userId;

    SharedPreferences sharedPreferences;
    final String PREFERENCES = "user info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose);
<<<<<<< Updated upstream
<<<<<<< HEAD
=======
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

>>>>>>> parent of 5a31eb3... I have changed the model to our own
        classifier = new Classifier(Utils.assetFilePath(this,"mobilenet-v2.pt"));
=======
        classifier = new Classifier(Utils.assetFilePath(this,"mynet_new_2.pt"));
>>>>>>> Stashed changes

        TextView capture = findViewById(R.id.ib_subRequest);
        imageView = findViewById(R.id.diagnose_eye_image);
        textView = findViewById(R.id.diagnose_level);
        capture.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                //第二个参数是需要申请的权限

                choosePhone(view);

            }


        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String pred = null;
        Bitmap bit = null;
        Uri uri = null;

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK){
            try {
                /**
                 * 该uri是上一个Activity返回的
                 */

                uri = data.getData();
                bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                pred = classifier.predict(bit);

                imageView.setImageBitmap(bit);
                textView.setText(pred);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("tag", e.getMessage());
                Toast.makeText(this, "程序崩溃", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Log.i("liang", "失败");
        }


    }

    public void choosePhone(View view){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);

        }else {
            choosePhoto();
        }
    }
    void choosePhoto(){
        /**
         * 打开选择图片的界面
         */
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);

    }
}
