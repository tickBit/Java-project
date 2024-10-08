package com.example.tune.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

import com.example.tune.repo.*;
import com.example.tune.model.*;

@Service
public class TunePostService {

    @Autowired
    private TunePostRepository tunePostRepository;

    public void addCommentToPost(Long postId, TuneComment newComment) {
        // fetch post by postID
        TunePost tunePost = tunePostRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        // add new comment to existing list
        tunePost.addComment(newComment);

        // save post and new comment
        tunePostRepository.save(tunePost);
    }
    
    // save new post to database
    public TunePost saveTunePost(TunePost tunePost) {
        return tunePostRepository.save(tunePost);
    }

    // fetch all TunePost objects from database
    public List<TunePost> getAllTunePosts() {
        return tunePostRepository.findAll();
    }

    // fetch TunePost by ID
    public Optional<TunePost> getTunePostById(Long id) {
        return tunePostRepository.findById(id);
    }

    // delete TunePost by ID
    public void deleteTunePost(Long id) {
        
        TunePost tunePost;
        tunePost = tunePostRepository.getReferenceById(id);
        
        // delete the file too
        // at least in Linux systems, the path is absolute path to the app's uploads directory
        //System.out.println(tunePost.getFilePath());
        
        File fileObj = new File(tunePost.getFilePath()); 
        fileObj.delete();

        // delete from database
        tunePostRepository.deleteById(id);
    }
}
