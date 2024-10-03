package com.example.tune.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class TunePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postName;
    private String filePath;

    @OneToMany(mappedBy = "tunePost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TuneComment> comments = new ArrayList<>();


    public List<TuneComment> getComments() {
        return comments;
    }

    public void setComments(List<TuneComment> comments) {
        this.comments = comments;
    }

    public void addComment(TuneComment comment) {
        // Is list intialized?
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);  // add comment to list
        comment.setTunePost(this);  // set reference on comment to the list
    }

    public void removeComment(TuneComment comment) {
        comments.remove(comment);
        comment.setTunePost(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostName() {
        return postName;
    }

    public String getFilePath() {
        return filePath;
    }
    
    public void setPostName(String postName) {
        this.postName = postName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
