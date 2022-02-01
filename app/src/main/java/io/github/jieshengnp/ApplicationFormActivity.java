package io.github.jieshengnp;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class ApplicationFormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    TextView forgetPwdTxt, box, genderErrorTxt, emailLbl, passwordLbl, signInTitle1, signInTitle2, selfieErrorTxt, uploadIcLbl;
    TextInputLayout accessCodeLayout, pinLayout, titleLayout, nameLayout, nationalityLayout, icLayout, raceLayout, dobLayout, postalLayout, streetLayout, blockLayout, unitLayout, phoneLayout, emailLayout, jobLayout, maritalLayout, passwordLayout;
    TextInputEditText accessCodeTxt, pinTxt, nameTxt, icTxt, dobTxt, postalTxt, streetTxt, blockTxt, unitTxt, mobileTxt, emailTxt, occupationTxt, passwordTxt;
    AutoCompleteTextView titleDropdown, countryDropdown, raceDropdown, maritalDropdown;
    Button loginBtn, nextBtn;
    RadioGroup loginRadioGroup;
    RadioButton obaRadioBtn, ocbcCardRadioBtn, genderMale, genderFemale;
    ProgressBar progressBar1, progressBar2, progressBar3;
    String key, selectedTitle, selectedCountry, selectedRace, selectedMarital, applicationId;
    Applicant applicant;
    Applicant currentApplicant;
    Application application;
    ImageView backBtn, uploadedSelfie;
    Button uploadSelfie;
    ActivityResultLauncher<Intent> activityResultLauncher;
    Bitmap imageBitmap;

    private String android_id;
    private static final int REQUEST_CAMERA_CODE = 100;
    StorageReference storageRef = FirebaseStorage.getInstance("gs://ocbc-team4-2b3ee.appspot.com/").getReference();

    ArrayAdapter<String> nationalitiesAdapter, racesAdapter, martialAdapter, salutationAdapter;

    boolean isLoggedIn, isSingpass;

    Button getAddressBtn;

    ConstraintLayout.LayoutParams boxLP;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://ocbc-team4-2b3ee-default-rtdb.asia-southeast1.firebasedatabase.app/");

    DatabaseReference mDatabase = firebaseDatabase.getReference();
    String[] salutations = {"Mr", "Mrs", "Ms", "Miss", "Mdm", "Dr"};
    String[] races = {"CHINESE", "EURASIAN", "INDIAN", "MALAY", "OTHER RACES"};
    String[] marital = {"DIVORCED", "MARRIED", "SEPARATED", "SINGLE", "WIDOWED"};
    RadioGroup genderGroup;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_form);

        android_id = Settings.Secure.getString(getBaseContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

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
        pinTxt = findViewById(R.id.pinTxt);
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
        passwordLayout = findViewById(R.id.passwordLayout);
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
        passwordTxt = findViewById(R.id.passwordTxt);
        backBtn = findViewById(R.id.backBtn);
        titleDropdown = findViewById(R.id.titleDropdown);
        countryDropdown = findViewById(R.id.countryDropdown);
        raceDropdown = findViewById(R.id.raceDropdown);
        maritalDropdown = findViewById(R.id.maritalDropdown);

        signInTitle1 = findViewById(R.id.textView8);
        signInTitle2 = findViewById(R.id.textView9);
        emailLbl = findViewById(R.id.emailLbl);
        passwordLbl = findViewById(R.id.passwordLbl);

        postalTxt = findViewById(R.id.postalTxt);
        streetTxt = findViewById(R.id.streetTxt);
        blockTxt = findViewById(R.id.blockTxt);
        getAddressBtn = findViewById(R.id.getAddressBtn);

        uploadedSelfie = findViewById(R.id.imageView);
        uploadSelfie = findViewById(R.id.uploadBtn);
        selfieErrorTxt = findViewById(R.id.selfieErrorTxt);
        uploadIcLbl = findViewById(R.id.UploadIcLbl);

        mAuth = FirebaseAuth.getInstance();

        isLoggedIn = false;

//      End of initialise code


        // ATTENTION: This was auto-generated to handle app links.
        handleDeepLink();

        accessCodeLayout.setVisibility(View.GONE);
        pinLayout.setVisibility(View.GONE);
        loginBtn.setVisibility(View.GONE);
        forgetPwdTxt.setVisibility(View.GONE);
        genderErrorTxt.setVisibility(View.GONE);

        boxLP = (ConstraintLayout.LayoutParams) box.getLayoutParams();
        boxLP.height -= DipToPixels(237);

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
        countries.remove("Malaysia");
        countries.add(0, "Singapore");
        countries.add(1, "Malaysia");
        nationalitiesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countries);
        countryDropdown.setAdapter(nationalitiesAdapter);

        countryDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCountry = parent.getAdapter().getItem(position).toString();
            }
        });

        //Add Race
        raceDropdown = findViewById(R.id.raceDropdown);
        racesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, races);
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
        martialAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, marital);
        maritalDropdown.setAdapter(martialAdapter);

        maritalDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedMarital = parent.getAdapter().getItem(position).toString();
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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ApplicationFormActivity.this, SelectApplicationType.class);
                startActivity(i);
            }
        });

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

        if (ContextCompat.checkSelfPermission(ApplicationFormActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(ApplicationFormActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_CAMERA_CODE);
        }

        uploadSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        activityResultLauncher.launch(intent);
                        selfieErrorTxt.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(ApplicationFormActivity.this, "Cant Open",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    if (ContextCompat.checkSelfPermission(ApplicationFormActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(ApplicationFormActivity.this, new String[]{
                                Manifest.permission.CAMERA
                        }, REQUEST_CAMERA_CODE);
                    }
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(validateInput()){
                    applicant = new Applicant();
                    //Title
                    applicant.setTitle(titleDropdown.getText().toString()==null? selectedTitle: titleDropdown.getText().toString());

                    //Name
                    applicant.setName(nameTxt.getText().toString());

                    //Nationality
                    applicant.setNationality(countryDropdown.getText().toString()==null? selectedCountry: countryDropdown.getText().toString());

                    //NRIC
                    applicant.setNRIC(icTxt.getText().toString());

                    //Race
                    applicant.setRace(raceDropdown.getText().toString()==null? selectedRace: raceDropdown.getText().toString());

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
                    applicant.setEmail(isLoggedIn? mAuth.getCurrentUser().getEmail() :emailTxt.getText().toString());

                    //Occupation
                    applicant.setOccupation(occupationTxt.getText().toString());

                    //Marital Status
                    applicant.setMartial(maritalDropdown.getText().toString()==null? selectedMarital: maritalDropdown.getText().toString());

                    //Set Singpass
                    applicant.setIsSingPass(isSingpass);

                    if (!isSingpass && !isLoggedIn) {
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        byte[] bb = bytes.toByteArray();
                        uploadFirebase(bb, applicant.getNRIC());
                    }
                    //Device Id
                    applicant.setDeviceId(android_id);
                    
//                  if progress bar 2 is lighted up, means is second user
                    if (progressBar2.getProgress() == 100) {
//                        Log.d("Application Key", "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//                        Log.d("Application Key", "" + application.getApplicationID());
//                        Log.d("Application Key", "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        key = mDatabase.child("Application").push().getKey();
                        application.setApplicant(1, applicant);
                        mDatabase.child("Application").child(application.getApplicationID()).setValue(application);
                        mDatabase.child("Application").child(application.getApplicationID()).child("applicantList").child("1").setValue(applicant);
                        String notifyEmailBody = "Hi " + application.getApplicantList().get(0).getName() + ", you may proceed to review and confirm the details of your joint account application\n\nPlease use this link to continue\nhttps://mxrcxsz.github.io/Assignment-1/confirmation/" + application.getApplicationID();
                        sendEmail("" + application.getApplicantList().get(0).getEmail(), "OCBC Joint Account Creation", "" + notifyEmailBody);

                    }
                    else {
                        key = mDatabase.child("Application").push().getKey();
                        application = new Application(key, randomNumberString());
                        mDatabase.child("Application").child(key).setValue(application);
                        mDatabase.child("Application").child(key).child("applicantList").child("0").setValue(applicant);

//                        mDatabase.child("User").child()
                    }
//                  Create an account for applicant if they do not have any
                    SignUp(applicant, passwordTxt.getText().toString());
                }
            }
        });
        validateInputInfo();
    }

    private void SignUp(Applicant applicant, String password) {
        mAuth.createUserWithEmailAndPassword(applicant.getEmail(), password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                          Sign up success, bring user to log in page
                            Log.d("Create", "createUserWithEmail:success");
//                          Get current user
                            FirebaseUser user = mAuth.getCurrentUser();

                            applicant.setDeviceId(null);
                            applicant.setIsSingPass(null);
                            //set username and save it to firebase
                            mDatabase.child("User").child(user.getUid()).setValue(applicant);
                        }

                        Bundle extras = new Bundle();
                        Intent in;
                        if(progressBar2.getProgress() == 100){
                            in = new Intent(ApplicationFormActivity.this, ConfirmationPage.class);
                        }else {
                            in = new Intent(ApplicationFormActivity.this, SendApplicationCodeActivity.class);
                        }
                        extras.putSerializable("Application", application);
                        in.putExtras(extras);
                        ApplicationFormActivity.this.startActivity(in);
                    }
                });
    }

    private void uploadFirebase(byte[]bb, String nric){

        StorageReference imagePath = storageRef.child("verificationImgs/"+nric+".jpg");
        UploadTask uploadTask = imagePath.putBytes(bb);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(ApplicationFormActivity.this,"Sucessfully Uploaded Picture",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.v("Err",e.getMessage());
                Toast.makeText(ApplicationFormActivity.this,"Failed To Upload Picture",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateLoginInfo() {
        boolean isValid = true;
        if (accessCodeTxt.getText().toString().isEmpty() || !accessCodeTxt.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            isValid = false;
        }

        if (pinTxt.getText().toString().isEmpty()){
            isValid = false;
        }

        return isValid;
    }

    public void SignIn(String email, String password){
        Log.v("test", "1");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.v("test", "2");
                        if (task.isSuccessful()) {
                            Log.v("success", "signin successful");
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(ApplicationFormActivity.this, "Successfully logged in.", Toast.LENGTH_SHORT).show();

                            //retrieve data
                            FirebaseUser user = mAuth.getCurrentUser();
                            DatabaseReference ref = firebaseDatabase.getReference().child("User").child(user.getUid());
                            ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (!task.isSuccessful()){
                                        Toast.makeText(ApplicationFormActivity.this, "An error occurred while retrieving data.", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Log.v("retrievedone", "Data retreiving");
                                        currentApplicant = task.getResult().getValue(Applicant.class);
                                        titleDropdown.setText(currentApplicant.getTitle(), false);
                                        nameTxt.setText(currentApplicant.getName());
                                        countryDropdown.setText(currentApplicant.getNationality(), false);
                                        icTxt.setText(currentApplicant.getNRIC());
                                        raceDropdown.setText(currentApplicant.getRace(), false);
                                        dobTxt.setText(currentApplicant.getDOB());

                                        if (currentApplicant.getGender().equals("Male")){
                                            genderMale.setChecked(true);
                                            genderFemale.setChecked(false);
                                        }
                                        else if (currentApplicant.getGender().equals("Female")){
                                            genderMale.setChecked(false);
                                            genderFemale.setChecked(true);
                                        }

                                        postalTxt.setText(currentApplicant.getPostal());
                                        streetTxt.setText(currentApplicant.getStreet());
                                        blockTxt.setText(currentApplicant.getBlock());
                                        unitTxt.setText(currentApplicant.getUnit());
                                        mobileTxt.setText(currentApplicant.getMobile());
                                        occupationTxt.setText(currentApplicant.getOccupation());
                                        maritalDropdown.setText(currentApplicant.getMartial(), false);

                                        isLoggedIn = true;
                                    }
                                }
                            });

                            //remove input field on success
                            accessCodeLayout.setVisibility(View.GONE);
                            pinLayout.setVisibility(View.GONE);
                            loginBtn.setVisibility(View.GONE);
                            forgetPwdTxt.setVisibility(View.GONE);
                            box.setVisibility(View.GONE);
                            signInTitle1.setVisibility(View.GONE);
                            signInTitle2.setVisibility(View.GONE);
                            loginRadioGroup.setVisibility(View.GONE);

                            accessCodeLayout.setError(null);
                            pinLayout.setError(null);

                            emailLbl.setVisibility(View.GONE);
                            emailLayout.setVisibility(View.GONE);
                            passwordLbl.setVisibility(View.GONE);
                            passwordLayout.setVisibility(View.GONE);

                            //change all fields to read-only
                            titleDropdown.setEnabled(false);
                            nameTxt.setEnabled(false);
                            countryDropdown.setEnabled(false);
                            icTxt.setEnabled(false);
                            raceDropdown.setEnabled(false);
                            dobTxt.setEnabled(false);
                            genderMale.setEnabled(false);
                            genderFemale.setEnabled(false);
                            postalTxt.setEnabled(false);
                            getAddressBtn.setEnabled(false);
                            streetTxt.setEnabled(false);
                            blockTxt.setEnabled(false);
                            unitTxt.setEnabled(false);
                            mobileTxt.setEnabled(false);
                            occupationTxt.setEnabled(false);
                            maritalDropdown.setEnabled(false);
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(ApplicationFormActivity.this, "Incorrect Email or Password.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ApplicationFormActivity.this, "Error Logging In.", Toast.LENGTH_SHORT).show();
                                Log.v("test", task.getException().getMessage());
                            }
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
            mDatabase.child("Application").child(applicationId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        application = task.getResult().getValue(Application.class);
                    }
                }
            });
            progressBar2.setProgress(100);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent in = getIntent();
//        applicationId = in.getStringExtra("ApplicationID");
        application = (Application)in.getSerializableExtra("Application");
        if(application != null){
            progressBar2.setProgress(100);
            if(application.getApplicantList().size() < 2){
                applicant = new Applicant();
                application.addApplicant(applicant);
            }
        }

//      Autofill if applicant select SingPass
        Applicant singPassData = (Applicant) in.getSerializableExtra("Applicant");
        if(singPassData != null){
            //Hide things for SingPass (image)
            isSingpass = true;
            uploadedSelfie.setVisibility(View.GONE);
            uploadSelfie.setVisibility(View.GONE);
            uploadIcLbl.setVisibility(View.GONE);

            //remove login option
            accessCodeLayout.setVisibility(View.GONE);
            pinLayout.setVisibility(View.GONE);
            loginBtn.setVisibility(View.GONE);
            forgetPwdTxt.setVisibility(View.GONE);
            box.setVisibility(View.GONE);
            signInTitle1.setVisibility(View.GONE);
            signInTitle2.setVisibility(View.GONE);
            loginRadioGroup.setVisibility(View.GONE);

            accessCodeLayout.setError(null);
            pinLayout.setError(null);

//            emailLbl.setVisibility(View.GONE);
//            emailLayout.setVisibility(View.GONE);
//            passwordLbl.setVisibility(View.GONE);
//            passwordLayout.setVisibility(View.GONE);
            applicant = singPassData;

            //Title
            titleDropdown.setText(applicant.getTitle(), false);

            //Name
            nameTxt.setText(applicant.getName());

            //Nationality
            countryDropdown.setText(applicant.getNationality(), false);

            //NRIC
            icTxt.setText(applicant.getNRIC());

            //Race
            raceDropdown.setText(applicant.getRace(), false);

            //DOB
            dobTxt.setText(applicant.getDOB());

            //Gender
            String applicantGender = applicant.getGender();
            if (applicantGender.equals("Male")){
                genderMale.setChecked(true);
                genderFemale.setChecked(false);
            }
            else if(applicantGender.equals("Female")){
                genderMale.setChecked(false);
                genderFemale.setChecked(true);
            }

            //Postal Code
            postalTxt.setText(applicant.getPostal());

            //Street
            streetTxt.setText(applicant.getStreet());

            //block
            blockTxt.setText(applicant.getBlock());

            //unit number
            unitTxt.setText(applicant.getUnit());

            //mobile number
            mobileTxt.setText(applicant.getMobile());

            //email
            emailTxt.setText(applicant.getEmail());

            //occupation
            occupationTxt.setText(applicant.getOccupation());

            //marital status
            maritalDropdown.setText(applicant.getMartial(), false);
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
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    public boolean validateInput(){
//        input validation here
        boolean isValid = true;

        //check if title is empty
        if(titleDropdown.getText().toString().isEmpty()){
            Log.d("Tag", "1");
            isValid = false;
        }

        //check if name is empty
        if(nameTxt.getText().toString().isEmpty()){
            Log.d("Tag", "2");
            isValid = false;
        }

        //check if nationality is empty
        if(countryDropdown.getText().toString().isEmpty()){
            Log.d("Tag", "3");
            isValid = false;
        }

        //check if ic is empty
        if(icTxt.getText().toString().isEmpty()){
            Log.d("Tag", "4");
            isValid = false;
        }

        //check if race is empty
        if(raceDropdown.getText().toString().isEmpty()){
            Log.d("Tag", "5");
            isValid = false;
        }

        //check if ic is empty
        if(dobTxt.getText().toString().isEmpty()){
            Log.d("Tag", "6");
            isValid = false;
        }

        //check if Gender is empty
        if(!genderMale.isChecked() && !genderFemale.isChecked()) {
            Log.d("Tag", "7");
            isValid = false;
        }

        //check if postal is empty
        if(postalTxt.getText().toString().isEmpty()){
            Log.d("Tag", "8");
            isValid = false;
        }

        //check if street is empty
        if(streetTxt.getText().toString().isEmpty()){
            Log.d("Tag", "9");
            isValid = false;
        }

        //check if block is empty
        if(blockTxt.getText().toString().isEmpty()){
            Log.d("Tag", "10");
            isValid = false;
        }

        //check if mobile number is empty
        if(mobileTxt.getText().toString().isEmpty()){
            Log.d("Tag", "11");
            isValid = false;
        }


        //check if postal is empty
        if(occupationTxt.getText().toString().isEmpty()){
            Log.d("Tag", "12");
            isValid = false;
        }

        //check if marital status is empty
        if(maritalDropdown.getText().toString().isEmpty()){
            Log.d("Tag", "13");
            isValid = false;
        }
        if (!isSingpass && !isLoggedIn) {
            try {
                imageBitmap.getWidth();
            } catch (NullPointerException e) {
                isValid = false;
                selfieErrorTxt.setVisibility(View.VISIBLE);
            }
        }
        //if user is already logged in
        if(!isLoggedIn){
            //check if email is empty
            if(emailTxt.getText().toString().isEmpty() || !emailTxt.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                isValid = false;
            }
            if(passwordTxt.getText().toString().isEmpty()){
                isValid = false;
            }
        }

        if(isValid == false){
            Toast.makeText(getApplicationContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
        }

        return isValid;
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

    public void validateInputInfo(){
        accessCodeTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (accessCodeTxt.getText().toString().isEmpty() || !accessCodeTxt.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                        accessCodeLayout.setError("Please enter a valid E-mail");
                    } else {
                        accessCodeLayout.setError(null);
                    }
                }
            }
        });

        pinTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (pinTxt.getText().toString().isEmpty()) {
                        pinLayout.setError("Please enter your password");
                    } else {
                        pinLayout.setError(null);
                    }
                }
            }
        });

        titleDropdown.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    //check if title is empty
                    if(titleDropdown.getText().toString().isEmpty()){
                        titleLayout.setError("Please choose your title");
                    }
                    else{
                        titleLayout.setError(null);
                    }
                }
            }
        });

        nameTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    //check if title is empty
                    if(nameTxt.getText().toString().isEmpty()){
                        nameLayout.setError("Please enter your name");
                    }
                    else{
                        nameLayout.setError(null);
                    }
                }
            }
        });

        countryDropdown.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    //check if title is empty
                    if(countryDropdown.getText().toString().isEmpty()){
                        nationalityLayout.setError("Please choose your country");
                    }
                    else{
                        nationalityLayout.setError(null);
                    }
                }
            }
        });

        icTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    //check if title is empty
                    if(icTxt.getText().toString().isEmpty()){
                        icLayout.setError("Please enter your IC");
                    }
                    else{
                        icLayout.setError(null);
                    }
                }
            }
        });

        raceDropdown.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    //check if title is empty
                    if(raceDropdown.getText().toString().isEmpty()){
                        raceLayout.setError("Please choose your race");
                    }
                    else{
                        raceLayout.setError(null);
                    }
                }
            }
        });

        dobTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    //check if title is empty
                    if(dobTxt.getText().toString().isEmpty()){
                        dobLayout.setError("Please choose your Date of Birth");
                    }
                    else{
                        dobLayout.setError(null);
                    }
                }
            }
        });

        genderGroup.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if(!genderMale.isChecked() && !genderFemale.isChecked()) {
                        genderErrorTxt.setVisibility(View.VISIBLE);
                        genderErrorTxt.setText("Please choose your gender");
                    }
                    else{
                        genderErrorTxt.setVisibility(View.GONE);
                    }
                }
            }
        });

        postalTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    //check if title is empty
                    if(postalTxt.getText().toString().isEmpty()){
                        postalLayout.setError("Please enter your postal code");
                    }
                    else{
                        postalLayout.setError(null);
                    }
                }
            }
        });

        streetTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    //check if title is empty
                    if(streetTxt.getText().toString().isEmpty()){
                        streetLayout.setError("Please enter your street");
                    }
                    else{
                        streetLayout.setError(null);
                    }
                }
            }
        });

        blockTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    //check if title is empty
                    if(blockTxt.getText().toString().isEmpty()){
                        blockLayout.setError("Please enter your block");
                    }
                    else{
                        blockLayout.setError(null);
                    }
                }
            }
        });

        mobileTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    //check if title is empty
                    if(mobileTxt.getText().toString().isEmpty()){
                        phoneLayout.setError("Please enter your mobile number");
                    }
                    else{
                        phoneLayout.setError(null);
                    }
                }
            }
        });

        if(!isLoggedIn) {
            emailTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        //check if title is empty
                        if (emailTxt.getText().toString().isEmpty() || !emailTxt.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                            emailLayout.setError("Please enter a valid email");
                        } else {
                            emailLayout.setError(null);
                        }
                    }
                }
            });

            passwordTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if (passwordTxt.getText().toString().isEmpty()) {
                            passwordLayout.setError("Please enter your password");
                        } else {
                            passwordLayout.setError(null);
                        }
                    }
                }
            });
        }

        occupationTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    //check if title is empty
                    if(occupationTxt.getText().toString().isEmpty()){
                        jobLayout.setError("Please enter your occupation");
                    }
                    else{
                        jobLayout.setError(null);
                    }
                }
            }
        });

        maritalDropdown.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    //check if title is empty
                    if(maritalDropdown.getText().toString().isEmpty()){
                        maritalLayout.setError("Please choose your marital status");
                    }
                    else{
                        maritalLayout.setError(null);
                    }
                }
            }
        });

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

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateLoginInfo()){
                    Log.v("validated", "Login info is not null");
                    String email = accessCodeTxt.getText().toString();
                    String password = pinTxt.getText().toString();

                    mAuth.signOut();
                    //get login info
                    SignIn(email, password);

                    uploadedSelfie.setVisibility(View.GONE);
                    uploadSelfie.setVisibility(View.GONE);
                    uploadIcLbl.setVisibility(View.GONE);
                    isLoggedIn = true;
                }
            }
        });

        // Add Salutations
        titleDropdown = findViewById(R.id.titleDropdown);
        salutationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, salutations);
        titleDropdown.setAdapter(salutationAdapter);

        titleDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTitle = parent.getAdapter().getItem(position).toString();
            }
        });
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