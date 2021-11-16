package io.github.jieshengnp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EnterApplicationCodeActivity extends AppCompatActivity {

    EditText applicationCodeTxt;
    Button continueBtn;
    Application application;
    List<Application> applicationList = new ArrayList<>();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://ocbc-team4-2b3ee-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_application_code);

        mDatabase.child("Application").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                applicationList = new ArrayList<>();
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    applicationList.add(eventSnapshot.getValue(Application.class));
                }

                Log.d("SIZE", "" + applicationList.size());
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
                for(Application application : applicationList){
                    if(applicationCodeTxt.getText().toString().equals(application.getApplicationCode())){
                        Log.d("TEST", "TEST");
                        Bundle extras = new Bundle();
                        extras.putSerializable("Application", (Serializable) application);
                        Intent in = new Intent(EnterApplicationCodeActivity.this, ApplicationFormActivity.class);
                        in.putExtras(extras);
                        startActivity(in);
                    }
                }
            }
        });

    }
}