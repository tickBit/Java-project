package com.example.tune.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.tune.model.TuneComment;
import com.example.tune.model.TunePost;

public interface TuneCommentRepository extends JpaRepository<TuneComment, Long> {
    
    // get all comments of a post
    List<TuneComment> findByTunePost(TunePost tunePost);
}