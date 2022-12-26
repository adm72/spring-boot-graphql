package ru.jbru.springbootgraphql.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jbru.springbootgraphql.exeption.CustomException;
import ru.jbru.springbootgraphql.model.PostEntity;
import ru.jbru.springbootgraphql.model.UserEntity;
import ru.jbru.springbootgraphql.model.dto.PostDTO;
import ru.jbru.springbootgraphql.repository.PostRepository;
import ru.jbru.springbootgraphql.service.PostService;
import ru.jbru.springbootgraphql.service.UserService;

import java.util.List;

@Slf4j
@Service
@Transactional
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @Override
    public PostEntity getById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(String.format("Post not found, id=%s", postId), HttpStatus.NOT_FOUND));
    }

    @Override
    public PostDTO getPost(Long postId) {
        PostEntity post = getById(postId);
        return mapper.map(post, PostDTO.class);
    }

    @Override
    public List<PostDTO> getPosts() {
        return postRepository.findAll().stream()
                .map(i -> mapper.map(i, PostDTO.class))
                .toList();
    }

    @Override
    public PostDTO createPost(PostDTO dto) {
        Long userId = userService.currentUserId();
        UserEntity user = userService.getById(userId);
        PostEntity post = PostEntity.builder()
                .user(user)
                .shortPost(dto.getShortPost())
                .coverImage(dto.getCoverImage())
                .text(dto.getText())
                .title(dto.getTitle())
                .build();
        post = postRepository.save(post);
        return mapper.map(post, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO dto) {
        Long userId = userService.currentUserId();
        PostEntity post = getById(dto.getId());
        if (post.getUser().getId().equals(userId)) {
            post.setShortPost(dto.getShortPost());
            post.setText(dto.getText());
            post.setTitle(dto.getTitle());
            post.setCoverImage(dto.getCoverImage());
            post = postRepository.save(post);
            return mapper.map(post, PostDTO.class);
        } else {
            log.error("Access is denied, post: {}, user: {}", dto.getId(), userId);
            throw new AccessDeniedException(String.format("Access is denied, post: %d, user: %d", dto.getId(), userId));
        }
    }

    @Override
    public void deletePost(Long postId) {
        Long userId = userService.currentUserId();
        PostEntity post = getById(postId);
        if (post.getUser().getId().equals(userId)) {
            postRepository.delete(post);
        } else {
            log.error("Access is denied, post: {}, user: {}", postId, userId);
            throw new AccessDeniedException(String.format("Access is denied, post: %d, user: %d", postId, userId));
        }
    }
}
