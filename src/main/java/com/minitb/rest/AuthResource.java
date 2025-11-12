package com.minitb.rest;

import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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

    // Prototype : user store in-memory
    private static final Map<String, User> USERS = new HashMap<>();

    static {
        USERS.put("rh", new User("rh", "rhpass", List.of("RH")));
        USERS.put("dir", new User("dir", "dirpass", List.of("DIRECTION")));
        USERS.put("consult", new User("consult", "consultpass", List.of("CONSULTANT")));
        USERS.put("admin", new User("admin", "admin", Arrays.asList("RH", "DIRECTION")));
    }

    @POST
    @Path("/login")
    public Response login(Credentials cred) {
        if (cred == null || cred.username == null || cred.password == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Missing credentials"))
                    .build();
        }

        User u = USERS.get(cred.username);
        if (u == null || !u.password.equals(cred.password)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("error", "Invalid credentials"))
                    .build();
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
                    .claim("groups", u.roles)
                    .expiresAt(Instant.now().plusSeconds(3600))
                    .sign(privateKey);

            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("username", cred.username);
            result.put("roles", u.roles);
            return Response.ok(result).build();

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

    public static class Credentials {
        public String username;
        public String password;
    }

    private static class User {
        public String username;
        public String password;
        public List<String> roles;
        public User(String u, String p, List<String> r) {
            username = u; password = p; roles = r;
        }
    }
}
