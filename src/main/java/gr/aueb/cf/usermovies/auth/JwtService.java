package gr.aueb.cf.usermovies.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;

@ApplicationScoped
@Provider
public class JwtService {

    @Inject
    private IKeyGenerator keyGenerator;


    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(keyGenerator.generateKey()).build().parseClaimsJws(token).getBody();
    }

    public String createToken(String username, String issuer){
        Key key = keyGenerator.generateKey();
        return Jwts.builder()
                .setSubject(username)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(afterMinutes(15L))
                .signWith(key)
                .compact();
    }

    private Date afterMinutes(long minutes) {
        return Date.from(LocalDateTime.now().plusMinutes(minutes).atZone(ZoneId.systemDefault()).toInstant());
    }
}
