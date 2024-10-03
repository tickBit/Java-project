package com.example.tune.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tune.model.TunePost;

public interface TunePostRepository extends JpaRepository<TunePost, Long> {
    // Mukautettuja kyselyitä voi lisätä tarvittaessa
}