package com.lib.mgmt.controls;

import com.lib.mgmt.dtos.Request;
import com.lib.mgmt.dtos.Response;
import com.lib.mgmt.dtos.auth.LoginRequest;
import com.lib.mgmt.exceptions.auth.DisabledUserException;
import com.lib.mgmt.exceptions.auth.InvalidUserCredentialsException;
import com.lib.mgmt.services.auth.UserAuthService;
import com.lib.mgmt.utils.auth.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(value = "http://localhost:4200")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private JwtUtil jwtUtil;
    private UserAuthService userAuthService;
    private AuthenticationManager authenticationManager;
    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @Autowired
    public void setUserAuthService(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(
             @Valid @RequestBody LoginRequest request) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (DisabledException e) {
            throw new DisabledUserException("User Inactive");
        } catch (BadCredentialsException e) {
            throw new InvalidUserCredentialsException("Invalid Credentials");
        }
        User user = (User) authentication.getPrincipal();
        Set<String> roles = user.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toSet());
        String token = jwtUtil.generateToken(authentication);
        Response response = new Response();
        response.setToken(token);
        response.setRoles(roles.stream().collect(Collectors.toList()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody Request request) {
        String username = userAuthService.findByUserName(request.getUsername());
        if(StringUtils.isNotEmpty(username)){
            return new ResponseEntity<>("User Already Exists", HttpStatus.CONFLICT);
        }else{
            userAuthService.saveUser(request);
        }
        return new ResponseEntity<>("User "+request.getUsername()+" successfully registered", HttpStatus.OK);
    }

}
