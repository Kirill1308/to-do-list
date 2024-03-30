package com.popov.tasklist.service;

import com.popov.tasklist.web.dto.auth.JwtRequest;
import com.popov.tasklist.web.dto.auth.JwtResponse;

public interface AuthService {
    JwtRequest login(JwtRequest loginRequest);
    JwtResponse refresh(String refreshToken);
}
