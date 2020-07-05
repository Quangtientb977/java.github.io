package com.esbt.streaming.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "device")
@Getter
@Setter
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int mode;

    private boolean actived;

    private int direction;

    @JsonIgnore
    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY)
    private Set<DeviceLog> deviceLogs = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
