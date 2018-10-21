package ro.ubb.test1.core.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"ro.ubb.test1.core.service","ro.ubb.blooddonations.core.controller","ro.ubb.blooddonations.core.utils",
        "ro.ubb.blooddonations.core.repository",})
public class CatalogConfig {
}
