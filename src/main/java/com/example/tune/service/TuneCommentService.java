package com.example.tune.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.example.tune.repo.*;
import com.example.tune.model.*;

@Service
public class TuneCommentService {

    @Autowired
    private TuneCommentRepository tuneCommentRepository;

    // save new comment to the database
    public TuneComment saveTuneComment(TuneComment tuneComment) {
        return tuneCommentRepository.save(tuneComment);
    }

    // fetch all comments related to a TunePost object
    public List<TuneComment> getCommentsByTunePost(TunePost tunePost) {
        return tuneCommentRepository.findByTunePost(tunePost);
    }

    // delete comment by id
    public void deleteTuneComment(Long id) {
        tuneCommentRepository.deleteById(id);
    }
}