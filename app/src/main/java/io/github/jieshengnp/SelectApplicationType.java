package io.github.jieshengnp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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
                newApplicant = sampleMyInfo();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Applicant", (Serializable) newApplicant);
                Intent in = new Intent(v.getContext(), ApplicationFormActivity.class);
                Intent getIn = getIntent();
                Application application = (Application) getIn.getSerializableExtra("Application");
                if(application != null) {
                    bundle.putSerializable("Application",application);
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
                Application application = (Application) getIn.getSerializableExtra("Application");
                if(application != null) {
                    bundle.putSerializable("Application",application);
                }
                in.putExtras(bundle);
                v.getContext().startActivity(in);
            }
        });
    }

    private Applicant sampleMyInfo(){
        Applicant applicant = new Applicant(
                "Mr",
                "Chen Ah Meng",
                "Singapore",
                "T0123456A",
                "CHINESE",
                "21/03/2001",
                "Male",
                "650358",
                "BUKIT BATOK STREET 31",
                "359",
                "#05-313",
                "81234567",
                "yeojax2@gmail.com",
                "Engineer",
                "MARRIED"
        );
        applicant.setIsSingPass(true);
        return applicant;
    }
}