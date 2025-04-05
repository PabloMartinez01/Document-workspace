package com.pablodev.documentworkspace.services.authentication;

import com.pablodev.documentworkspace.dto.authentication.AuthenticationRequest;
import com.pablodev.documentworkspace.dto.authentication.AuthenticationResponse;
import com.pablodev.documentworkspace.dto.authentication.RegistrationRequest;
import com.pablodev.documentworkspace.model.User;
import com.pablodev.documentworkspace.repositories.UserRepository;
import com.pablodev.documentworkspace.services.jwt.ApplicationAbstractJwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultAuthenticationService implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ApplicationAbstractJwtService jwtService;

    @Override
    public void register(RegistrationRequest registrationRequest) {
        User user = User.builder()
                .username(registrationRequest.getUsername())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .build();
        userRepository.save(user);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
        );

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        String token = jwtService.createToken((User) authentication.getPrincipal());

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }


}
