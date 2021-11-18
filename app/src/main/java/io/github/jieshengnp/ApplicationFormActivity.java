package io.github.jieshengnp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
    String key, selectedTitle, selectedCountry, selectedRace, selectedMarital, applicationId;
    Applicant applicant;
    Application application;
    ImageView backBtn;

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
        backBtn = findViewById(R.id.backBtn);

        postalTxt = findViewById(R.id.postalTxt);
        streetTxt = findViewById(R.id.streetTxt);
        blockTxt = findViewById(R.id.blockTxt);
        getAddressBtn = findViewById(R.id.getAddressBtn);

//      End of initialise code


        // ATTENTION: This was auto-generated to handle app links.
        handleDeepLink();

        accessCodeLayout.setVisibility(View.GONE);
        pinLayout.setVisibility(View.GONE);
        loginBtn.setVisibility(View.GONE);
        forgetPwdTxt.setVisibility(View.GONE);

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
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {
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

        //Auto sets address based on postal code
        getAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ApplicationFormActivity.this, SelectApplicationType.class);
                startActivity(i);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameTxt.getText().toString();
                if (validateInput(name)) {
                    if (applicant == null) {
                        applicant = new Applicant();
                        applicant.setSingPass(false);

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
                        if (genderButton != null) {
                            applicant.setGender(genderButton.getText().toString());
                        }

                        //Address
                        applicant.setPostal(postalTxt.getText().toString());
                        applicant.setStreet(streetTxt.getText().toString());
                        applicant.setBlock(blockTxt.getText().toString());
                        applicant.setUnit(unitTxt.getText().toString());
                    }

                    //Title
                    applicant.setTitle(selectedTitle);

                    //Mobile
                    applicant.setMobile(mobileTxt.getText().toString());

                    //Email
                    applicant.setEmail(emailTxt.getText().toString());

                    //Occupation
                    applicant.setOccupation(occupationTxt.getText().toString());

                    //Marital Status
                    applicant.setMartial(selectedMarital);

                    Log.d("check", "name" + applicant.getName());

                    if (progressBar2.getProgress() == 100) {
                        String notifyEmailBody = "";
                        sendEmail("" + application.getApplicantList().get(0).getEmail(), "OCBC Joint Account Creation", "" + notifyEmailBody);
                    }

                    key = mDatabase.child("Application").push().getKey();
                    application = new Application(key, randomNumberString(), applicant);
                    mDatabase.child("Application").child(key).setValue(application);

                    Bundle extras = new Bundle();
                    Intent in = new Intent(v.getContext(), SendApplicationCodeActivity.class);
                    extras.putSerializable("Application", application);
                    in.putExtras(extras);
                    v.getContext().startActivity(in);
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleDeepLink();
    }

    private void handleDeepLink() {
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        if(appLinkData != null){
            applicationId = appLinkData.getLastPathSegment();
            progressBar2.setProgress(100);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent in = getIntent();
        applicationId = in.getStringExtra("ApplicationID");
        if(applicationId != null){
            progressBar2.setProgress(100);
        }

//      Autofill if applicant select SingPass
        Applicant singPassData = (Applicant) in.getSerializableExtra("Applicant");
        if(singPassData != null){
            applicant = singPassData;
            applicant.setSingPass(true);

            //Name
            nameTxt.setText(applicant.getName());

            //Nationality

            //NRIC

            //Race

            //DOB

            //Gender

            //Address
        }
    }

    public void sendEmail(String recipient, String subject, String body){
        new Thread(() -> {
            try {
                GmailSender sender = new GmailSender("ocbc.auth@gmail.com",
                        "PasswordForOcbc");
                sender.sendMail("OCBC", body,
                        "ocbc.auth@gmail.com", "" + recipient);
            } catch (Exception e) {
                Log.e("SendMail", e.getMessage(), e);
            }
        }).start();
    }

    public boolean validateInput(String n){
//       input validation here
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