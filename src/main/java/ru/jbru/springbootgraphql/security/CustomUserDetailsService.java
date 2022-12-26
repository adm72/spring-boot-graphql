package ru.jbru.springbootgraphql.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.jbru.springbootgraphql.model.UserEntity;
import ru.jbru.springbootgraphql.repository.UserRepository;

import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User not found, email: %s", email)));
        return userBuild(user);
    }

    public UserDetails loadUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User not found, id: %s", id)));
        return userBuild(user);
    }

    private CustomUserDetails userBuild(UserEntity user) {
        Collection<CustomGrantedAuthority> authorities = user.getRoles().stream()
                .map(CustomGrantedAuthority::new)
                .toList();
        return new CustomUserDetails(user.getId(), user.getEmail(), user.getPassword(), authorities);
    }
}
