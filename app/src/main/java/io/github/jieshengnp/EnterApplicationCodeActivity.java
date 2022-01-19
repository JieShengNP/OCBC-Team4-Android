package io.github.jieshengnp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EnterApplicationCodeActivity extends AppCompatActivity {

    EditText applicationCodeTxt;
    Button continueBtn;
    Application application;
    List<Application> applicationList = new ArrayList<>();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://ocbc-team4-2b3ee-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();
    private String android_id;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_application_code);

        android_id = Settings.Secure.getString(getBaseContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.d("DeviceId", android_id);
        mDatabase.child("Application").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                applicationList = new ArrayList<>();
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    applicationList.add(eventSnapshot.getValue(Application.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

//      Initialise
        applicationCodeTxt = findViewById(R.id.applicationCodeTxt);
        continueBtn = findViewById(R.id.continueBtn);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean found = false;
                for(Application application : applicationList){
                    if(applicationCodeTxt.getText().toString().equals(application.getApplicationCode())) {
                        found = true;
                        Bundle extras = new Bundle();
                        extras.putSerializable("Application", application);
                        Intent in;
                        if (application.getApplicantList().get(0).getDeviceId().equals(android_id) && application.getApplicantList().size() == 1) {
                            in = new Intent(EnterApplicationCodeActivity.this, SendApplicationCodeActivity.class);
                        } else if (application.getApplicantList().size() == 2 && (application.getApplicantList().get(0).getDeviceId().equals(android_id) || application.getApplicantList().get(1).getDeviceId().equals(android_id))) {
                            in = new Intent(EnterApplicationCodeActivity.this, ConfirmationPage.class);
                        } else if (application.getApplicantList().size() == 2){
                            // Disable Application if there's already 2 applicants and none of the device ID matches
                            found = false;
                            break;
                        } else {
                            in = new Intent(EnterApplicationCodeActivity.this, SelectApplicationType.class);
                        }
                        in.putExtras(extras);
                        startActivity(in);
                    }
                }
                if(found == false){
                    Toast.makeText(getApplicationContext(), "Invalid application code, please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}