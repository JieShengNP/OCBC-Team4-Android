package io.github.jieshengnp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public class ApplicationFormActivity extends AppCompatActivity {

    TextView forgetPwdTxt, box;
    TextInputLayout accessCodeLayout, pinLayout;
    TextInputEditText accessCodeTxt, nameTxt;
    AutoCompleteTextView titleDropdown, countryDropdown, raceDropdown, maritalDropdown;
    Button loginBtn, nextBtn;
    ProgressBar progressBar1, progressBar2, progressBar3;
    String key;
    Applicant applicant;
    Application application;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://ocbc-team4-2b3ee-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();
    String[] salutations = {"Mr", "Mrs", "Ms", "Miss", "Mdm", "Dr"};
    String[] races = {"CHINESE", "EURASIAN", "INDIAN", "MALAY", "OTHER RACES"};
    String[] marital = {"DIVORCED", "MARRIED", "SEPARATED", "SINGLE", "WIDOWED"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_form);

//      Initialise data
        forgetPwdTxt = findViewById(R.id.forgetPwdTxt);
        loginBtn = findViewById(R.id.loginBtn);
        nextBtn = findViewById(R.id.nextBtn);
        accessCodeTxt = findViewById(R.id.accessCodeTxt);
        nameTxt = findViewById(R.id.nameTxt);
        accessCodeLayout = findViewById(R.id.accessCodeLayout);
        pinLayout = findViewById(R.id.pinLayout);
        box = findViewById(R.id.box);
        progressBar1 = findViewById(R.id.progressBar1);
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar3 = findViewById(R.id.progressBar3);

        accessCodeLayout.setVisibility(View.GONE);
        pinLayout.setVisibility(View.GONE);
        loginBtn.setVisibility(View.GONE);
        forgetPwdTxt.setVisibility(View.GONE);

        progressBar1.setProgress(100);

        ConstraintLayout.LayoutParams boxLP = (ConstraintLayout.LayoutParams) box.getLayoutParams();
        boxLP.height -= DipToPixels(237);

        // Add Salutations
        titleDropdown = findViewById(R.id.titleDropdown);
        ArrayAdapter<String> salutationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, salutations);
        titleDropdown.setAdapter(salutationAdapter);

        //Add Nationalities
        countryDropdown = findViewById(R.id.countryDropdown);
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale: locales){
            String country = locale.getDisplayCountry();
            if (country.trim().length()>0 && !countries.contains(country)){
                countries.add(country);
            }
        }
        Collections.sort(countries);
        countries.remove("Singapore");
        countries.add(0, "Singapore");
        ArrayAdapter<String> nationalitiesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countries);
        countryDropdown.setAdapter(nationalitiesAdapter);

        //Add Ethnicity
        raceDropdown = findViewById(R.id.raceDropdown);
        ArrayAdapter<String> racesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, races);
        raceDropdown.setAdapter(racesAdapter);

        //Add Marital Status
        maritalDropdown = findViewById(R.id.maritalDropdown);
        ArrayAdapter<String> martialAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, marital);
        maritalDropdown.setAdapter(martialAdapter);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout til = findViewById(R.id.accessCodeLayout);
                til.setError("Testing error msg");
                TextInputLayout til2 = findViewById(R.id.nameLayout);
                til2.setError("Testing error msg");
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameTxt.getText().toString();
                if(validateInput(name)){
                    applicant = new Applicant(name, true);
                    Log.d("LMAO", applicant.getFullName());
                    Log.d("HAH", applicant.getSingPass().toString());
                    key = mDatabase.child("Application").push().getKey();
                    application = new Application(key, randomNumberString(), applicant);
                    mDatabase.child("Application").child(key).setValue(application);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent in = getIntent();
        application = (Application) in.getSerializableExtra("Application");
        if(application != null && application.getApplicantList().size() == 1){
            startActivity(new Intent(ApplicationFormActivity.this, SelectApplicationType.class));
        }

//      Autofill if applicant select SingPass
        applicant = (Applicant) in.getSerializableExtra("Applicant");
        if(applicant != null){
            nameTxt.setText(applicant.getFullName());
        }
    }

    public boolean validateInput(String n){
//        input validation here]
        return true;
    }

    public static String randomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    //Convert px to dp
    public float DipToPixels(float dp){
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics()
        );
    }
}