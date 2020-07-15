package com.dusan.webshop.api.controller;

import com.dusan.webshop.api.docs.Descriptions;
import com.dusan.webshop.dto.request.AuthenticationRequest;
import com.dusan.webshop.dto.response.AuthenticationResponse;
import com.dusan.webshop.security.JWTUtils;
import com.dusan.webshop.security.TokenWrapper;
import com.dusan.webshop.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Tag(name = "Authentication")
@AllArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private JWTUtils jwtUtils;

    @Operation(summary = "Authenticate a user", description = Descriptions.AUTHENTICATE,
                responses = {@ApiResponse(responseCode = "200", description = "successful authentication"),
                             @ApiResponse(responseCode = "403", description = "bad credentials", content = @Content)})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthenticationResponse authenticate(@Valid @RequestBody AuthenticationRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = authenticationManager.authenticate(authToken);
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        TokenWrapper token = getToken(userDetails);
        return createResponse(token);
    }

    private TokenWrapper getToken(UserDetailsImpl userDetails) {
        long userId = userDetails.getUserId();
        String role = userDetails.getAuthorities().stream()
                .map(Object::toString)
                .collect(Collectors.joining());
        return jwtUtils.createToken(userId, role);
    }

    private AuthenticationResponse createResponse(TokenWrapper tokenWrapper) {
        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken("Bearer " + tokenWrapper.getToken());
        response.setExpiration(tokenWrapper.getExpire());
        return response;
    }
}
