package io.github.jieshengnp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public class ApplicationFormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    TextView forgetPwdTxt, box, genderErrorTxt;
    TextInputLayout accessCodeLayout, pinLayout, titleLayout, nameLayout, nationalityLayout, icLayout, raceLayout, dobLayout, postalLayout, streetLayout, blockLayout, unitLayout, phoneLayout, emailLayout, jobLayout, maritalLayout;
    TextInputEditText accessCodeTxt, nameTxt, icTxt, dobTxt, postalTxt, streetTxt, blockTxt, unitTxt, mobileTxt, emailTxt, occupationTxt;
    AutoCompleteTextView titleDropdown, countryDropdown, raceDropdown, maritalDropdown;
    Button loginBtn, nextBtn;
    RadioGroup loginRadioGroup;
    RadioButton obaRadioBtn, ocbcCardRadioBtn, genderMale, genderFemale;
    ProgressBar progressBar1, progressBar2, progressBar3;
    String key, selectedTitle, selectedCountry, selectedRace, selectedMarital;
    Applicant applicant;
    Application application;

    Button getAddressBtn;

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
        genderErrorTxt = findViewById(R.id.genderErrorTxt);
        loginRadioGroup = findViewById(R.id.loginRadioGrp);
        obaRadioBtn = findViewById(R.id.obaRadioBtn);
        ocbcCardRadioBtn = findViewById(R.id.ocbcCardRadioBtn);
        genderMale = findViewById(R.id.genderMale);
        genderFemale = findViewById(R.id.genderFemale);
        loginBtn = findViewById(R.id.loginBtn);
        nextBtn = findViewById(R.id.nextBtn);
        accessCodeTxt = findViewById(R.id.accessCodeTxt);
        nameTxt = findViewById(R.id.nameTxt);
        accessCodeLayout = findViewById(R.id.accessCodeLayout);
        pinLayout = findViewById(R.id.pinLayout);
        titleLayout = findViewById(R.id.titleLayout);
        nameLayout = findViewById(R.id.nameLayout);
        nationalityLayout = findViewById(R.id.nationalityLayout);
        icLayout = findViewById(R.id.icLayout);
        raceLayout = findViewById(R.id.raceLayout);
        dobLayout = findViewById(R.id.dobLayout);
        postalLayout = findViewById(R.id.postalLayout);
        streetLayout = findViewById(R.id.streetLayout);
        blockLayout = findViewById(R.id.blockLayout);
        unitLayout = findViewById(R.id.unitLayout);
        phoneLayout = findViewById(R.id.phoneLayout);
        emailLayout = findViewById(R.id.emailLayout);
        jobLayout = findViewById(R.id.jobLayout);
        maritalLayout = findViewById(R.id.maritalLayout);
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

        postalTxt = findViewById(R.id.postalTxt);
        streetTxt = findViewById(R.id.streetTxt);
        blockTxt = findViewById(R.id.blockTxt);
        getAddressBtn = findViewById(R.id.getAddressBtn);

        accessCodeLayout.setVisibility(View.GONE);
        pinLayout.setVisibility(View.GONE);
        loginBtn.setVisibility(View.GONE);
        forgetPwdTxt.setVisibility(View.GONE);
        genderErrorTxt.setVisibility(View.GONE);

        progressBar1.setProgress(100);

        ConstraintLayout.LayoutParams boxLP = (ConstraintLayout.LayoutParams) box.getLayoutParams();
        boxLP.height -= DipToPixels(237);

        //Set Login View according to customer type
        obaRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean obaChecked = obaRadioBtn.isChecked();

                if (obaChecked == true){
                    accessCodeLayout.setVisibility(View.VISIBLE);
                    pinLayout.setVisibility(View.VISIBLE);
                    loginBtn.setVisibility(View.VISIBLE);
                    forgetPwdTxt.setVisibility(View.VISIBLE);
                    boxLP.height = (int) DipToPixels(457);
                }
            }
        });

        ocbcCardRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ocbcChecked = ocbcCardRadioBtn.isChecked();

                if (ocbcChecked == true){
                    accessCodeLayout.setVisibility(View.GONE);
                    pinLayout.setVisibility(View.GONE);
                    loginBtn.setVisibility(View.GONE);
                    forgetPwdTxt.setVisibility(View.GONE);
                    boxLP.height = (int) DipToPixels(220);
                }
            }
        });

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

        //Add Race
        raceDropdown = findViewById(R.id.raceDropdown);
        ArrayAdapter<String> racesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, races);
        raceDropdown.setAdapter(racesAdapter);

        raceDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedRace = parent.getAdapter().getItem(position).toString();
            }
        });

        //Add Date of Birth
        dobTxt.setInputType(0);
        dobTxt.setFocusable(false);
        dobTxt.setClickable(true);
        dobTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
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

        //Auto sets address based on postal code
        getAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postalTxt.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter a Postal Code", Toast.LENGTH_SHORT).show();
                }
                else {
                    RequestQueue queue = Volley.newRequestQueue(ApplicationFormActivity.this);
                    String reqUrl = "https://developers.onemap.sg/commonapi/search?searchVal=" + postalTxt.getText().toString() + "&returnGeom=N&getAddrDetails=Y";

                    JsonObjectRequest objRequest = new JsonObjectRequest
                            (Request.Method.GET, reqUrl, null, response -> {
                                Log.d("null", response.toString());

                                try {
                                    JSONArray array = response.getJSONArray("results");
                                    if (array.length() > 0) {
                                        String street = array.getJSONObject(0).getString("ROAD_NAME");
                                        String building = array.getJSONObject(0).getString("BUILDING");
                                        String blkNo = array.getJSONObject(0).getString("BLK_NO");

                                        if (building.equalsIgnoreCase("NIL")) {
                                            building = "";
                                        } else {
                                            building = ", " + building;
                                        }

                                        streetTxt.setText(street + building);
                                        blockTxt.setText(blkNo);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Address could not be found", Toast.LENGTH_SHORT).show();
                                        streetTxt.setText("");
                                        blockTxt.setText("");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }, error -> {
                                error.printStackTrace();
                                Log.d("error", error.getMessage());
                            });
                    APISingleton.getInstance(getApplicationContext()).addToRequestQueue(objRequest);
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()){
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

    //Show DatePicker
    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    public boolean validateInput(){
//        input validation here

        //check if title is empty
        if(titleDropdown.getText().toString().isEmpty()){
            titleLayout.setError("Please choose your title");
            return false;
        }
        else{
            titleLayout.setError(null);
        }

        //check if name is empty
        if(nameTxt.getText().toString().isEmpty()){
            nameLayout.setError("Please enter your name");
            return false;
        }
        else{
            nameLayout.setError(null);
        }

        //check if nationality is empty
        if(countryDropdown.getText().toString().isEmpty()){
            nationalityLayout.setError("Please choose your nationality");
            return false;
        }
        else{
            nationalityLayout.setError(null);
        }

        //check if ic is empty
        if(icTxt.getText().toString().isEmpty()){
            icLayout.setError("Please enter your NRIC or Passport Number");
            return false;
        }
        else{
            icLayout.setError(null);
        }

        //check if race is empty
        if(raceDropdown.getText().toString().isEmpty()){
            raceLayout.setError("Please choose your nationality");
            return false;
        }
        else{
            raceLayout.setError(null);
        }

        //check if ic is empty
        if(dobTxt.getText().toString().isEmpty()){
            dobLayout.setError("Please choose your Date of Birth");
            return false;
        }
        else{
            dobLayout.setError(null);
        }

        //check if Gender is empty
        if(!genderMale.isChecked() && !genderFemale.isChecked()){
            genderErrorTxt.setVisibility(View.VISIBLE);
            genderErrorTxt.setText("Please choose your gender");
            return false;
        }
        else{
            genderErrorTxt.setVisibility(View.GONE);
        }

        //check if postal is empty
        if(postalTxt.getText().toString().isEmpty()){
            postalLayout.setError("Please enter your postal code");
            return false;
        }
        else{
            postalLayout.setError(null);
        }

        //check if street is empty
        if(streetTxt.getText().toString().isEmpty()){
            streetLayout.setError("Please enter your street");
            return false;
        }
        else{
            streetLayout.setError(null);
        }

        //check if block is empty
        if(blockTxt.getText().toString().isEmpty()){
            blockLayout.setError("Please enter your Block/House Number");
            return false;
        }
        else{
            blockLayout.setError(null);
        }

        //check if mobile number is empty
        if(mobileTxt.getText().toString().isEmpty()){
            phoneLayout.setError("Please enter your Mobile Number");
            return false;
        }
        else{
            phoneLayout.setError(null);
        }

        //check if email is empty
        if(emailTxt.getText().toString().isEmpty()){
            emailLayout.setError("Please enter your E-mail address");
            return false;
        }
        else{
            emailLayout.setError(null);
        }

        //check if postal is empty
        if(occupationTxt.getText().toString().isEmpty()){
            jobLayout.setError("Please enter your Occupation");
            return false;
        }
        else{
            jobLayout.setError(null);
        }

        //check if marital status is empty
        if(maritalDropdown.getText().toString().isEmpty()){
            maritalLayout.setError("Please choose your Marital Status");
            return false;
        }
        else{
            maritalLayout.setError(null);
        }

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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String stringDay;
        String stringMonth;

        month += 1;

        if (dayOfMonth < 10){
            stringDay = "0" + dayOfMonth;
        }
        else{
            stringDay = "" + dayOfMonth;
        }
        if (month < 10){
            stringMonth = "0" + month;
        }
        else{
            stringMonth = "" + month;
        }


        String date = stringDay + "/" + stringMonth + "/" + year;
        dobTxt.setText(date);
    }
}