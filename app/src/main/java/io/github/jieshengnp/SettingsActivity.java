package io.github.jieshengnp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {
    Switch swicth1;
    ImageView backBtn;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    SharedPreferences sharedpreferences;
    String password;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        swicth1 = findViewById(R.id.switch1);
        progressDialog = new ProgressDialog(this);
        backBtn = findViewById(R.id.backBtn);

        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        String email = preferences.getString("email", "");
        if(!email.equals("")){
            swicth1.setChecked(true);
        }

        swicth1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    reEnterPassword();
                }
                else{
                    SettingsActivity.this.getSharedPreferences("Login", 0).edit().clear().commit();
                }
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

    }
    public void SignIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
//                                Toast.makeText(SettingsActivity.this, "User authenticated", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = getSharedPreferences("Login", MODE_PRIVATE).edit();
                                editor.putString("email", mUser.getEmail());
                                editor.putString("password", password);
                                editor.apply();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(SettingsActivity.this, "Incorrect password.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SettingsActivity.this, "Error Logging In.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    private void reEnterPassword(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle("Please re-enter your password");
// I'm using fragment here so I'm using getView() to provide ViewGroup
// but you can provide here any other instance of ViewGroup from your Fragment / Activity
        View viewInflated = LayoutInflater.from(SettingsActivity.this).inflate(R.layout.text_inpu_password, (ViewGroup)this
                .findViewById(android.R.id.content), false);
// Set up the input
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(viewInflated);

// Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                password = input.getText().toString();

                progressDialog.setTitle("Re-authenticating user");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                SignIn(mUser.getEmail(), password);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}