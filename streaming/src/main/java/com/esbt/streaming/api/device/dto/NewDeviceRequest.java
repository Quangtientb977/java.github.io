package com.esbt.streaming.api.device.dto;

import lombok.Data;

@Data
public class NewDeviceRequest {
    private int direction;

    private int mode;

    private String userId;
}
