package com.rabianesrine.testspringboot.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabianesrine.testspringboot.Config.JwtService;
import com.rabianesrine.testspringboot.entities.Client;
import com.rabianesrine.testspringboot.repositories.ClientRepository;
import com.rabianesrine.testspringboot.token.Token;
import com.rabianesrine.testspringboot.token.TokenRepository;
import com.rabianesrine.testspringboot.token.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final ClientRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        var client = Client.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .motsDePasse(encoder.encode(request.getMotsDePasse()))
                .role(request.getRole())
                .build();
        var savedClient  = repository.save(client);
        var jwtToken = jwtService.generateToken(client);
        var refreshToken = jwtService.generateRefreshToken(client);

        // revokeAllUserToken(user);
        savedClientToken(savedClient, jwtToken);

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .prenom(savedClient.getPrenom())
                .nom(savedClient.getNom())
                .dateInscription(new Date(System.currentTimeMillis()))
                .build();
    }

    private void revokeAllClientToken(Client client){
        var validClientToken = tokenRepository.findAllValidTokensByUser(client.getId());
        if(validClientToken.isEmpty())
        {
            return;
        }

        validClientToken.forEach(t ->{
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validClientToken);
    }

    private void savedClientToken(Client client, String jwtToken) {
        var token = Token.builder()
                .client(client)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getMotDePasse()
                )
        );

        var client = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(client);
        var refreshToken = jwtService.generateRefreshToken(client);

        revokeAllClientToken(client);
        savedClientToken(client, jwtToken);

        return  AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader= request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String clientEmail;


        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        clientEmail = jwtService.extractUserName(refreshToken);
        if(clientEmail!= null){
            var  client = this.repository.findByEmail(clientEmail)
                    .orElseThrow();

            if (jwtService.isTokenValid(refreshToken , client )){
                var accessToken = jwtService.generateToken(client);
                revokeAllClientToken(client);
                savedClientToken(client, accessToken);
                var authResponse = AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
