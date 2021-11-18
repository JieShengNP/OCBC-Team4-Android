package io.github.jieshengnp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Map;

public class AccountDetailsActivity extends AppCompatActivity {

    TextView accountNo, accountTypeStatus, accountOwner, accDetailsBal1, accDetailsBal2, accDetailsLimit;
    Accounts currentAccount;
    LinearLayout homeLayout, applicationLayout, settingLayout, logoutLayout;
    AlertDialog.Builder adBuilder;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        accountNo = findViewById(R.id.accountDetailsAccNo);
        accountTypeStatus = findViewById(R.id.accountDetailsTypeStatus);
        accountOwner = findViewById(R.id.accountDetailsOwner);
        accDetailsBal1 = findViewById(R.id.accountDetailsBal1);
        accDetailsBal2 = findViewById(R.id.accountDetailsBal2);
        accDetailsLimit = findViewById(R.id.accountDetailsLimit);

        homeLayout = findViewById(R.id.barHomeLayout);
        applicationLayout = findViewById(R.id.barApplicationLayout);
        settingLayout = findViewById(R.id.barSettingLayout);
        logoutLayout = findViewById(R.id.barLogoutLayout);

        adBuilder = new AlertDialog.Builder(AccountDetailsActivity.this);
        mAuth = FirebaseAuth.getInstance();

        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adBuilder.setTitle("Are you sure you want to log out?");
                adBuilder.setPositiveButton("Log out", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        Intent intent = new Intent(AccountDetailsActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(AccountDetailsActivity.this, "You have successfully logged out!", Toast.LENGTH_SHORT).show();
                    }
                });
                adBuilder.setNegativeButton("Cancel", null);
                adBuilder.setCancelable(true);
                adBuilder.show();
            }
        });

        currentAccount = (Accounts) getIntent().getSerializableExtra("Account");

        accountNo.setText("Account " + currentAccount.getBankNo());
        String typeStatus = currentAccount.getType() + " Account";
        if (currentAccount.getStatus().equals("Restricted")){
            typeStatus += " " + "(Restricted)";
            accDetailsLimit.setText("Transfer Limit: $500 (Restricted)");
        } else {
            accDetailsLimit.setText("Transfer Limit: $10,000");
        }
        accountTypeStatus.setText(typeStatus);
        String accOwners = "Account Owners: ";
        ArrayList<String> owners = new ArrayList<String>(currentAccount.getOwner().values());
        for (int i = 0; i < owners.size(); i++){
            accOwners += owners.get(i);
            if (i + i < owners.size()){
                accOwners += ", ";
            }
        }
        accountOwner.setText(accOwners);

        accDetailsBal1.setText("$" + String.format("%,.2f", currentAccount.getBalance()));
        accDetailsBal2.setText("$" + String.format("%,.2f", currentAccount.getBalance()));
    }
}