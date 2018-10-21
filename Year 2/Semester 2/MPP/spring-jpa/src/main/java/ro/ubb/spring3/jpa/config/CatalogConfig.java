package ro.ubb.spring3.jpa.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"ro.ubb.spring3.jpa.repository", "ro.ubb.spring3.jpa.service", "ro.ubb.spring3.jpa.ui"})
public class CatalogConfig {


}
