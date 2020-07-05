package com.esbt.streaming.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_log")
@Getter
@Setter
public class UserLog extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int direction;

    private int mode;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}
