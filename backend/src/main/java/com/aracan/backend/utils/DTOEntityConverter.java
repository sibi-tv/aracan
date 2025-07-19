package com.aracan.backend.utils;

import com.aracan.backend.dto.UserDTO;
import com.aracan.backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class DTOEntityConverter {

    public UserDTO userEntityToUserDTO(User userEntity) {

        UserDTO userDTO = new UserDTO();

        userDTO.set_cognitoSub(userEntity.getCognitoSub());
        userDTO.set_email(userEntity.getEmail());
        userDTO.set_firstName(userEntity.getFirstName());
        userDTO.set_lastName(userEntity.getLastName());
        userDTO.set_orders(userEntity.getOrders());
        userDTO.set_addresses(userEntity.getAddresses());
        userDTO.set_createdAt(userEntity.getCreatedAt());
        userDTO.set_updatedAt(userEntity.getUpdatedAt());

        return userDTO;

    }

    public User userDTOToUserEntity(UserDTO userDTO) {

        User userEntity = new User();

        userEntity.setCognitoSub(userDTO.get_cognitoSub());
        userEntity.setEmail(userDTO.get_email());
        userEntity.setFirstName(userDTO.get_firstName());
        userEntity.setLastName(userDTO.get_lastName());
        userEntity.setOrders(userDTO.get_orders());
        userEntity.setAddresses(userDTO.get_addresses());
        userEntity.setCreatedAt(userDTO.get_createdAt());
        userEntity.setUpdatedAt(userDTO.get_updatedAt());

        return userEntity;

    }

}
