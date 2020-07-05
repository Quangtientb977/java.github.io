package com.esbt.streaming.api.user;

import com.esbt.streaming.api.user.dto.UserLogRequest;
import com.esbt.streaming.app.entity.Response;
import com.esbt.streaming.app.exception.GlobalException;
import com.esbt.streaming.data.model.User;
import com.esbt.streaming.data.model.UserLog;
import com.esbt.streaming.data.repository.UserLogRepository;
import com.esbt.streaming.data.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/user-log")
@AllArgsConstructor
public class UserLogController {

    private final UserRepository userRepository;
    private final UserLogRepository userLogRepository;

    @GetMapping("/all")
    public ResponseEntity getUserLog(@RequestParam("userId") String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new GlobalException("User does not exist"));

        return Response.data(userLogRepository.findAllByUserId(userId));
    }

    @PostMapping("/create")
    public ResponseEntity createLogs(@RequestBody UserLogRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new GlobalException("User does not exist"));

        UserLog log = new UserLog();

        log.setDirection(request.getDirection());
        log.setMode(request.getMode());
        log.setUser(user);
        log.setCreateAt(new Date());
        log.setUpdateAt(new Date());

        log = userLogRepository.save(log);

        return Response.data(log);
    }
}
