package christel.mvele.nutrisportBackend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*"); // Autoriser toutes les origines (à ajuster en fonction de vos besoins)
        config.addAllowedMethod("*"); // Autoriser toutes les méthodes HTTP
        config.addAllowedHeader("*"); // Autoriser tous les en-têtes HTTP
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
