package com.serverless.db.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

import java.util.List;

public class Question {
    private String id;
    private String formId;
    private String type;
    private String language;
    private String question;
    private String answer;
    private List<String> answerList;

    public Question() {}
    public Question(String formId, String type, String language, String question) {
        this.formId = formId;
        this.type = type;
        this.language = language;
        this.question = question;
    }

    public Question(String formId, String type, String language, String question, String answer, List<String> answerList) {
        this.formId = formId;
        this.type = type;
        this.language = language;
        this.question = question;
        this.answer = answer;
        this.answerList = answerList;
    }
    @DynamoDBHashKey(attributeName = "id")
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @DynamoDBAttribute(attributeName = "formId")
    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }
    @DynamoDBAttribute(attributeName = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @DynamoDBAttribute(attributeName = "lang")
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    @DynamoDBAttribute(attributeName = "question")
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
    @DynamoDBAttribute(attributeName = "answer")
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    @DynamoDBAttribute(attributeName = "answerList")
    public List<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }
}
