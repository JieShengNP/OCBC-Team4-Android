package io.github.jieshengnp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.io.Serializable;

public class SelectApplicationType extends AppCompatActivity {
    Button myInfoBtn, formBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_application_type);

        //      Hide the top title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        myInfoBtn = findViewById(R.id.useMyInfoButton);
        formBtn = findViewById(R.id.completeFormButton);

        myInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Applicant newApplicant = new Applicant("ASD", true);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Applicant", (Serializable) newApplicant);
                Intent in = new Intent(v.getContext(), ApplicationFormActivity.class);
                in.putExtras(bundle);
                v.getContext().startActivity(in);
            }
        });

        formBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), ApplicationFormActivity.class));
            }
        });
    }
}