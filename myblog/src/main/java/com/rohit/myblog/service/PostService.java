package com.rohit.myblog.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;

import com.rohit.myblog.dto.UserPost;
import com.rohit.myblog.exceptions.BlogException;
import com.rohit.myblog.exceptions.Error;
import com.rohit.myblog.model.Post;
import com.rohit.myblog.repo.PostRepository;

@Service
public class PostService {

	public static final Logger LOG = LogManager.getLogger(PostService.class);

	@Autowired
	private LoginService loginService;

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Transactional
	public void createPost(UserPost userPost) throws BlogException {
		LOG.info("Creating new user post");
		
		Post post = mapFromDtoToPost(userPost);
		if(post != null) { 
			post.setName(userDetailsService.loadAppUserByUsername(userPost.getUsername()).getName());
			try {
				postRepository.save(post);
			}catch(Exception e) {
				LOG.error("Failed to created new user post",e);
				throw new BlogException(Error.FAILED_TO_CREATE_NEW_POST,e);
			}
		}
		else {
			LOG.error("Failed to created new user post");
			throw new BlogException(Error.FAILED_TO_CREATE_NEW_POST,null);
		}
			
		LOG.info("New usr post created successfully");
	}

	@Transactional
	public List<UserPost> showAllPosts() throws BlogException {
		LOG.info("Getting all the posts..");
		List<UserPost> userPosts = null;
		try {
			List<Post> posts = postRepository.findAll();
			userPosts = posts.stream().map(this::mapFromPostToDto).collect(Collectors.toList());
		}catch(Exception e) {
			LOG.error("Failed to get all posts",e);
			throw new BlogException(Error.FAILED_TO_GE_ALL_POSTS, e);
		}
		if(userPosts == null) {
			LOG.error("Failed to get all posts");
			throw new BlogException(Error.FAILED_TO_GE_ALL_POSTS, null);
		}
		return userPosts;
	}

	@Transactional
	public UserPost getSinglePost(Long id) throws BlogException {
		LOG.info("Getting single post of id {}",id);
		Post post = null;
		try {
			post = postRepository.findById(id).orElseThrow(() -> new BlogException(Error.BLOG_POST_NOT_FOUND,new String[] {id.toString()},null));
		}catch(BlogException e) {
			throw e;
		}catch(Exception e) {
			LOG.error("Failed to get the post",e);
			new BlogException(Error.BLOG_POST_NOT_FOUND,new String[] {id.toString()},e);
		}
		if(post == null) {
			LOG.error("Failed to get the post");
			new BlogException(Error.BLOG_POST_NOT_FOUND,new String[] {id.toString()},null);
		}
		return mapFromPostToDto(post);
	}

	private Post mapFromDtoToPost(UserPost userPost) throws BlogException {
		Post post = new Post();
		if(userPost != null) {
			post.setId(userPost.getId());
			post.setTitle(userPost.getTitle());
			post.setContent(userPost.getContent());
			User user = loginService.getCurrentUser().orElseThrow(() -> new BlogException(Error.USER_NOT_LOGGED_IN, null));
			post.setUsername(user.getUsername());
			post.setCreatedOn(Instant.now());
		}
		return post;
	}

	private UserPost mapFromPostToDto(Post post) {
		UserPost userPost = new UserPost();
		if(post != null) {
			userPost.setId(post.getId());
			userPost.setTitle(post.getTitle());
			userPost.setContent(post.getContent());
			userPost.setUsername(post.getUsername());
			userPost.setName(post.getName());
			userPost.setCreatedOn(post.getCreatedOn());
		}
		return userPost;
	}

}
