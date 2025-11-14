package com.minitb.rest;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.minitb.entity.UserEntity;
import com.minitb.repository.UserRepository;
import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.*;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UserRepository userRepository;

    @ConfigProperty(name = "jwt.duration", defaultValue = "3600")
    long tokenDuration; // en secondes

    @POST
    @Path("/login")
    public Response login(Credentials cred) {
        if (cred == null || cred.username == null || cred.password == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Missing credentials"))
                    .build();
        }

        var userOpt = userRepository.findByUsername(cred.username);
        if (userOpt.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("error", "Invalid credentials")).build();
        }

        UserEntity user = userOpt.get();

        System.out.println("DEBUG -> username=" + user.username + ", passwordHash=" + user.passwordHash);

        System.out.println("DEBUG BCrypt verify: " + BCrypt.verifyer()
                .verify("rhpass".toCharArray(), "$2a$12$kK6PQ9uU2B.Fm6p4BjXgyei1b5NqBPJ8i2p9C4FvN58sLB3lsrb0K")
                .verified);

        // Vérifcation du mot de passe avec BCrypt
        BCrypt.Result result = BCrypt.verifyer().verify(cred.password.toCharArray(), user.passwordHash);
        if (!result.verified) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("error", "Invalid credentials")).build();
        }

        try {
            // Load private key from resources
            InputStream is = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("privateKey.pem");

            if (is == null) {
                throw new IllegalStateException("privateKey.pem introuvable dans le classpath !");
            }

            String pem = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            pem = pem.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            byte[] decoded = Base64.getDecoder().decode(pem);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);

            // Build JWT
            String token = Jwt.issuer("mini-tb")
                    .upn(cred.username)
                    .claim("groups", user.roles)
                    .expiresAt(Instant.now().plusSeconds(tokenDuration))
                    .sign(privateKey);

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("token", token);
            resultMap.put("username", cred.username);
            resultMap.put("roles", user.roles);
            return Response.ok(resultMap).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Token generation failed: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/hello")
    @RolesAllowed({"RH"})
    public Response hello() {
        return Response.ok(Map.of("message", "Bienvenue RH, accès autorisé ✅")).build();
    }

    // --- DTO interne ----
    public static class Credentials {
        public String username;
        public String password;
    }

}
