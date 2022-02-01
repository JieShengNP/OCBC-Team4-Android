package io.github.jieshengnp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    ArrayList<Accounts> accountsList;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://ocbc-team4-2b3ee-default-rtdb.asia-southeast1.firebasedatabase.app/");
    Applicant currentUserData;
    AccountsAdapter accountsAdapter;
    TextView nameTxt;
    LinearLayout homeLayout, applicationLayout, settingLayout, logoutLayout;
    AlertDialog.Builder adBuilder;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        nameTxt = findViewById(R.id.dashboardWelcome);

        homeLayout = findViewById(R.id.barHomeLayout);
        applicationLayout = findViewById(R.id.barApplicationLayout);
        settingLayout = findViewById(R.id.barSettingLayout);
        logoutLayout = findViewById(R.id.barLogoutLayout);

        adBuilder = new AlertDialog.Builder(DashboardActivity.this);
        mAuth = FirebaseAuth.getInstance();

        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adBuilder.setTitle("Are you sure you want to log out?");
                adBuilder.setPositiveButton("Log out", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(DashboardActivity.this, "You have successfully logged out!", Toast.LENGTH_SHORT).show();
                    }
                });
                adBuilder.setNegativeButton("Cancel", null);
                adBuilder.setCancelable(true);
                adBuilder.show();
            }
        });

        accountsList = new ArrayList<>();

        accountsAdapter = new AccountsAdapter(accountsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.accountsRecyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(accountsAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        ObtainAccounts();
        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void ObtainAccounts(){
        accountsList.clear();
        DatabaseReference ref = firebaseDatabase.getReference().child("User").child(user.getUid());

        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(DashboardActivity.this, "An error occurred while retrieving data.", Toast.LENGTH_SHORT).show();
                } else {
                    currentUserData = task.getResult().getValue(Applicant.class);
                    nameTxt.setText("Welcome, " + currentUserData.getName() + "!");
                    for(Map.Entry<String, Boolean> entry: currentUserData.Accounts.entrySet()) {
                        if (entry.getValue()){
                            firebaseDatabase.getReference().child("Account").child(entry.getKey()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (task.isSuccessful()){
                                        Accounts newAccount = task.getResult().getValue(Accounts.class);
                                        Log.v("AcccountNew", newAccount.getBankNo());
                                        accountsList.add(newAccount);
                                        accountsAdapter.notifyDataSetChanged();
                                    }
                                }
                            });
                        }
                    }
                    accountsAdapter.notifyDataSetChanged();
                }
            }
        });

    }
}