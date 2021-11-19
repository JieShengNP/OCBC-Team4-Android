package io.github.jieshengnp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfirmationPage extends AppCompatActivity {
    ProgressBar progressbar4,progressbar5,progressbar6;
    Button accCreate1,accCreate2;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://ocbc-team4-2b3ee-default-rtdb.asia-southeast1.firebasedatabase.app/");

    DatabaseReference mDatabase = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);
//        progressbar4= findViewById(R.id.progressBar4);
//        progressbar5 = findViewById(R.id.progressBar5);
//        progressbar4.setProgress(100);
//        progressbar5.setProgress(100);
//
//        accCreate1=findViewById(R.id.accCreate1);
//        accCreate2=findViewById(R.id.accCreate2);
//
//        Intent receivIntent = getIntent();
//        Application application = (Application)receivIntent.getSerializableExtra("Application");
//        Applicant firstApplicant = application.getApplicantList().get(0);
//        Applicant secondApplicant = application.getApplicantList().get(1);
//
//
//        //On click Confirm for both parties.
//
//
//
//
//        //First User
//        Boolean isSingPass1 = firstApplicant.getIsSingPass();
//        String Title1 = firstApplicant.getTitle();
//        String Name1 =  firstApplicant.getName();
//        String Nationality1 =firstApplicant.getNationality();
//        String NRIC1 =firstApplicant.getNRIC();
//        String Race1 =firstApplicant.getRace();
//        String DOB1 =firstApplicant.getDOB();
//        String Gender1 =firstApplicant.getGender();
//        String Postal1 =firstApplicant.getPostal();
//        String Street1 =firstApplicant.getStreet();
//        String Block1 =firstApplicant.getBlock();
//        String Unit1 =firstApplicant.getUnit();
//        String Mobile1 =firstApplicant.getMobile();
//        String Email1 =firstApplicant.getEmail();
//        String Occupation1 = firstApplicant.getOccupation();
//        String Martial1 = firstApplicant.getMartial();
////Unsure of Hash for accounts in User.
//        User U1 = new User(isSingPass1,Title1,Name1,Nationality1,NRIC1,Race1,DOB1,Gender1,Postal1,Street1,Block1,Unit1,Mobile1,Email1,Occupation1,Martial1);
//        String keyU1 = mDatabase.child("User").push().getKey();
//        mDatabase.child("User").child(keyU1).setValue(U1);
//
//
//
//
//        Boolean isSingPass2 = secondApplicant.getIsSingPass();
//        String Title2 =secondApplicant.getTitle();
//        String Name2 =  secondApplicant.getName();
//        String Nationality2 =secondApplicant.getNationality();
//        String NRIC2 =secondApplicant.getNRIC();
//        String Race2 =secondApplicant.getRace();
//        String DOB2 =secondApplicant.getDOB();
//        String Gender2 =secondApplicant.getGender();
//        String Postal2 =secondApplicant.getPostal();
//        String Street2 =secondApplicant.getStreet();
//        String Block2 =secondApplicant.getBlock();
//        String Unit2 =secondApplicant.getUnit();
//        String Mobile2 =secondApplicant.getMobile();
//        String Email2 =secondApplicant.getEmail();
//        String Occupation2 =secondApplicant.getOccupation();
//        String Martial2 =secondApplicant.getMartial();
////Unsure about Hash for accounts in User.
//        User U2 = new User(isSingPass2,Title2,Name2,Nationality2,NRIC2,Race2,DOB2,Gender2,Postal2,Street2,Block2,Unit2,Mobile2,Email2,Occupation2,Martial2);
//        String keyU2 = mDatabase.child("User").push().getKey();
//        mDatabase.child("User").child(keyU2).setValue(U2);
//
//
//
//
//
//
//
//

    }




}