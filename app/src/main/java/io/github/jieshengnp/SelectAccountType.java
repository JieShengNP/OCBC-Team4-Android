package io.github.jieshengnp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectAccountType extends AppCompatActivity {

    TextView individualAccBtn, jointAccBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account_type);

        individualAccBtn = findViewById(R.id.IndividualAccBtn);
        jointAccBtn = findViewById(R.id.JointAccBtn);

        jointAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectAccountType.this,SelectApplicationCode.class);
                startActivity(i);
            }
        });
    }
}