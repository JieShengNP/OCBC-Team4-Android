package io.github.jieshengnp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ArrayList<Accounts> accountsList = new ArrayList<>();

        Accounts account = new Accounts();
        account.setBalance(120.30);
        account.setBankNo("111-11111-111");
        account.setStatus("Active");
        account.setType("Joint");
        accountsList.add(account);
        Accounts account2 = new Accounts();
        account2.setBankNo("222-22222-222");
        account2.setStatus("Restricted");
        account2.setBalance(345.30);
        account2.setType("Joint");
        accountsList.add(account2);


        AccountsAdapter accountsAdapter = new AccountsAdapter(accountsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.accountsRecyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(accountsAdapter);
        accountsAdapter.notifyDataSetChanged();
    }
}