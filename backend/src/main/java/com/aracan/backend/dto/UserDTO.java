package com.aracan.backend.dto;

import com.aracan.backend.entity.Address;
import com.aracan.backend.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private String _cognitoSub;
    private String _email;
    private String _firstName;
    private String _lastName;
    private Set<Order> _orders = new HashSet<>();
    private Set<Address> _addresses = new HashSet<>();
    private LocalDateTime _createdAt;
    private LocalDateTime _updatedAt;

}
