package com.lib.mgmt.models.library;

import javax.persistence.*;

@Entity
@Table(name = "Tutorial")
public class Tutorial {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private boolean isPublished;
    public Tutorial(){}
    public Tutorial(long id, String title, String description, boolean isPublished) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isPublished = isPublished;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }
}
