package io.github.jieshengnp;

import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    String email, password;
    private FirebaseAuth mAuth;
    EditText emailTxt, passwordTxt;
    Button loginBtn;
    ImageView backBtn;
    ProgressDialog progressDialog;
    private CancellationSignal cancellationSignal = null;
    private BiometricPrompt.AuthenticationCallback authenticationCallback;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailTxt = findViewById(R.id.loginEmailTxt);
        passwordTxt = findViewById(R.id.loginPasswordTxt);
        loginBtn = findViewById(R.id.loginLoginBtn);
        backBtn = findViewById(R.id.imageView6);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = emailTxt.getText().toString();
                String passwordText = passwordTxt.getText().toString();
                if (emailText.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter an email!", Toast.LENGTH_SHORT).show();
                } else if (passwordText.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter a password!", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setTitle("Signing In");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    SignIn(emailText, passwordText);
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


//      Code for biometrics
        authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
            // here we need to implement two methods
            // onAuthenticationError and
            // onAuthenticationSucceeded If the
            // fingerprint is not recognized by the
            // app it will call onAuthenticationError
            // and show a toast
            @Override
            public void onAuthenticationError(
                    int errorCode, CharSequence errString)
            {
                super.onAuthenticationError(errorCode, errString);
                notifyUser("Authentication Error : " + errString);
            }
            // If the fingerprint is recognized by the
            // app then it will call
            // onAuthenticationSucceeded and show a
            // toast that Authentication has Succeed
            // Here you can also start a new activity
            // after that
            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result)
            {
                super.onAuthenticationSucceeded(result);
                progressDialog.setTitle("Signing In");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                SignIn(email, password);
            }
        };

        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        email = preferences.getString("email", "");
        password = preferences.getString("password", "");
        if(!email.equals("") && !password.equals("") && checkBiometricSupport()){
            BiometricPrompt biometricPrompt = new BiometricPrompt
                    .Builder(getApplicationContext())
                    .setTitle("Login with finger print")
                    .setSubtitle("Place your finger on the sensor")
//                        .setDescription("Uses FP")
                    .setNegativeButton("Cancel", getMainExecutor(), new DialogInterface.OnClickListener() {
                        @Override
                        public void
                        onClick(DialogInterface dialogInterface, int i)
                        {
//                                notifyUser("Authentication Cancelled");
                        }
                    }).build();

            // start the authenticationCallback in
            // mainExecutor
            biometricPrompt.authenticate(
                    getCancellationSignal(),
                    getMainExecutor(),
                    authenticationCallback);
        }
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
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(LoginActivity.this, "Incorrect Email or Password.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Error Logging In.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private CancellationSignal getCancellationSignal()
    {
        cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(
                new CancellationSignal.OnCancelListener() {
                    @Override public void onCancel()
                    {
//                        notifyUser("Authentication was Cancelled by the user");
                    }
                });
        return cancellationSignal;
    }

    // it checks whether the
    // app the app has fingerprint
    // permission
    @RequiresApi(Build.VERSION_CODES.M)
    private Boolean checkBiometricSupport()
    {
        KeyguardManager keyguardManager = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
        if (!keyguardManager.isDeviceSecure()) {
            notifyUser("Fingerprint authentication has not been enabled in settings");
            return false;
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC)!= PackageManager.PERMISSION_GRANTED) {
            notifyUser("Fingerprint Authentication Permission is not enabled");
            return false;
        }
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            return true;
        }
        else
            return true;
    }

    // this is a toast method which is responsible for
    // showing toast it takes a string as parameter
    private void notifyUser(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}