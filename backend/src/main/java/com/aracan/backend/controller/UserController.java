package com.aracan.backend.controller;

import com.aracan.backend.dto.AbridgedUserDTO;
import com.aracan.backend.dto.UserDTO;
import com.aracan.backend.entity.User;
import com.aracan.backend.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/{cognitoSub}")
    public ResponseEntity<UserDTO> getUserByCognitoSub(@PathVariable String cognitoSub, @AuthenticationPrincipal Jwt jwt) {

        StringBuilder cognitoGroup = new StringBuilder(jwt.getClaimAsString("cognito:groups"));
        cognitoGroup.deleteCharAt(0);
        cognitoGroup.deleteCharAt(cognitoGroup.length()-1);

        UserDTO userDTO = new UserDTO();
        ResponseEntity<UserDTO> response = null;

        if (cognitoGroup.toString().equals("Admins") || jwt.getSubject().equals(cognitoSub)) {
            userDTO = userService.getUserByCognitoSub(cognitoSub);
            response = userDTO != null ? ResponseEntity.status(HttpStatus.OK).body(userDTO):ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return response;

    }

    @GetMapping("/all_users")
    public ResponseEntity<List<UserDTO>> getAllUsers(@AuthenticationPrincipal Jwt jwt) {

        StringBuilder cognitoGroup = new StringBuilder(jwt.getClaimAsString("cognito:groups"));
        cognitoGroup.deleteCharAt(0);
        cognitoGroup.deleteCharAt(cognitoGroup.length()-1);

        List<UserDTO> allUsers = new ArrayList<>();
        ResponseEntity<List<UserDTO>> response = null;

        if (cognitoGroup.toString().equals("Admins")) {
            allUsers = userService.getAllUsers();
            response = !allUsers.isEmpty() ? ResponseEntity.status(HttpStatus.OK).body(allUsers):ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return response;

    }

    @DeleteMapping("/delete_user/{cognitoSub}")
    public ResponseEntity<AbridgedUserDTO> deleteUser(@PathVariable String cognitoSub, @AuthenticationPrincipal Jwt jwt) {

        StringBuilder cognitoGroup = new StringBuilder(jwt.getClaimAsString("cognito:groups"));
        cognitoGroup.deleteCharAt(0);
        cognitoGroup.deleteCharAt(cognitoGroup.length() - 1);

        AbridgedUserDTO userDTO = new AbridgedUserDTO();
        ResponseEntity<AbridgedUserDTO> response = null;

        if (cognitoGroup.toString().equals("Admins") || jwt.getSubject().equals(cognitoSub)) {
            System.out.println(cognitoSub);
            userDTO = userService.deleteUser(cognitoSub);
            response = userDTO != null ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(userDTO):ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return response;

    }

}
