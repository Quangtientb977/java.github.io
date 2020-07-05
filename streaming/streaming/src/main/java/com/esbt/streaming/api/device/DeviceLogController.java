package com.esbt.streaming.api.device;

import com.esbt.streaming.api.device.dto.DeviceLogRequest;
import com.esbt.streaming.app.entity.Response;
import com.esbt.streaming.app.exception.GlobalException;
import com.esbt.streaming.data.model.Device;
import com.esbt.streaming.data.model.DeviceLog;
import com.esbt.streaming.data.repository.DeviceLogRepository;
import com.esbt.streaming.data.repository.DeviceRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/device-log")
@AllArgsConstructor
public class DeviceLogController {
    private final DeviceRepository deviceRepository;
    private final DeviceLogRepository deviceLogRepository;

    @GetMapping("/all")
    public ResponseEntity getUserLog(@RequestParam("deviceId") Long deviceId) {
        Device divce = deviceRepository.findById(deviceId).orElseThrow(() -> new GlobalException("Device does not exist"));

        return Response.data(deviceLogRepository.findAllByDeviceId(deviceId));
    }

    @PostMapping("/create")
    public ResponseEntity createLogs(@RequestBody DeviceLogRequest request) {
        Device device = deviceRepository.findById(request.getDeviceId()).orElseThrow(() -> new GlobalException("Device does not exist"));

        DeviceLog log = new DeviceLog();

        log.setDirection(request.getDirection());
        log.setSpeed(request.getSpeed());
        log.setCreateAt(new Date());
        log.setUpdateAt(new Date());

        log.setDevice(device);
        log = deviceLogRepository.save(log);

        return Response.data(log);
    }
}
