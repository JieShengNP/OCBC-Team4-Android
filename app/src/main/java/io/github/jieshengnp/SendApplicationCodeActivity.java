package io.github.jieshengnp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SendApplicationCodeActivity extends AppCompatActivity {

    Button sendCodeBtn;
    TextView emailTxt, applicationCodeLbl;
    Application application;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_application_code);

        sendCodeBtn = findViewById(R.id.sendCodeBtn);
        emailTxt = findViewById(R.id.emailTxt);
        backBtn = findViewById(R.id.backBtn);
        applicationCodeLbl = findViewById(R.id.applicationCodeLbl);

        Intent getIn = getIntent();
        application = (Application) getIn.getSerializableExtra("Application");

        applicationCodeLbl.setText(application.getApplicationCode());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SendApplicationCodeActivity.this,ApplicationFormActivity.class);
                startActivity(i);
            }
        });

        sendCodeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String body = "Please do not share this with others\n\nYour application code will be " + application.getApplicationCode() + "\n\n or use this link https://mxrcxsz.github.io/Assignment-1/applicationform/" + application.getApplicationID();
                sendEmail("" + emailTxt.getText().toString(), "OCBC", body);
            }
        });
    }

    public void sendEmail(String recipient, String subject, String body){
        new Thread(() -> {
            try {
                GmailSender sender = new GmailSender("ocbc.auth@gmail.com",
                        "PasswordForOcbc");
                sender.sendMail("OCBC", body,
                        "ocbc.auth@gmail.com", "" + recipient);
                showToast("Email sent! tell them to check their inbox");

            } catch (Exception e) {
                Log.e("SendMail", e.getMessage(), e);
            }
        }).start();
    }

    public void showToast(final String toast)
    {
        runOnUiThread(() -> Toast.makeText(SendApplicationCodeActivity.this, toast, Toast.LENGTH_SHORT).show());
    }

}
