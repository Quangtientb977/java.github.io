package com.esbt.streaming.api.device;

import com.esbt.streaming.api.device.dto.NewDeviceRequest;
import com.esbt.streaming.api.device.dto.UpdateDeviceRequest;
import com.esbt.streaming.app.entity.Response;
import com.esbt.streaming.app.exception.GlobalException;
import com.esbt.streaming.app.security.CurrentUser;
import com.esbt.streaming.data.model.Device;
import com.esbt.streaming.data.model.Role;
import com.esbt.streaming.data.model.User;
import com.esbt.streaming.data.repository.DeviceRepository;
import com.esbt.streaming.data.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/device")
@AllArgsConstructor
public class DeviceController {
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity getAllDevices(@CurrentUser User user) {
        boolean isAdmin = false;

        Set<Role> roleSet = user.getRoles();
        for(Role role : roleSet) {
            if ("ROLE_ADMIN".equals(role.getCode())) isAdmin = true;
        }

        if (isAdmin) return Response.data(deviceRepository.findAll());
        else return Response.data(deviceRepository.findByUserId(user.getId()));
    }

    @PostMapping("/update")
    public ResponseEntity updateDevice(@RequestBody UpdateDeviceRequest request, @CurrentUser User user) {
            Device device = deviceRepository.findById(request.getId()).orElseThrow(() -> new GlobalException("Device does not exist"));

            device.setMode(request.getMode());
            device.setDirection(request.getDirection());

            if (!StringUtils.isEmpty(request.getUserId())) {
                Optional<User> userOpt = userRepository.findById(request.getUserId());

                if (userOpt.isPresent()) {
                    device.setUser(userOpt.get());
                } else throw new GlobalException("User does not exist");

            } else {
                device.setUser(null);
            }

            device = deviceRepository.save(device);

            return Response.data(device);
    }

    @PostMapping("/create")
    public ResponseEntity createNewDevice(@RequestBody NewDeviceRequest request, @CurrentUser User user) {
        Device device = new Device();

        device.setMode(request.getMode());
        device.setDirection(request.getDirection());

        if (!StringUtils.isEmpty(request.getUserId())) {
            Optional<User> userOpt = userRepository.findById(request.getUserId());

            if (userOpt.isPresent()) {
                device.setUser(userOpt.get());
            }

        }

        device = deviceRepository.save(device);

        return Response.data(device);
    }

    @PostMapping("/delete")
    public ResponseEntity deleteDevice(@RequestParam("deviceId") long deviceId, @CurrentUser User user) {
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new GlobalException("Device does not exist"));

        deviceRepository.delete(device);
        return Response.data(deviceId);
    }
}
