package io.github.jieshengnp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import java.util.List;

public class CurrentApplications extends AppCompatActivity {

    TextView welcomeTxt;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://ocbc-team4-2b3ee-default-rtdb.asia-southeast1.firebasedatabase.app/");
    ArrayList<Application> applicationList;
    String userNRIC, name;
    ImageView homeLayout, applicationLayout, settingLayout;
    CurrentApplicationsAdapter customApplicationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_applications);

        homeLayout = findViewById(R.id.barHomeLayout2);
        applicationLayout = findViewById(R.id.barApplicationLayout2);
        settingLayout = findViewById(R.id.barSettingLayout2);
        welcomeTxt = findViewById(R.id.dashboardWelcome2);
        applicationList = new ArrayList<>();

        user = FirebaseAuth.getInstance().getCurrentUser();

        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurrentApplications.this, DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurrentApplications.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ObtainApplications();
    }

    private void ObtainApplications(){
        applicationList.clear();
        firebaseDatabase.getReference().child("User").child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    User user = (task.getResult().getValue(User.class));
                    userNRIC = user.getNRIC();
                    name = user.getName();
                    welcomeTxt.setText("Welcome, " + name+ "!");
                    customApplicationAdapter = new CurrentApplicationsAdapter(applicationList, userNRIC);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    RecyclerView recyclerView = findViewById(R.id.rv_currentApp);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(customApplicationAdapter);

                    firebaseDatabase.getReference().child("Application").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snap : snapshot.getChildren()){
                                boolean exist = false;
                                Application app = snap.getValue(Application.class);
                                if (app.getApplicantList().size() == 1){
                                    if (app.getApplicantList().get(0).getNRIC().equals(userNRIC)){
                                        exist = true;
                                    }
                                } else if (app.getApplicantList().size() == 2){
                                    if (app.getApplicantList().get(1).getNRIC().equals(userNRIC)){
                                        exist = true;
                                    }
                                }
                                if (exist){
                                    applicationList.add(app);
                                }
                            }
                            customApplicationAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "An error occured while retrieving applications!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}