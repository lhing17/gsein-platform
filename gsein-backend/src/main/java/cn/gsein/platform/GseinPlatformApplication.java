package cn.gsein.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GseinPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(GseinPlatformApplication.class, args);
    }

}
