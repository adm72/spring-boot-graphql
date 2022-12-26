package ru.jbru.springbootgraphql.service;

import ru.jbru.springbootgraphql.model.PostEntity;
import ru.jbru.springbootgraphql.model.dto.PostDTO;

import java.util.List;

public interface PostService {
    PostEntity getById(Long postId);

    PostDTO getPost(Long postId);

    List<PostDTO> getPosts();

    PostDTO createPost(PostDTO postDTO);

    PostDTO updatePost(PostDTO postDTO);

    void deletePost(Long postId);

}
