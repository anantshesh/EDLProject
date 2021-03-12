package com.example.edification.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edification.R;
import com.example.edification.models.Answers;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class answerAdapter extends RecyclerView.Adapter<answerAdapter.MyHolder> {

    Context context;
    List<Answers> answersList;

    public answerAdapter(Context context, List<Answers> answersList) {
        this.context = context;
        this.answersList = answersList;

    }

    @NonNull
    @Override
    public answerAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.answer_row, parent, false);
        return new MyHolder(view);
           }

    @Override
    public void onBindViewHolder(@NonNull answerAdapter.MyHolder holder, int position) {

        final String ans = answersList.get(position).getAnswer();
        final String time = answersList.get(position).getAnsId();
        final String Uname = answersList.get(position).getAnswererName();
        final String Uemail = answersList.get(position).getAnswererEmail();


        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(time));

        String pTime = (String) DateFormat.format("dd/MM/yyyy hh:mm:ss:mm aa", calendar);

        holder.time.setText(pTime);
        holder.answer.setText(ans);
        holder.email.setText(Uemail);
        holder.name.setText(Uname);

    }

    @Override
    public int getItemCount() {
        return answersList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView name, email, time, answer;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.NameTv);
            email = itemView.findViewById(R.id.email);
            time = itemView.findViewById(R.id.TimeTv);
            answer = itemView.findViewById(R.id.answer);
        }
    }
}
