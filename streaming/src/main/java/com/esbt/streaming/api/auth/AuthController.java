package com.esbt.streaming.api.auth;

import com.esbt.streaming.api.auth.dto.LoginRequest;
import com.esbt.streaming.app.entity.Response;
import com.esbt.streaming.app.exception.GlobalException;
import com.esbt.streaming.app.security.CurrentUser;
import com.esbt.streaming.app.security.JwtAuthenticationResponse;
import com.esbt.streaming.app.security.JwtTokenProvider;
import com.esbt.streaming.common.UserError;
import com.esbt.streaming.data.model.User;
import com.esbt.streaming.service.UserValidation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final UserValidation userValidation;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @GetMapping("/me")
    public ResponseEntity authenticateUser(@CurrentUser User user) {
        return Response.data(user);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest request) {
        String error = userValidation.validateInputRequest(request);

        if (StringUtils.isEmpty(error)) {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = tokenProvider.generateToken(authentication);
                return Response.data(new JwtAuthenticationResponse(jwt, authentication.getPrincipal()));
            } catch (BadCredentialsException e) {
                throw new GlobalException(UserError.INVALID_USER);
            }
        } else {
            throw new GlobalException(error);
        }
    }
}
