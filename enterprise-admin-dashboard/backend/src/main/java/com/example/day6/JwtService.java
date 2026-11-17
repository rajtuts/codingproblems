package com.example.day6;
import io.jsonwebtoken.*; import io.jsonwebtoken.security.Keys; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service; import javax.crypto.SecretKey; import java.nio.charset.StandardCharsets; import java.util.Date;
@Service public class JwtService {
 private final SecretKey key; private final long ttl;
 public JwtService(@Value("${app.jwt.secret}") String s,@Value("${app.jwt.expiration-ms}") long t){key=Keys.hmacShaKeyFor(s.getBytes(StandardCharsets.UTF_8));ttl=t;}
 public String create(String user,String role){Date now=new Date();return Jwts.builder().subject(user).claim("role",role).issuedAt(now).expiration(new Date(now.getTime()+ttl)).signWith(key).compact();}
 public Claims parse(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();}
}
