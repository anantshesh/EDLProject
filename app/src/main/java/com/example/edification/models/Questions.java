package com.example.edification.models;

public class Questions {

    private String Department, Email, Question, QuestionId, Uid, Name;

    public Questions() {
    }

    public Questions(String department, String email, String question, String questionId, String uid, String name) {
        Department = department;
        Email = email;
        Question = question;
        QuestionId = questionId;
        Uid = uid;
        Name = name;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String questionId) {
        QuestionId = questionId;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
