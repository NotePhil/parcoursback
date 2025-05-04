package cmr.notep.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class JWTService {

    private static final String SECRET = "FCB69C9A7D656D0A7CDF14C6D85315B26293BAA3C78FEC925BC1C5EDF7508D30CD033F8D9C6A37447610C402F5FBFD24608B0A7254F1E00B560391FA79BB678F";
    private static final long VALIDITY = TimeUnit.MINUTES.toMillis(30);

    public String generateToken(UserDetails userDetails)
    {
        Map<String , String> claims = new HashMap<>() ;
        claims.put("iss" , "http://localhost:8083/auth/realms/baeldung") ;
        claims.put("scope" , "profile write read") ;

        String compact = Jwts.builder().claims(claims).subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now())).expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateKey()).compact();
        return compact;
    }

    public SecretKey generateKey()
    {
        byte[] decodedkey = Base64.getDecoder().decode(SECRET) ;
        return Keys.hmacShaKeyFor(decodedkey);
    }

    public String extractUsername(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getSubject();
    }

    private Claims getClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public boolean isTokenValid(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getExpiration().after(Date.from(Instant.now()));
    }
}
