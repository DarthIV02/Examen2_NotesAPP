package com.example.notesapp2.models;

public class Note {
    private String id;
    private String text;
    private String lastModified;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLastModified(){
        return lastModified;
    }

    public void setLastModified(String lastModified){
        this.lastModified = lastModified;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }
}