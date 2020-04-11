package com.rohit.myblog.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.myblog.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

}
