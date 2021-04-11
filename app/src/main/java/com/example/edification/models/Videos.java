package com.example.edification.models;

public class Videos {

    String TimeStamp, VideoId, VideoTitle, VideoUrl, VideoPremierTime, Department;

    public Videos() {
    }

    public Videos(String timeStamp, String videoId, String videoTitle, String videoUrl, String videoPremierTime, String department ) {
        TimeStamp = timeStamp;
        VideoId = videoId;
        VideoTitle = videoTitle;
        VideoUrl = videoUrl;
        VideoPremierTime = videoPremierTime;
        Department = department;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getVideoPremierTime() {
        return VideoPremierTime;
    }

    public void setVideoPremierTime(String videoPremierTime) {
        VideoPremierTime = videoPremierTime;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getVideoId() {
        return VideoId;
    }

    public void setVideoId(String videoId) {
        VideoId = videoId;
    }

    public String getVideoTitle() {
        return VideoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        VideoTitle = videoTitle;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }
}
