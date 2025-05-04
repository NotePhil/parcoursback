package cmr.notep.JwtSecretMaker;

import io.jsonwebtoken.security.Keys;
import org.junit.Test;

import javax.crypto.SecretKey;

public class JwtSecretMaker {

    @Test
    public void genetateSecretKey()
    {
        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);
        System.out.println("Clé secrete generée :"+ java.util.Base64.getEncoder().encodeToString(key.getEncoded()));
    }

}
