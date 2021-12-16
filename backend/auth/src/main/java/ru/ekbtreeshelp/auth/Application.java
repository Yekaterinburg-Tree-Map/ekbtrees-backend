package ru.ekbtreeshelp.auth;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.ekbtreeshelp.core.SpringComponentScanHelper;

@OpenAPIDefinition(
        info = @Info(
                title = "EkbTrees Auth API",
                version = "1.0.0",
                contact = @Contact(
                        name = "Maxim", email = "nagovitsin.maxim@gmail.com"
                )
        ),
        servers = {
                @Server(
                        url = "https://ekb-trees-help.ru/"
                )
        }
)
@SpringBootApplication(
        scanBasePackageClasses = { SpringComponentScanHelper.class, Application.class }
)
@ConfigurationPropertiesScan(
        basePackageClasses = { SpringComponentScanHelper.class, Application.class }
)
@EntityScan(basePackageClasses = { SpringComponentScanHelper.class, Application.class })
@EnableJpaRepositories(basePackageClasses = { SpringComponentScanHelper.class, Application.class })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
