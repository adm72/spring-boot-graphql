package ru.jbru.springbootgraphql.controller;

import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.jbru.springbootgraphql.model.dto.BaseResponse;
import ru.jbru.springbootgraphql.model.dto.PostDTO;
import ru.jbru.springbootgraphql.service.PostService;

import java.util.List;

@Controller
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @MutationMapping("createPost")
    public PostDTO createPost(@Argument PostDTO input) {
        return postService.createPost(input);
    }

    @QueryMapping("getPosts")
    public List<PostDTO> getPosts() {
        return postService.getPosts();
    }

    @QueryMapping("getPost")
    public PostDTO getPost(@Argument Long id) {
        return postService.getPost(id);
    }

    @MutationMapping("deletePost")
    public BaseResponse deletePost(@Argument Long id) {
        postService.deletePost(id);
        return new BaseResponse();
    }

    @MutationMapping("updatePost")
    public PostDTO updatePost(@Argument PostDTO input) {
        return postService.updatePost(input);
    }
}
