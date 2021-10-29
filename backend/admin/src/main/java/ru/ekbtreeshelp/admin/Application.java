package ru.ekbtreeshelp.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.ekbtreeshelp.core.SpringComponentScanHelper;

@SpringBootApplication(
        scanBasePackageClasses = { SpringComponentScanHelper.class, Application.class }
)
@EntityScan(basePackageClasses = { SpringComponentScanHelper.class, Application.class })
@EnableJpaRepositories(basePackageClasses = { SpringComponentScanHelper.class, Application.class })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
