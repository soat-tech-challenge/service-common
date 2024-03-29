package br.com.grupo63.techchallenge.common.config.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

@Service
public class JwtService {
    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    private final JwtParser parser;

    public JwtService(@Value("${app.auth.jwt_public_key}") String jwtPublicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        log.info("Initializing JWT Service\nPublic key: {}", jwtPublicKey);
        parser = Jwts.parser().verifyWith(getVerifyingKey(jwtPublicKey)).build();
    }

    public Claims getClaims(String token) {
        return parser.parseSignedClaims(token).getPayload();
    }

    private PublicKey getVerifyingKey(String jwtPublicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Decoders.BASE64.decode(jwtPublicKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }
}
