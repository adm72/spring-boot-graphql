package ru.jbru.springbootgraphql.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jbru.springbootgraphql.exeption.CustomException;
import ru.jbru.springbootgraphql.model.UserEntity;
import ru.jbru.springbootgraphql.model.dto.UserDTO;
import ru.jbru.springbootgraphql.repository.UserRepository;
import ru.jbru.springbootgraphql.security.CustomUserDetails;
import ru.jbru.springbootgraphql.service.UserService;

import java.util.List;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream()
                .map(i -> modelMapper.map(i, UserDTO.class))
                .toList();
    }

    @Override
    public UserDTO getUser(Long id) {
        UserEntity user = getById(id);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserEntity getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(String.format("The user doesn't exist, id: %s", id), HttpStatus.NOT_FOUND));
    }

    @Override
    public Long currentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.toString().equals("anonymousUser")) {
            return null;
        }
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return customUserDetails.getId();
    }
}
