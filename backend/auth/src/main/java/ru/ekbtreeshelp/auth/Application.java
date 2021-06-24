package ru.ekbtreeshelp.auth;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

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
@SpringBootApplication
@ConfigurationPropertiesScan("ru.ekbtreeshelp.auth.config")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
