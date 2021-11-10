package io.github.jieshengnp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectAccountActivity extends AppCompatActivity {

    Button acc360Btn = findViewById(R.id.Acc360Btn);
    Button accMSABtn = findViewById(R.id.AccMSABtn);
    Button accFRANKBtn = findViewById(R.id.AccFRANKBtn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account);

        acc360Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectAccountActivity.this,SelectAccountType.class);
                startActivity(i);
            }
        });
        accMSABtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectAccountActivity.this,SelectAccountType.class);
                startActivity(i);
            }
        });
        accFRANKBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectAccountActivity.this,SelectAccountType.class);
                startActivity(i);
            }
        });
    }
}