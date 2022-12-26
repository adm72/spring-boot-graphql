package ru.jbru.springbootgraphql.service;

import ru.jbru.springbootgraphql.model.UserEntity;
import ru.jbru.springbootgraphql.model.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();

    UserDTO getUser(Long id);

    UserEntity getById(Long id);

    Long currentUserId();
}
