package io.github.jieshengnp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectApplicationCode extends AppCompatActivity {

    TextView noAppCode, hasAppCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_application_code);

        noAppCode = findViewById(R.id.noAppCode);
        hasAppCode = findViewById(R.id.hasAppCode);

        noAppCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectApplicationCode.this, SelectApplicationType.class);
                startActivity(i);
            }
        });

        hasAppCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectApplicationCode.this, EnterApplicationCodeActivity.class);
                startActivity(i);
            }
        });
    }
}