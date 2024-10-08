package com.utp.springboot.backend.apirest.monitor.auth;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //.antMatchers(HttpMethod.GET, "/api/epps", "/api/epps/page/**").permitAll()
                //.antMatchers("/api/empleados/**").permitAll()
                //.antMatchers("/api/registros/**").permitAll()
                //.antMatchers(HttpMethod.GET, "/api/categorias","/api/categorias/page/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/empleados/ver/**","/api/empleados/page/**").hasAnyRole("USER", "ADMIN")
                //    empleados/ver/33 ("USER", "ADMIN")
                //    SUBIR IMAGEN DE SU PERFIL ("USER", "ADMIN")
                //    registros/ver/55 ("USER", "ADMIN")
                /*.antMatchers(HttpMethod.GET, "/api/empleados/{id}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/empleados").hasRole("ADMIN")
                .antMatchers("/api/empleados/**").hasRole("ADMIN")
                .antMatchers("/api/empleados/**").hasRole("ADMIN")*/
                //.anyRequest().authenticated()
                .and().cors().configurationSource(corsConfigurationSource());
        //.anyRequest().authenticated();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
