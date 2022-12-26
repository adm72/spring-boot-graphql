package ru.jbru.springbootgraphql.controller;

import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.jbru.springbootgraphql.model.dto.UserDTO;
import ru.jbru.springbootgraphql.service.UserService;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @QueryMapping("getUser")
    public UserDTO getUser(@Argument Long id) {
        return userService.getUser(id);
    }

    @QueryMapping("getUsers")
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @QueryMapping("getCurrentUser")
    public UserDTO getCurrentUser() {
        Long id = userService.currentUserId();
        return userService.getUser(id);
    }

}
