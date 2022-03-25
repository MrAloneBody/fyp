package com.example.fyp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.ViewHolder>{

    //variables

    private List<Model> modelList;
    private Context  context;

    //constructor


    public MyAdaptor(List<Model> modelList, Context context) {

        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup , int position) {

        //this method will be called whenever our viewholder will be created
        //inflate the layout row.xml

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup , false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //this will bind the data to viewholder from where it'll be shown in other groups
        Model model= modelList.get(position);
        holder.team1tv.setText(model.getTeam1());
        holder.team2tv.setText(model.getTeam2());
        holder.matchtypetv.setText(model.getMatchtype());
        holder.matchstatustv.setText(model.getMatchstatus());
        holder.datetv.setText(model.getDate());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Show dialog on click with options of "Match Details" and "PLayers Details"

                String matchid = model.getId();
                String  date= model.getDate();

                //options to Display in dialog
                String[] options= {"Match Detials","Players List","Match Summary"};

                //Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                builder.setTitle("Choose Option");//Title

                //set options to dialog box
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which ==0){
                            //Match details is clicked
                            Intent intent = new Intent(context, MatchDetailActivity.class);
                            intent.putExtra("match_id",matchid);
                            intent.putExtra("date",date);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);


                        }

                        if (which ==1){
                            //PLayers list is clicked

                            Intent intent = new Intent(context, PlayersActivity.class);
                            intent.putExtra("match_id",matchid);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                        }
                        if (which ==2){
                            //MatchSummary is clicked

                            Intent intent = new Intent(context, MatchSummaryActivity.class);
                            intent.putExtra("match_id",matchid);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                        }
                    }
                });
                builder.create().show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        
        //define view objects
        
        TextView team1tv, team2tv, matchtypetv, matchstatustv, datetv;
        CardView cardView;
        
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            team1tv = itemView.findViewById(R.id.team1tv);
            team2tv =itemView.findViewById(R.id.team2tv);
            matchtypetv =itemView.findViewById(R.id.matchtypetv);
            matchstatustv =itemView.findViewById(R.id.matchstatustv);
            datetv =itemView.findViewById(R.id.datetv);
            cardView =itemView.findViewById(R.id.cardview);


        }
    }
    
}
