package com.pablodev.documentworkspace.controllers;

import com.pablodev.documentworkspace.dto.authentication.AuthenticationRequest;
import com.pablodev.documentworkspace.dto.authentication.AuthenticationResponse;
import com.pablodev.documentworkspace.dto.authentication.RegistrationRequest;
import com.pablodev.documentworkspace.services.authentication.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        this.authenticationService.register(registrationRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);
        return ResponseEntity.ok().body(authenticationResponse);
    }

}
