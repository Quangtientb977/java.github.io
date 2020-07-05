package com.esbt.streaming.data.repository;

import com.esbt.streaming.data.model.DeviceLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceLogRepository extends JpaRepository<DeviceLog, Long> {

    Iterable<DeviceLog> findAllByDeviceId(Long deviceId);
}
