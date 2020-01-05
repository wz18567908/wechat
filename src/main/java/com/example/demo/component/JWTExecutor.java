package com.example.demo.component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.User;
import com.example.demo.exception.AuthorizationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTExecutor {

    public static final String JWT_ID = "ctcloud_jwt";

    @Autowired
    private DemoConfiguration demoConfiguration;

    @Autowired
    private RSAGeneral rsaGeneral;

    public String createJWT(String subject) throws AuthorizationException {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;
        long currentTimeMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentTimeMillis);
        long expirationTimeMillis = currentTimeMillis + demoConfiguration.getJWTValidTimeLength() * 60 * 1000;
        Date expirationDate = new Date(expirationTimeMillis);

        PrivateKey privateKey = rsaGeneral.getPrivateKey();
        JwtBuilder builder = Jwts.builder()
            .setId(JWT_ID)
            .setNotBefore(currentDate)
            .setSubject(subject)
            .setExpiration(expirationDate)
            .signWith(signatureAlgorithm, privateKey);
        return builder.compact();
    }

    public Claims parseJWT(String jwt) throws AuthorizationException {
        PublicKey publicKey = rsaGeneral.getPublicKey();
        Claims claims = Jwts.parser()
           .setSigningKey(publicKey)
           .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public String generalSubject(User user) {
        JSONObject json = new JSONObject();
        json.put("userName", user.getUserName());
        json.put("tokenStartTime", user.getTokenStartTime());
        return json.toJSONString();
    }

    public long geJWTCheckTimeMillis() {
        return demoConfiguration.getJWTCheckTimeLength() * 60 * 1000;
    }
}

