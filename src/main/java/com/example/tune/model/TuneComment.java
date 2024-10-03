package com.example.tune.model;

import jakarta.persistence.*;

@Entity
public class TuneComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String text;

    public void setText(String text) {
        this.text = text;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tune_post_id")
    private TunePost tunePost;

    public TunePost getTunePost() {
        return tunePost;
    }

    public void setTunePost(TunePost tunePost) {
        this.tunePost = tunePost;
    }

    public String getText() {
        return this.text;
    }
}
