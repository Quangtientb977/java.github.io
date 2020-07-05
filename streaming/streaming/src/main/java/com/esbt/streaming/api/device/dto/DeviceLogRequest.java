package com.esbt.streaming.api.device.dto;

import lombok.Data;

@Data
public class DeviceLogRequest {
    private Long deviceId;

    private int direction;

    private int speed;
}
