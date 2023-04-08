package com.lib.mgmt.services.auth;

import java.util.List;
import java.util.stream.Collectors;

import com.lib.mgmt.dtos.Request;
import com.lib.mgmt.models.auth.User;
import com.lib.mgmt.models.auth.UserRole;
import com.lib.mgmt.repos.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username).get();

        List<UserRole> userRoles = user.getUserRoles().stream().collect(Collectors.toList());

        List<GrantedAuthority> grantedAuthorities = userRoles.stream().map(r -> {
            return new SimpleGrantedAuthority(r.getRole());
        }).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(username, user.getUserPass(), grantedAuthorities);
    }

    public void saveUser(Request request) {
        if (userRepository.findByUserName(request.getUserName()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setUserName(request.getUserName());
        user.setUserPass(passwordEncoder.encode(request.getUserPwd()));

        user.setUserRoles(request.getRoles().stream().map(r -> {
            UserRole ur = new UserRole();
            ur.setRole(r);
            return ur;
        }).collect(Collectors.toSet()));

        userRepository.save(user);
    }

}

