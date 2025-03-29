package com.pablodev.documentworkspace.services.authentication;

import com.pablodev.documentworkspace.dto.authentication.AuthenticationRequest;
import com.pablodev.documentworkspace.dto.authentication.AuthenticationResponse;
import com.pablodev.documentworkspace.dto.authentication.RegistrationRequest;

public interface AuthenticationService {
    void register(RegistrationRequest registrationRequest);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
