package com.example.edification.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edification.R;
import com.example.edification.indidualQuestions;
import com.example.edification.models.Questions;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class questionsAdapter extends RecyclerView.Adapter<questionsAdapter.MyHolder> {

    Context context;
    List<Questions> questionsList;

    public questionsAdapter(Context context, List<Questions> questionsList){
        this.context = context;
        this.questionsList = questionsList;
    }

    @NonNull
    @Override
    public questionsAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.question_row, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull questionsAdapter.MyHolder holder, int position) {

        final String questionId = questionsList.get(position).getQuestionId();
        final String email = questionsList.get(position).getEmail();
        final String department  = questionsList.get(position).getDepartment();
        final  String question = questionsList.get(position).getQuestion();
        final  String name = questionsList.get(position).getName();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(questionId));

        String pTime = (String) DateFormat.format("dd/MM/yyyy hh:mm aa", calendar);

        holder.name.setText(name);
        holder.time.setText(pTime);
        holder.question.setText(question);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, indidualQuestions.class);
                intent.putExtra("question", questionId);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView name, time, question, count;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.userName);
            time = itemView.findViewById(R.id.timestamp);
            question = itemView.findViewById(R.id.question);

        }
    }
}
