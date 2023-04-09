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
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    public String findByUserName(String username) {
        if (userRepository.findByUserName(username).isPresent()) {
            return username;
        }
        return null;
    }
    public String saveUser(Request request) {
        User user = new User();
        user.setUserName(request.getUsername());
        user.setUserPass(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        user.setUserRoles(request.getRoles().stream().map(r -> {
            UserRole ur = new UserRole();
            ur.setRole(r);
            return ur;
        }).collect(Collectors.toSet()));

        userRepository.save(user);
        return "User "+request.getUsername()+" Saved Successfully";
    }

}

