package io.github.jieshengnp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

        myInfoBtn = findViewById(R.id.useMyInfoButton);
        formBtn = findViewById(R.id.completeFormButton);

        myInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Applicant newApplicant = new Applicant();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Applicant", (Serializable) newApplicant);
                Intent in = new Intent(v.getContext(), ApplicationFormActivity.class);
                Intent getIn = getIntent();
                String applicationId = getIn.getStringExtra("ApplicationID");
                if(applicationId != null) {
                    Log.d("", ""+applicationId);
                    bundle.putString("ApplicationID",applicationId);
                }
                in.putExtras(bundle);
                v.getContext().startActivity(in);
            }
        });

        formBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent in = new Intent(v.getContext(), ApplicationFormActivity.class);
                Intent getIn = getIntent();
                String applicationId = getIn.getStringExtra("ApplicationID");
                if(applicationId != null) {
                    Log.d("", ""+applicationId);
                    bundle.putString("ApplicationID",applicationId);
                }
                in.putExtras(bundle);
                v.getContext().startActivity(in);
            }
        });
    }
}