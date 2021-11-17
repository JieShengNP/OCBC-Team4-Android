package io.github.jieshengnp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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

import java.util.Random;

public class ApplicationFormActivity extends AppCompatActivity {

    TextView forgetPwdTxt, box;
    TextInputLayout accessCodeLayout, pinLayout;
    TextInputEditText accessCodeTxt, nameTxt;
    Button loginBtn, nextBtn;
    String key;
    Applicant applicant;
    Application application;

    TextInputEditText postalTxt, streetTxt, blockTxt;
    Button getAddressBtn;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://ocbc-team4-2b3ee-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_form);

//      Hide the top title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

//      Initialise data
        forgetPwdTxt = findViewById(R.id.forgetPwdTxt);
        loginBtn = findViewById(R.id.loginBtn);
        nextBtn = findViewById(R.id.nextBtn);
        accessCodeTxt = findViewById(R.id.accessCodeTxt);
        nameTxt = findViewById(R.id.nameTxt);
        accessCodeLayout = findViewById(R.id.accessCodeLayout);
        pinLayout = findViewById(R.id.pinLayout);
        box = findViewById(R.id.box);

        postalTxt = findViewById(R.id.postalTxt);
        streetTxt = findViewById(R.id.streetTxt);
        blockTxt = findViewById(R.id.blockTxt);
        getAddressBtn = findViewById(R.id.getAddressBtn);

        accessCodeLayout.setVisibility(View.GONE);
        pinLayout.setVisibility(View.GONE);
        loginBtn.setVisibility(View.GONE);
        forgetPwdTxt.setVisibility(View.GONE);

        ConstraintLayout.LayoutParams boxLP = (ConstraintLayout.LayoutParams) box.getLayoutParams();
        boxLP.height -= DipToPixels(237);

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
                                if (array.length()>0){
                                    String street = array.getJSONObject(0).getString("ROAD_NAME");
                                    String building = array.getJSONObject(0).getString("BUILDING");
                                    String blkNo = array.getJSONObject(0).getString("BLK_NO");

                                    if (building.equalsIgnoreCase("NIL")){
                                        building = "";
                                    }
                                    else{
                                        building = ", " + building;
                                    }

                                    streetTxt.setText(street + building);
                                    blockTxt.setText(blkNo);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Address could not be found", Toast.LENGTH_SHORT).show();
                                    streetTxt.setText("");
                                    blockTxt.setText("");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }, error ->{
                            error.printStackTrace();
                            Log.d("error", error.getMessage());
                });

                APISingleton.getInstance(getApplicationContext()).addToRequestQueue(objRequest);
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