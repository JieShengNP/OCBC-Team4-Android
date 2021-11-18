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



    Button accCreate1,accCreate2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);
        progressbar4= findViewById(R.id.progressBar4);
        progressbar5 = findViewById(R.id.progressBar5);
        progressbar4.setProgress(100);
        progressbar5.setProgress(100);

        accCreate1=findViewById(R.id.accCreate1);
        accCreate2=findViewById(R.id.accCreate2);

        Intent receivIntent = getIntent();






    }




}