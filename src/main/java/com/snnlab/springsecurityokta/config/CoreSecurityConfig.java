package com.snnlab.springsecurityokta.config;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class CoreSecurityConfig extends WebSecurityConfigurerAdapter {

        // If you need , to be added CORS Origin Configuration, I prefer api-gateway.
}
