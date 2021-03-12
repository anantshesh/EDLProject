package com.example.edification.models;

public class Answers {

    private String QuestionId, AnsId, Answer, AnswererName, AnswererEmail;

    public Answers() {
    }

    public Answers(String questionId, String ansId, String answer, String answererName, String answererEmail) {
        QuestionId = questionId;
        AnsId = ansId;
        Answer = answer;
        AnswererName = answererName;
        AnswererEmail = answererEmail;
    }

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String questionId) {
        QuestionId = questionId;
    }

    public String getAnsId() {
        return AnsId;
    }

    public void setAnsId(String ansId) {
        AnsId = ansId;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getAnswererName() {
        return AnswererName;
    }

    public void setAnswererName(String answererName) {
        AnswererName = answererName;
    }

    public String getAnswererEmail() {
        return AnswererEmail;
    }

    public void setAnswererEmail(String answererEmail) {
        AnswererEmail = answererEmail;
    }
}
