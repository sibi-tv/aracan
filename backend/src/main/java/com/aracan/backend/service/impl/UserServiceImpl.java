package com.aracan.backend.service.impl;

import com.aracan.backend.dto.UserDTO;
import com.aracan.backend.entity.User;
import com.aracan.backend.repository.UserRepository;
import com.aracan.backend.service.UserService;
import com.aracan.backend.utils.DTOEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DTOEntityConverter dtoEntityConverter;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.existsById(userDTO.get_cognitoSub())) {
            throw new IllegalStateException("User with ID " + userDTO.get_cognitoSub() + " already exists.");
        }

        User userEntity = dtoEntityConverter.userDTOToUserEntity(userDTO);

        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setUpdatedAt(LocalDateTime.now());

        userEntity = userRepository.save(userEntity);
        userDTO = dtoEntityConverter.userEntityToUserDTO(userEntity);

        return userDTO;
    }

}
