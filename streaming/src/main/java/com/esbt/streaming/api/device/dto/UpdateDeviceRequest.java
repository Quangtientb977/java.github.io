package com.esbt.streaming.api.device.dto;

import lombok.Data;

@Data
public class UpdateDeviceRequest {
    private long id;

    private int direction;

    private int mode;

    private String userId;
}
