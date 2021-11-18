package io.github.jieshengnp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectAccountActivity extends AppCompatActivity {

    ImageView img1, img2, img3;
    TextView acc360Btn, accMSABtn, accFRANKBtn, labelTxt,desTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account);

        acc360Btn = findViewById(R.id.Acc360Btn);
        accMSABtn = findViewById(R.id.AccMSABtn);
        accFRANKBtn = findViewById(R.id.AccFRANKBtn);
        img1 = findViewById(R.id.imageView2);
        img2 = findViewById(R.id.imageView3);
        img3 = findViewById(R.id.imageView4);
        labelTxt = findViewById(R.id.textView3);
        desTxt = findViewById(R.id.descTxt);

        labelTxt.setVisibility(View.GONE);
        desTxt.setVisibility(View.GONE);

        img3.bringToFront();

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

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labelTxt.setVisibility(View.VISIBLE);
                desTxt.setVisibility(View.VISIBLE);
                desTxt.setText("OCBC 360 is a savings account with a base interest rate of 0.05% per annum on the account's balance. You enjoy bonus interest rates when you credit your monthly salary, meet certain deposit amounts such as S$500 monthly increases or S$200,000 total balance, purchase insurance, or invest with OCBC");
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labelTxt.setVisibility(View.VISIBLE);
                desTxt.setVisibility(View.VISIBLE);
                desTxt.setText("Simply save monthly, without making any withdrawals - and watch your interest grow! Earn additional 0.05% bonus interest when you deposit at least S$50 and make no withdrawals in the same month.");
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labelTxt.setVisibility(View.VISIBLE);
                desTxt.setVisibility(View.VISIBLE);
                desTxt.setText("An account that earns you up to 4x more than regular savings accounts. When it comes to saving, it's time to glo up from your regular savings account with more interest.");
            }
        });
    }
}