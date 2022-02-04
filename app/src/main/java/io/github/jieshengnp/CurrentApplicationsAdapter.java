package io.github.jieshengnp;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CurrentApplicationsAdapter extends RecyclerView.Adapter<CurrentApplicationsViewHolder>{
    ArrayList<Application> applicationList;
    String NRIC;

    public CurrentApplicationsAdapter(ArrayList<Application> applicationList, String NRIC){
        this.applicationList = applicationList;
        this.NRIC = NRIC;
    }

    @Override
    public CurrentApplicationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_application, parent, false);
        return new CurrentApplicationsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CurrentApplicationsViewHolder holder, int position) {
        if (applicationList.get(position).getApplicantAccepted().isEmpty()) {
            if (applicationList.get(position).getApplicantList().size() == 1) {
                holder.name1.setText(applicationList.get(position).getApplicantList().get(0).getName());
                holder.name2.setText("No applicant yet.");
                holder.status.setText("Awaiting New Applicant");
                holder.status.setTextColor(Color.rgb(246,190,0));
            } else if (applicationList.get(position).getApplicantList().size() == 2) {
                holder.name1.setText(applicationList.get(position).getApplicantList().get(0).getName());
                holder.name2.setText(applicationList.get(position).getApplicantList().get(1).getName());
                holder.status.setText("Both Applicant Registered");
                holder.status.setTextColor(Color.rgb(0,100,0));
                holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.name1.getContext(), ConfirmationPage.class);
                        intent.putExtra("Application", applicationList.get(position));
                        holder.name1.getContext().startActivity(intent);
                    }
                });
            }
        } else {
            if (applicationList.get(position).getApplicantAccepted().containsValue(false)){
                holder.name1.setText(applicationList.get(position).getApplicantList().get(0).getName());
                holder.name2.setText(applicationList.get(position).getApplicantList().get(1).getName());
                holder.status.setText("Application Cancelled");
                holder.status.setTextColor(Color.rgb(100,0,0));
            }
            else if (applicationList.get(position).getApplicantAccepted().containsValue(true)) {
                if (!applicationList.get(position).getApplicantAccepted().containsKey(NRIC)) {
                    holder.name1.setText(applicationList.get(position).getApplicantList().get(0).getName());
                    holder.name2.setText(applicationList.get(position).getApplicantList().get(1).getName());
                    holder.status.setText("Awaiting Confirmation");
                    holder.status.setTextColor(Color.rgb(246,190,0));
                    holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(holder.name1.getContext(), ConfirmationPage.class);
                            intent.putExtra("Application", applicationList.get(position));
                            holder.name1.getContext().startActivity(intent);
                        }
                    });
                } else {
                    if (applicationList.get(position).getApplicantAccepted().size() == 2) {
                        holder.name1.setText(applicationList.get(position).getApplicantList().get(0).getName());
                        holder.name2.setText(applicationList.get(position).getApplicantList().get(1).getName());
                        holder.status.setText("Successful Application!");
                        holder.status.setTextColor(Color.rgb(0,100,0));
                    } else {
                        holder.name1.setText(applicationList.get(position).getApplicantList().get(0).getName());
                        holder.name2.setText(applicationList.get(position).getApplicantList().get(1).getName());
                        holder.status.setText("Awaiting Applicant #2");
                        holder.status.setTextColor(Color.rgb(246,190,0));
                    }
                }
            }
            else {
                holder.name1.setText(applicationList.get(position).getApplicantList().get(0).getName());
                holder.name2.setText(applicationList.get(position).getApplicantList().get(1).getName());
                holder.status.setText("Awaiting Confirmation");
                holder.status.setTextColor(Color.rgb(246,190,0));
                holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.name1.getContext(), ConfirmationPage.class);
                        intent.putExtra("Application", applicationList.get(position));
                        holder.name1.getContext().startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }
}
