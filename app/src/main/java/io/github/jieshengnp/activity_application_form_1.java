package io.github.jieshengnp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class activity_application_form_1 extends AppCompatActivity {

    TextView forgetPwdTxt;
    TextInputEditText accessCodeTxt, nameTxt;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_form1);

//      Hide the top title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

//      Initialise data
        forgetPwdTxt = findViewById(R.id.forgetPwdTxt);
        loginBtn = findViewById(R.id.loginBtn);
        accessCodeTxt = findViewById(R.id.accessCodeTxt);
        nameTxt = findViewById(R.id.nameTxt);

        forgetPwdTxt.setVisibility(View.GONE);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout til = findViewById(R.id.accessCodeLayout);
                til.setError("Testing error msg");
                TextInputLayout til2 = findViewById(R.id.nameLayout);
                til2.setError("Testing error msg");
            }
        });

    }
}