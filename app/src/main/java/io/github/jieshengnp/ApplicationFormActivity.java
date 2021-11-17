package io.github.jieshengnp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    TextInputEditText accessCodeTxt, nameTxt, icTxt, dobTxt, postalTxt, streetTxt, blockTxt, unitTxt, mobileTxt, emailTxt, occupationTxt;
    AutoCompleteTextView titleDropdown, countryDropdown, raceDropdown, maritalDropdown;
    Button loginBtn, nextBtn;
    ProgressBar progressBar1, progressBar2, progressBar3;
    String key, selectedTitle, selectedCountry, selectedRace, selectedMarital;
    Applicant applicant;
    Application application;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://ocbc-team4-2b3ee-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();
    String[] salutations = {"Mr", "Mrs", "Ms", "Miss", "Mdm", "Dr"};
    String[] races = {"CHINESE", "EURASIAN", "INDIAN", "MALAY", "OTHER RACES"};
    String[] marital = {"DIVORCED", "MARRIED", "SEPARATED", "SINGLE", "WIDOWED"};
    RadioGroup genderGroup;

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
        icTxt = findViewById(R.id.icTxt);
        dobTxt = findViewById(R.id.dobTxt);
        genderGroup = findViewById(R.id.genderRadio);
        postalTxt = findViewById(R.id.postalTxt);
        streetTxt = findViewById(R.id.streetTxt);
        blockTxt = findViewById(R.id.blockTxt);
        unitTxt = findViewById(R.id.unitTxt);
        mobileTxt = findViewById(R.id.phoneTxt);
        emailTxt = findViewById(R.id.emailTxt);
        occupationTxt = findViewById(R.id.jobTxt);

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

        titleDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTitle = parent.getAdapter().getItem(position).toString();
            }
        });

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

        countryDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCountry = parent.getAdapter().getItem(position).toString();
            }
        });

        //Add Ethnicity
        raceDropdown = findViewById(R.id.raceDropdown);
        ArrayAdapter<String> racesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, races);
        raceDropdown.setAdapter(racesAdapter);

        raceDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedRace = parent.getAdapter().getItem(position).toString();
            }
        });


        //Add Marital Status
        maritalDropdown = findViewById(R.id.maritalDropdown);
        ArrayAdapter<String> martialAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, marital);
        maritalDropdown.setAdapter(martialAdapter);

        maritalDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedMarital = parent.getAdapter().getItem(position).toString();
            }
        });

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

                    applicant = new Applicant();
                    //Title
                    applicant.setTitle(selectedTitle);

                    //Name
                    applicant.setName(nameTxt.getText().toString());

                    //Nationality
                    applicant.setNationality(selectedCountry);

                    //NRIC
                    applicant.setNRIC(icTxt.getText().toString());

                    //Race
                    applicant.setRace(selectedRace);

                    //DOB
                    applicant.setDOB(dobTxt.getText().toString());

                    //Gender
                    RadioButton genderButton = findViewById(genderGroup.getCheckedRadioButtonId());
                    applicant.setGender(genderButton.getText().toString());

                    //Address
                    applicant.setPostal(postalTxt.getText().toString());
                    applicant.setStreet(streetTxt.getText().toString());
                    applicant.setBlock(blockTxt.getText().toString());
                    applicant.setUnit(unitTxt.getText().toString());

                    //Mobile
                    applicant.setMobile(mobileTxt.getText().toString());

                    //Email
                    applicant.setEmail(emailTxt.getText().toString());

                    //Occupation
                    applicant.setOccupation(occupationTxt.getText().toString());

                    //Marital Status
                    applicant.setMartial(selectedMarital);

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
            nameTxt.setText(applicant.getName());
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