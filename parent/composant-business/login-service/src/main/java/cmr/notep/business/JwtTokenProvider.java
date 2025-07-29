package cmr.notep.business;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
@Transactional
public class JwtTokenProvider {

    private String jwtSecret="593ad05ff55b277334172603d01203aacd5528e6f19c752e8cb7c4c6f449697a";


    private long jwtExpirationDate=604800000;

    // generate JWT token
    public String generateToken(Authentication authentication){

        String username = authentication.getName();

        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(key())
                .compact();

        return token;
    }

//    private Key key(){
//        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
//    }

    private Key key() {
        try {
            byte[] keyBytes = Hex.decodeHex(jwtSecret.toCharArray());
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (DecoderException e) {
            throw new RuntimeException("Failed to decode jwtSecret hex string", e);
        }
    }

    // get username from JWT token
    public String getUsername(String token){

        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // validate JWT token
    public boolean validateToken(String token){
        Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parse(token);
        return true;

    }

}
