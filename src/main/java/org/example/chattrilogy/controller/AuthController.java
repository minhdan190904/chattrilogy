package org.example.chattrilogy.controller;

import jakarta.validation.Valid;
import org.example.chattrilogy.domain.User;
import org.example.chattrilogy.domain.dto.LoginDTO;
import org.example.chattrilogy.domain.dto.RegisterDTO;
import org.example.chattrilogy.domain.dto.ResLoginDTO;
import org.example.chattrilogy.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.chattrilogy.util.SecurityUtil;

import java.util.Date;
import java.util.Random;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<ResLoginDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        System.out.println("loginDTO: " + loginDTO);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            String accessToken = this.securityUtil.createToken(authentication);
            ResLoginDTO resLoginDTO = new ResLoginDTO();
            resLoginDTO.setToken(accessToken);
            User user = userService.getUserByUserName(loginDTO.getUsername());
            resLoginDTO.setUser(user);
            return ResponseEntity.ok().body(resLoginDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterDTO registerDTO) {
        String hashPassword = passwordEncoder.encode(registerDTO.getPassword());
        Random random = new Random();
        int randomInt = random.nextInt(353) + 1;
        User user = new User(registerDTO.getName(), hashPassword, registerDTO.getUsername(), new Date(), "avatar/avatar_sample/" + randomInt + ".png", false, new Date());
        userService.handleCreateUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
