package org.example.controllers;

import org.example.dto.AuthRequest;
import org.example.models.entity.User;
import org.example.models.repos.UserRepo;
import org.example.security.jwt.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private AuthenticationManager authenticationManager;
    private UserRepo userRepo;
    private JwtTokenUtil jwtTokenUtil;

    public UserController(AuthenticationManager authenticationManager, UserRepo userRepo, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(), request.getPassword()
                )
        );

        User user = userRepo.findByLogin(request.getLogin()).get();
        return jwtTokenUtil.generateToken(user);
    }
}
