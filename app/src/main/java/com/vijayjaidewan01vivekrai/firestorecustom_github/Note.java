package com.vijayjaidewan01vivekrai.firestorecustom_github;

/**
 * Created by MR VIVEK RAI on 01-06-2018.
 */
public class Note {

    private String title;
    private String description;
    private int priority;

    public Note() {
    }

    public Note(String title, String description , int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
