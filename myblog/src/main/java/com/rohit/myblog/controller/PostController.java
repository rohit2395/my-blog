package com.rohit.myblog.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.myblog.common.BlogUtil;
import com.rohit.myblog.common.UIMessages;
import com.rohit.myblog.dto.ApiResponse;
import com.rohit.myblog.dto.UserPost;
import com.rohit.myblog.exceptions.BlogException;
import com.rohit.myblog.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	public static final Logger LOG = LogManager.getLogger(PostController.class);
	
	@Autowired
	private PostService postService;
	
	@PostMapping
	public ResponseEntity<ApiResponse> createPost(@RequestBody UserPost userPost) {
		LOG.info("Receieved request to create a post..");
		try {
			postService.createPost(userPost);
		} catch (BlogException e) {
			return new ResponseEntity<ApiResponse>(BlogUtil.buildApiResoponse(e),e.getErrorCode());
		}
		ResponseEntity<ApiResponse> res = new ResponseEntity<ApiResponse>(
				BlogUtil.buildApiResoponse(UIMessages.POST_CREATED,HttpStatus.CREATED)
				,HttpStatus.OK);
		return res;
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> showAllPosts() {
		LOG.info("Receieved request to retrieve all posts..");
		List<UserPost> posts = null;
		try {
			posts = postService.showAllPosts();
		} catch (BlogException e) {
			return new ResponseEntity<ApiResponse>(BlogUtil.buildApiResoponse(e),e.getErrorCode());
		}
		ResponseEntity<List<UserPost>> res = new ResponseEntity<>(posts,HttpStatus.OK);
		return res;
	}
	
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getSinglePost(@PathVariable @RequestBody Long id) {
		LOG.info("Receieved request to retrieve all posts..");
		UserPost post = null;
		try {
			post = postService.getSinglePost(id);
		} catch (BlogException e) {
			return new ResponseEntity<ApiResponse>(BlogUtil.buildApiResoponse(e),e.getErrorCode());
		}
		ResponseEntity<UserPost> res = new ResponseEntity<>(post,HttpStatus.OK);
		return res;
	}
}
