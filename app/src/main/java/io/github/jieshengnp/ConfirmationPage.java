package io.github.jieshengnp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

public class ConfirmationPage extends AppCompatActivity {
ProgressBar progressbar4,progressbar5,progressbar6;
ImageView uploadedSelfie;
Button uploadSelfie;
ActivityResultLauncher<Intent> activityResultLauncher;
Bitmap imageBitmap;
private static final int REQUEST_CAMERA_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);
        progressbar4= findViewById(R.id.progressBar4);
        progressbar5 = findViewById(R.id.progressBar5);
        uploadedSelfie = findViewById(R.id.imageViewSelfie);
        uploadSelfie = findViewById(R.id.uploadSelfie);
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
    }
}