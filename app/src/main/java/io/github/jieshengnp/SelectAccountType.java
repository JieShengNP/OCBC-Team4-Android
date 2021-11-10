package io.github.jieshengnp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectAccountType extends AppCompatActivity {

    Button individualAccBtn = findViewById(R.id.IndividualAccBtn);
    Button jointAccBtn = findViewById(R.id.JointAccBtn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account_type);

        jointAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectAccountType.this,SelectApplicationCode.class);
                startActivity(i);
            }
        });
    }
}