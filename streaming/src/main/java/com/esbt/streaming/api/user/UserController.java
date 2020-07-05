package com.esbt.streaming.api.user;

import com.esbt.streaming.api.user.dto.UserRequest;
import com.esbt.streaming.app.entity.Response;
import com.esbt.streaming.app.exception.GlobalException;
import com.esbt.streaming.data.model.Role;
import com.esbt.streaming.data.model.User;
import com.esbt.streaming.data.repository.RoleRepository;
import com.esbt.streaming.data.repository.UserRepository;
import com.esbt.streaming.service.UserValidation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserValidation userValidation;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @PostMapping("/create")
    public ResponseEntity createNewUser(@RequestBody UserRequest request) {

        String error = userValidation.validateNewUserRequest(request);

        if (StringUtils.isEmpty(error)) {
            User user = new User();

            user.setId(UUID.randomUUID().toString());

            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setCreateAt(new Date());
            user.setUpdateAt(new Date());

            Optional<Role> roleOpt = roleRepository.findByCode("ROLE_USER");

            if (roleOpt.isPresent()) {
                user.getRoles().add(roleOpt.get());
            } else {
                Role role  = new Role();
                role.setCode("ROLE_USER");

                role = roleRepository.save(role);

                user.getRoles().add(role);
            }

            user = userRepository.save(user);

            return Response.data(user);
        } else {
            throw new GlobalException(error);
        }
    }

    @PostMapping("/update")
    public ResponseEntity updateUser(@RequestBody UserRequest request) {


            User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new GlobalException("User không tồn tại"));

            if (!StringUtils.isEmpty(request.getPassword())) {
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }
            user.setEmail(request.getEmail());
            user.setCreateAt(new Date());

            user = userRepository.save(user);

            return Response.data(user);

    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity getAllUsers() {
        return Response.data(userRepository.findAll());
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity deleteUser(@RequestParam("id") String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new GlobalException("User does not exist"));

        userRepository.delete(user);
        return Response.data("deleted");
    }
}
