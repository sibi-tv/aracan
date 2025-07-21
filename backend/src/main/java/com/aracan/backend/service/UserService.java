package com.aracan.backend.service;

import com.aracan.backend.dto.AbridgedUserDTO;
import com.aracan.backend.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserByCognitoSub(String cognitoSub);
    List<UserDTO> getAllUsers();
//    UserDTO updateUser(String cognitoSub, UserDTO userDTO);
    AbridgedUserDTO deleteUser(String cognitoSub);

}
