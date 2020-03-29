package com.example.liquibasetest;

import org.omg.SendingContext.RunTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@SpringBootApplication
public class LiquibasetestApplication implements CommandLineRunner {

    private static final String ALGORITHM = "HmacSHA256";

    public static void main(String[] args) {
        SpringApplication.run(LiquibasetestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IlRFU1RfSUQiLCJoaWQiOiJURVNUX0hJRCIsImlhdCI6MTU4NTE0MTY5Nzc5Mn0.G4DjJg29t8iyWDpfA7PWfAQyMVHtNYSZnU74VpOImrA";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IlRFU1RfSUQiLCJoaWQiOiJURVNUX0hJRCIsImlhdCI6MTU4NTE0MzgxMTM1N30.430KGkjkHK2jborWHdDkpi-Do_G31-CPeuQUKifKX1s";
        String[] tokenParts = token.split("\\.");

        if (tokenParts.length < 3) {
            throw new RuntimeException("Invalid token. Some parts of token is missing");
        }

        String tokenHeader = tokenParts[0];
        String tokenPayload = tokenParts[1];
        String tokenSignature = tokenParts[2];
        boolean signatureVerified = isSignatureVerified(tokenHeader, tokenPayload, tokenSignature);
        System.out.println(signatureVerified);
    }

    private boolean isSignatureVerified(String tokenHeader, String tokenPayload, String tokenSignature)
            throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        SecretKeySpec secret = new SecretKeySpec("QWERTY".getBytes(), ALGORITHM);
        Mac mac = Mac.getInstance(ALGORITHM);
        mac.init(secret);

        byte[] signatureBytes = mac.doFinal((tokenHeader + "." + tokenPayload).getBytes(StandardCharsets.UTF_8.name()));
        String calculatedSignature = Base64.getUrlEncoder().withoutPadding().encodeToString(signatureBytes);

        return calculatedSignature.equalsIgnoreCase(tokenSignature);
    }
}
