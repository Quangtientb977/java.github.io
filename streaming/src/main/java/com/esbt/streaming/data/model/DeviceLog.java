package com.esbt.streaming.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "device_log")
@Getter
@Setter
public class DeviceLog extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int direction;

    private int speed;

    @ManyToOne
    @JoinColumn(name="device_id", nullable=false)
    private Device device;
}
