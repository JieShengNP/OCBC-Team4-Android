package io.github.jieshengnp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ConfirmationPage extends AppCompatActivity {
    ProgressBar progressbar4,progressbar5,progressbar6;
    Button accCreate1,accCreate2;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://ocbc-team4-2b3ee-default-rtdb.asia-southeast1.firebasedatabase.app/");
    String applicationId;
    Application application;
    List<Application> applicationList = new ArrayList<>();
    DatabaseReference mDatabase = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);

        // ATTENTION: This was auto-generated to handle app links.
        handleConfirmationDeepLink();

        Intent getIn = getIntent();
        application = (Application) getIn.getSerializableExtra("Application");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleConfirmationDeepLink();
    }

    private void handleConfirmationDeepLink() {
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        if(appLinkData != null){
            applicationId = appLinkData.getLastPathSegment();

            mDatabase.child("Application").child(applicationId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        application = task.getResult().getValue(Application.class);
                        Log.d("NAME", application.getApplicantList().get(0).getName());
                    }
                }
            });
        }
    }


}