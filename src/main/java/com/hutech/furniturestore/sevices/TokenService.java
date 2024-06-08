package com.hutech.furniturestore.sevices;

import com.hutech.furniturestore.models.Token;
import com.hutech.furniturestore.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    public Token saveToken(Token token) {
        return tokenRepository.save(token);
    }

    public Token getTokenById(Long id) {
        return tokenRepository.findById(id).orElse(null);
    }

    public List<Token> getAllTokens() {
        return tokenRepository.findAll();
    }

    public void deleteToken(Long id) {
        tokenRepository.deleteById(id);
    }
}
