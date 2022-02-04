package io.github.jieshengnp;

import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CurrentApplicationsViewHolder extends RecyclerView.ViewHolder{
    TextView name1, name2, status;
    ConstraintLayout constraintLayout;
    public CurrentApplicationsViewHolder(View itemView){
        super(itemView);
        name1 = itemView.findViewById(R.id.rv_app_name1);
        name2 = itemView.findViewById(R.id.rv_app_name2);
        status = itemView.findViewById(R.id.rv_app_result);
        constraintLayout = itemView.findViewById(R.id.rv_app_constaint);
    }
}
