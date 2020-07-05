package com.esbt.streaming.data.repository;

import com.esbt.streaming.data.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Iterable<Device> findByUserId(String userId);
}
