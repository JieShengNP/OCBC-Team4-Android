package io.github.jieshengnp;

import android.view.LayoutInflater;
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
        switch(viewType) {
            case 1: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_actions, parent, false);
                return new AccountsViewHolder(itemView);
            }
            case 0:
            default: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_accounts, parent, false);
                return new AccountsViewHolder(itemView);
            }
        }
    }

    @Override
    public void onBindViewHolder(AccountsViewHolder holder, int position) {
        if (position == accountsList.size()){
            return;
        }
        Accounts account = accountsList.get(position);
        holder.accountBal.setText("$" + account.getBalance());
        holder.accountNo.setText(account.getBankNo());
        String typeStatus = account.getType() + " Account";
        if (account.getStatus() == "Restricted"){
            typeStatus += " " + "(Restricted)";
        }
        holder.accountTypeStatus.setText(typeStatus);
    }

    @Override
    public int getItemCount() {
        return accountsList.size()+1;
    }

    @Override
    public int getItemViewType(int position){
        if (position == accountsList.size()){
            return 1;
        }
        return 0;
    }
}
