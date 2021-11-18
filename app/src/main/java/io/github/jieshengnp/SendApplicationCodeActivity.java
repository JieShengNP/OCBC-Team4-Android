package io.github.jieshengnp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SendApplicationCodeActivity extends AppCompatActivity {

    Button sendCodeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_application_code);

        sendCodeBtn = findViewById(R.id.sendCodeBtn);
        sendCodeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    public void sendEmail(){
        new Thread(() -> {
            try {
                GmailSender sender = new GmailSender("ocbc.auth@gmail.com",
                        "PasswordForOcbc");
                sender.sendMail("OCBC", "Please do not share this with others\n\nYour application code will be 314453",
                        "ocbc.auth@gmail.com", "mxrcxsz@gmail.com");
                Toast.makeText(SendApplicationCodeActivity.this,
                                "Application code is sent, ask them to check their email ",
                                Toast.LENGTH_SHORT)
                        .show();

            } catch (Exception e) {
                Log.e("SendMail", e.getMessage(), e);
            }
        }).start();
    }
}