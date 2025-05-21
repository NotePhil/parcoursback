package cmr.notep;

import cmr.notep.token.JWTService;
import io.jsonwebtoken.Jwts;
import org.h2.value.DataType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

@SpringBootApplication(scanBasePackages = "cmr.notep")
@EntityScan(basePackages = "cmr.notep")
public class LoginApplication {
//    private static JWTService jwtservice = new JWTService();


    public static void main(String[] args) {

//        SecretKey key = Jwts.SIG.HS512.key().build();
//        String encodedKey = DatatypeConverter.printHexBinary(key.getEncoded());
//        System.out.println("key: "+encodedKey);

        SpringApplication.run(LoginApplication.class, args);
    }
}
