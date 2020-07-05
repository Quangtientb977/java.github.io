package com.esbt.streaming.service;

import com.esbt.streaming.api.auth.dto.LoginRequest;
import com.esbt.streaming.api.user.dto.UserRequest;
import com.esbt.streaming.common.UserError;
import com.esbt.streaming.common.StringCommon;
import com.esbt.streaming.data.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;

@Service
@AllArgsConstructor
public class UserValidation {
    private final UserRepository userRepository;

    public String validateInputRequest(LoginRequest request) {
        if (StringUtils.isEmpty(request.getUsername())) {
            return UserError.EMPTY_USERNAME;
        } else if (StringUtils.isEmpty(request.getPassword())) {
            return UserError.EMPTY_PASSWORD;
        } else if (!userRepository.findByUsername(request.getUsername()).isPresent()) {
            return UserError.INVALID_USER;
        }

        return StringCommon.EMPTY_STRING;
    }

    public String validateNewUserRequest(UserRequest request) {
        if (StringUtils.isEmpty(request.getUsername())) {
            return UserError.EMPTY_USERNAME;
        } else if (!validateUserRegex(request.getUsername())) {
            return UserError.INVALID_USERNAME;
        } else if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return UserError.EXISTED_USERNAME;
        } else if (StringUtils.isEmpty(request.getPassword())) {
            return UserError.EMPTY_PASSWORD;
        } else if (StringUtils.isEmpty(request.getEmail())) {
            return UserError.EMPTY_EMAIL;
        } else if (!validateEmailRegex(request.getEmail())) {
            return UserError.INVALID_EMAIL;
        } else if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return UserError.EXISTED_EMAIL;
        }

        return StringCommon.EMPTY_STRING;
    }

    private boolean validateEmailRegex(String emailStr) {
        Matcher matcher = StringCommon.VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    private boolean validateUserRegex(String username) {
        Matcher matcher = StringCommon.VALID_USERNAME_REGEX.matcher(username);
        return matcher.find();
    }
}
