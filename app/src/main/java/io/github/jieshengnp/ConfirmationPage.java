package io.github.jieshengnp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ConfirmationPage extends AppCompatActivity {
    ProgressBar confirmBar1,confirmBar2,confirmBar3;
    Button confirmBtn, cancelBtn;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://ocbc-team4-2b3ee-default-rtdb.asia-southeast1.firebasedatabase.app/");
    String applicationId, deviceId;
    Application application;
    List<Application> applicationList = new ArrayList<>();
    DatabaseReference mDatabase = firebaseDatabase.getReference();
    TextView confirmTitle1, confirmName1, confirmNationality1, confirmNRIC1, confirmRace1, confirmDOB1, confirmGender1, confirmPostal1, confirmStreet1, confirmBlock1, confirmUnit1, confirmMobile1, confirmEmail1, confirmOccupation1, confirmMarital1, confirmTitle2, confirmName2, confirmNationality2, confirmNRIC2, confirmRace2, confirmDOB2, confirmGender2, confirmPostal2, confirmStreet2, confirmBlock2, confirmUnit2, confirmMobile2, confirmEmail2, confirmOccupation2, confirmMarital2, confirmWelcome;
    Applicant applicant1, applicant2, currentApplicant;
    AlertDialog.Builder adBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);

        confirmBar1 = findViewById(R.id.confirmBar1);
        confirmBar2 = findViewById(R.id.confirmBar2);
        confirmBar3 = findViewById(R.id.confirmBar3);
        confirmBar1.setProgress(100);
        confirmBar2.setProgress(100);
        confirmBar3.setProgress(100);

        confirmTitle1 = findViewById(R.id.confirmTitle1);
        confirmName1 = findViewById(R.id.confirmName1);
        confirmNationality1 = findViewById(R.id.confirmNationality1);
        confirmNRIC1 = findViewById(R.id.confirmNRIC1);
        confirmRace1 = findViewById(R.id.confirmRace1);
        confirmDOB1 = findViewById(R.id.confirmDOB1);
        confirmGender1 = findViewById(R.id.confirmGender1);
        confirmPostal1 = findViewById(R.id.confirmPostal1);
        confirmStreet1 = findViewById(R.id.confirmStreet1);
        confirmBlock1 = findViewById(R.id.confirmBlock1);
        confirmUnit1 = findViewById(R.id.confirmUnit1);
        confirmMobile1 = findViewById(R.id.confirmMobile1);
        confirmEmail1 = findViewById(R.id.confirmEmail1);
        confirmOccupation1 = findViewById(R.id.confirmOccupation1);
        confirmMarital1 = findViewById(R.id.confirmMarital1);

        confirmTitle2 = findViewById(R.id.confirmTitle2);
        confirmName2 = findViewById(R.id.confirmName2);
        confirmNationality2 = findViewById(R.id.confirmNationality2);
        confirmNRIC2 = findViewById(R.id.confirmNRIC2);
        confirmRace2 = findViewById(R.id.confirmRace2);
        confirmDOB2 = findViewById(R.id.confirmDOB2);
        confirmGender2 = findViewById(R.id.confirmGender2);
        confirmPostal2 = findViewById(R.id.confirmPostal2);
        confirmStreet2 = findViewById(R.id.confirmStreet2);
        confirmBlock2 = findViewById(R.id.confirmBlock2);
        confirmUnit2 = findViewById(R.id.confirmUnit2);
        confirmMobile2 = findViewById(R.id.confirmMobile2);
        confirmEmail2 = findViewById(R.id.confirmEmail2);
        confirmOccupation2 = findViewById(R.id.confirmOccupation2);
        confirmMarital2 = findViewById(R.id.confirmMarital2);

        confirmBtn = findViewById(R.id.confirmBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        adBuilder = new AlertDialog.Builder(ConfirmationPage.this);

        deviceId = Settings.Secure.getString(getBaseContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        // ATTENTION: This was auto-generated to handle app links.
        if (!handleConfirmationDeepLink()) {
            Intent getIn = getIntent();
            application = (Application) getIn.getSerializableExtra("Application");
            ObtainDataFromFirebase(application.getApplicationID());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleConfirmationDeepLink();
    }

    private boolean handleConfirmationDeepLink() {
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        if(appLinkData != null){
            applicationId = appLinkData.getLastPathSegment();
            ObtainDataFromFirebase(applicationId);
            return true;
        }
        return false;
    }

    private void ObtainDataFromFirebase(String applicationId) {
        mDatabase.child("Application").child(applicationId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    application = task.getResult().getValue(Application.class);
                    Log.d("NAME", application.getApplicantList().get(0).getName());
                    applicant1 = application.getApplicantList().get(0);
                    applicant2 = application.getApplicantList().get(1);
                    UpdateFields();
                }
            }
        });
    }

    private void UpdateFields(){
        confirmTitle1.setText(applicant1.getTitle());
        confirmName1.setText(applicant1.getName());
        confirmNationality1.setText(applicant1.getNationality());
        confirmNRIC1.setText(applicant1.getNRIC());
        confirmRace1.setText(applicant1.getRace());
        confirmDOB1.setText(applicant1.getDOB());
        confirmGender1.setText(applicant1.getGender());
        confirmPostal1.setText(applicant1.getPostal());
        confirmStreet1.setText(applicant1.getStreet());
        confirmBlock1.setText(applicant1.getBlock());
        confirmUnit1.setText(applicant1.getUnit());
        confirmMobile1.setText(applicant1.getMobile());
        confirmEmail1.setText(applicant1.getEmail());
        confirmOccupation1.setText(applicant1.getOccupation());
        confirmMarital1.setText(applicant1.getMartial());
        confirmTitle2.setText(applicant2.getTitle());
        confirmName2.setText(applicant2.getName());
        confirmNationality2.setText(applicant2.getNationality());
        confirmNRIC2.setText(applicant2.getNRIC());
        confirmRace2.setText(applicant2.getRace());
        confirmDOB2.setText(applicant2.getDOB());
        confirmGender2.setText(applicant2.getGender());
        confirmPostal2.setText(applicant2.getPostal());
        confirmStreet2.setText(applicant2.getStreet());
        confirmBlock2.setText(applicant2.getBlock());
        confirmUnit2.setText(applicant2.getUnit());
        confirmMobile2.setText(applicant2.getMobile());
        confirmEmail2.setText(applicant2.getEmail());
        confirmOccupation2.setText(applicant2.getOccupation());
        confirmMarital2.setText(applicant2.getMartial());


        if (deviceId.equals(applicant1.getDeviceId())){
            currentApplicant = applicant1;
        } else if (deviceId.equals(applicant2.getDeviceId())){
            currentApplicant = applicant2;
        }

        if (currentApplicant != null) {
            confirmWelcome = findViewById(R.id.confirmWelcome);
            if(application.getApplicantAccepted().get(currentApplicant.getNRIC()) != null && application.getApplicantAccepted().get(currentApplicant.getNRIC())) {
                confirmWelcome.setText("Welcome " + currentApplicant.getName() + ", you have already confirmed the application.");
                confirmWelcome.setTextColor(Color.GREEN);
                confirmBtn.setVisibility(View.GONE);
                cancelBtn.setVisibility(View.GONE);
            } else if (application.getApplicantAccepted().containsValue(false)){
                confirmWelcome.setText("This application has been deemed void by one of the applicant.");
                confirmWelcome.setTextColor(Color.RED);
                confirmBtn.setVisibility(View.GONE);
                cancelBtn.setVisibility(View.GONE);
            } else {
                confirmWelcome.setText(confirmWelcome.getText() + " " + currentApplicant.getName() + "!");
                confirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adBuilder.setTitle("Are you sure?");
                        adBuilder.setMessage("Press Yes to confirm and give consent for the application, otherwise No to return back to the details page.");
                        adBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                application.getApplicantAccepted().put(currentApplicant.getNRIC(), true);
                                mDatabase.child("Application").child(application.getApplicationID()).setValue(application);
                            }
                        });
                        adBuilder.setNegativeButton("No", null);
                        adBuilder.setCancelable(false);
                        adBuilder.show();
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adBuilder.setTitle("Are you sure?");
                        adBuilder.setMessage("Press Yes to confirm and cancel the entire application, otherwise No to return back to the details page.");
                        adBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                application.getApplicantAccepted().put(currentApplicant.getNRIC(), false);
                                mDatabase.child("Application").child(application.getApplicationID()).setValue(application);
                            }
                        });
                        adBuilder.setNegativeButton("No", null);
                        adBuilder.setCancelable(false);
                        adBuilder.show();
                    }
                });
            }
        }

    }
}