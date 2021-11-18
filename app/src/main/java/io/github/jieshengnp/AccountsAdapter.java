package io.github.jieshengnp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsViewHolder>{
    ArrayList<Accounts> accountsList;

    public AccountsAdapter(ArrayList<Accounts> accountsList){
        this.accountsList = accountsList;
    }

    @Override
    public AccountsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_accounts, parent, false);
        return new AccountsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AccountsViewHolder holder, int position) {
        Accounts account = accountsList.get(position);
        holder.accountBal.setText("$" + String.format("%,.2f", account.getBalance()));
        holder.accountNo.setText(account.getBankNo());
        String typeStatus = account.getType() + " Account";
        if (account.getStatus().equals("Restricted")){
            typeStatus += " " + "(Restricted)";
        }
        holder.accountTypeStatus.setText(typeStatus);
        holder.accountLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AccountDetailsActivity.class);
                intent.putExtra("Account", accountsList.get(position));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountsList.size();
    }
}
