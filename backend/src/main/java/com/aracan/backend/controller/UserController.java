package com.aracan.backend.controller;

import com.aracan.backend.dto.UserDTO;
import com.aracan.backend.entity.User;
import com.aracan.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create_user")
    public ResponseEntity<UserDTO> createUser(@AuthenticationPrincipal Jwt jwt) {
        String cognitoSub = jwt.getSubject();
        String email = jwt.getClaimAsString("email");
        String firstName = jwt.getClaimAsString("given_name");
        String lastName = jwt.getClaimAsString("family_name");

        UserDTO userDTO = new UserDTO();

        userDTO.set_cognitoSub(cognitoSub);
        userDTO.set_email(email);
        userDTO.set_firstName(firstName);
        userDTO.set_lastName(lastName);

        userDTO = userService.createUser(userDTO);
        ResponseEntity<UserDTO> response = ResponseEntity.status(201).body(userDTO);

        return response;
    }

}
