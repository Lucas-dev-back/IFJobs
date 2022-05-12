package br.edu.ifrn.ifjobs.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class GeradorTokenService {

    private static String SECRET_KEY_JWT = "SEnHaDiFIciL1";

    public static String geraToken(String email, int tempoExpiracao) {
        final String JWT = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + tempoExpiracao))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY_JWT).compact();

        return "Bearer " + JWT;
    }

}
