package com.aracan.backend.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {

    PAID,
    SHIPPED,
    DELIVERED,
    CANCELLED;

}
