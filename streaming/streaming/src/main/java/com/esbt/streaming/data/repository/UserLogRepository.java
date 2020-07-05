package com.esbt.streaming.data.repository;

import com.esbt.streaming.data.model.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {

    Iterable<UserLog> findAllByUserId(String userId);
}
