package com.example.edification.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edification.R;
import com.example.edification.models.Videos;

import java.util.ArrayList;
import java.util.Calendar;

public class videosAdapter extends RecyclerView.Adapter<videosAdapter.HolderVideo>{

    Context context;
    private ArrayList<Videos> videosArrayList;

    public videosAdapter(Context context, ArrayList<Videos> videosArrayList) {
        this.context = context;
        this.videosArrayList = videosArrayList;
    }

    @NonNull
    @Override
    public HolderVideo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_videos, parent, false);
        return new HolderVideo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderVideo holder, int position) {

        Videos videos = videosArrayList.get(position);

        String id = videos.getVideoId();
        String title1 = videos.getVideoTitle();
        String time1 = videos.getTimeStamp();
        String video1 = videos.getVideoUrl();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(time1));
        String date = DateFormat.format("dd/MM/yyyy hh:mm a" ,calendar).toString();

        holder.title.setText(title1);
        holder.time.setText(date);
        setVideoUrl(videos, holder);




    }

    private void setVideoUrl(Videos videos, HolderVideo holder) {

        //holder.progressBar.setVisibility(View.VISIBLE);

        String videoUrl = videos.getVideoUrl();

        MediaController mediaController = new MediaController(context);
        mediaController.setAnchorView(holder.videoView);

        Uri videoUri = Uri.parse(videoUrl);
        holder.videoView.setMediaController(mediaController);
        holder.videoView.setVideoURI(videoUri);

        holder.videoView.requestFocus();
        holder.videoView.setOnPreparedListener(mp -> mp.stop());

        holder.videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what){
                    case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:{
                       // holder.progressBar.setVisibility(View.VISIBLE);
                        return true;
                    }
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:{
                       // holder.progressBar.setVisibility(View.VISIBLE);
                        return true;
                    }
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:{
                      //  holder.progressBar.setVisibility(View.GONE);
                        return true;
                    }
                }
                return false;
            }
        });

        holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return videosArrayList.size();
    }

    class HolderVideo extends RecyclerView.ViewHolder{

        TextView title, time;
        VideoView videoView;
        ProgressBar progressBar;


        public HolderVideo(@NonNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.videoView);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
           // progressBar = itemView.findViewById(R.id.progressVideo);
        }
    }
}
