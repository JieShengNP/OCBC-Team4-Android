package io.github.jieshengnp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ConfirmationPage extends AppCompatActivity {
    ProgressBar progressbar4,progressbar5,progressbar6;
    ImageView uploadedSelfie;
    Button uploadSelfie,confirmButton;
    ActivityResultLauncher<Intent> activityResultLauncher;
    Bitmap imageBitmap;
    private static final int REQUEST_CAMERA_CODE = 100;
    StorageReference storageRef = FirebaseStorage.getInstance("gs://ocbc-team4-2b3ee.appspot.com/").getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);
        progressbar4= findViewById(R.id.progressBar4);
        progressbar5 = findViewById(R.id.progressBar5);
        uploadedSelfie = findViewById(R.id.imageViewSelfie);
        uploadSelfie = findViewById(R.id.uploadSelfie);
        confirmButton = findViewById(R.id.uploadConfirmm);
        progressbar4.setProgress(100);
        progressbar5.setProgress(100);


        if (ContextCompat.checkSelfPermission(ConfirmationPage.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(ConfirmationPage.this, new String[]{
                    Manifest.permission.CAMERA


            }, REQUEST_CAMERA_CODE);

        }

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle extras = result.getData().getExtras();
                    imageBitmap = (Bitmap) extras.get("data");
                    uploadedSelfie.setImageBitmap(imageBitmap);


                }

            }
        });

        uploadSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    activityResultLauncher.launch(intent);
                } else {
                    Toast.makeText(ConfirmationPage.this, "Cant Open",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
                byte[] bb = bytes.toByteArray();
                uploadFirebase(bb);
            }
        });

    }

    private void uploadFirebase(byte[]bb){

        StorageReference imagePath = storageRef.child("verificationImgs/test.jpg");
        UploadTask uploadTask = imagePath.putBytes(bb);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(ConfirmationPage.this,"Sucessfully Uploaded Picture",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ConfirmationPage.this,"Failed To Upload Picture",Toast.LENGTH_SHORT).show();
            }
        });




    }
}