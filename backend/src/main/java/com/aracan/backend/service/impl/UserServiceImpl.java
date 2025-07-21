package com.aracan.backend.service.impl;

import com.aracan.backend.dto.AbridgedUserDTO;
import com.aracan.backend.dto.UserDTO;
import com.aracan.backend.entity.User;
import com.aracan.backend.repository.UserRepository;
import com.aracan.backend.service.UserService;
import com.aracan.backend.utils.DTOEntityConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public UserDTO getUserByCognitoSub(String cognitoSub) { //needs to handle case where id does not exist

        Optional<User> optUserEntity = userRepository.findById(cognitoSub);
        UserDTO userDTO = optUserEntity.map(user -> dtoEntityConverter.userEntityToUserDTO(user)).orElse(null);

        return userDTO;

    }

    @Override
    public List<UserDTO> getAllUsers() {

        List<UserDTO> allUsers = new ArrayList<>();

        if (!userRepository.findAll().isEmpty()) {
            for (User userEntity : userRepository.findAll()) {
                UserDTO userDTO = dtoEntityConverter.userEntityToUserDTO(userEntity);
                allUsers.add(userDTO);
            }
        }

        return allUsers;

    }

    @Override
    @Transactional
    public AbridgedUserDTO deleteUser(String cognitoSub) {

        AbridgedUserDTO userDTO = new AbridgedUserDTO();
        Optional<User> optUserEntity = userRepository.findById(cognitoSub);

        if (optUserEntity.isPresent()) {
            User userEntity = optUserEntity.get();
            BeanUtils.copyProperties(userEntity, userDTO);
            userRepository.delete(userEntity);
        }

        return userDTO;

    }

}
