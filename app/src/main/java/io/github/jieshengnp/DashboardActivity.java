package io.github.jieshengnp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    ArrayList<Accounts> accountsList;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://ocbc-team4-2b3ee-default-rtdb.asia-southeast1.firebasedatabase.app/");
    Applicant currentUserData;
    AccountsAdapter accountsAdapter;
    TextView nameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        nameTxt = findViewById(R.id.dashboardWelcome);

        accountsList = new ArrayList<>();

        accountsAdapter = new AccountsAdapter(accountsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.accountsRecyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(accountsAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        ObtainAccounts();
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