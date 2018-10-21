package ro.ubb.springdi.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by radu.
 */
@Configuration
@ComponentScan({"ro.ubb.springdi.repository", "ro.ubb.springdi.service", "ro.ubb.springdi.ui"})
public class CatalogConfig {
}
