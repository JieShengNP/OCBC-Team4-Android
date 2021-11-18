package io.github.jieshengnp;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AccountsViewHolder extends RecyclerView.ViewHolder{
    TextView accountNo, accountTypeStatus, accountBal;
    LinearLayout accountLinearLayout;
    public AccountsViewHolder(View itemView){
        super(itemView);
        accountNo = itemView.findViewById(R.id.recyclerViewAccountNo);
        accountTypeStatus = itemView.findViewById(R.id.recyclerViewAccountTypeStatus);
        accountBal = itemView.findViewById(R.id.recyclerViewAccountBal);
        accountLinearLayout = itemView.findViewById(R.id.accountsLinearLayout);
    }
}
