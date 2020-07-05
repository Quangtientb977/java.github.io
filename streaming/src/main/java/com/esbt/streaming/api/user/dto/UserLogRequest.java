package com.esbt.streaming.api.user.dto;

import lombok.Data;

@Data
public class UserLogRequest {
    private String userId;

    private int direction;

    private int mode;
}
