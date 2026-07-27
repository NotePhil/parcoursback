package cmr.notep;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "cmr.notep")
@EntityScan(basePackages = "cmr.notep")
public class ExemplaireApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExemplaireApplication.class, args);
    }
}
