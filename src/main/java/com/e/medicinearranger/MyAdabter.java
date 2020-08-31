package com.e.medicinearranger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdabter extends RecyclerView.Adapter<MyAdabter.MyViewHolder> {

    Context context;
    ArrayList<UserHelper>userHelpers;

    public MyAdabter(Context c,ArrayList<UserHelper>p ){
        context = c;
        userHelpers=p;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.med_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(userHelpers.get(position).getName());
        holder.duration.setText(userHelpers.get(position).getDuration());
        holder.time.setText(userHelpers.get(position).getHour()+":"+userHelpers.get(position).getMin()+" "+userHelpers.get(position).getFormat());
        holder.doses.setText((userHelpers.get(position).getDoses()));
        holder.beforAfter.setText(userHelpers.get(position).getBeforAfter());
        holder.spinnercolour.setText(userHelpers.get(position).getSpinnercolour());
        holder.spinnertype.setText(userHelpers.get(position).getSpinnertype());
        holder.day.setText("");

        for (int i =0;i<userHelpers.get(position).getDay().size();i++){
            if (i==0)
                holder.day.setText( holder.day.getText()+userHelpers.get(position).getDay().get(i));
            else
                holder.day.setText( holder.day.getText()+","+userHelpers.get(position).getDay().get(i));

        }
    }

    @Override
    public int getItemCount() {
        return userHelpers.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,day,time,beforAfter,doses,spinnertype,duration,spinnercolour;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.namemedicine);
            day =(TextView)itemView.findViewById(R.id.daymed);
            time =(TextView)itemView.findViewById(R.id.timemed);
            doses = (TextView)itemView.findViewById(R.id.dosemedicine);
            beforAfter = (TextView)itemView.findViewById(R.id.beforafter);
            spinnertype =(TextView)itemView.findViewById(R.id.typeee);
            duration = (TextView)itemView.findViewById(R.id.dura);
            spinnercolour=(TextView)itemView.findViewById(R.id.colormed);


        }
    }
}
