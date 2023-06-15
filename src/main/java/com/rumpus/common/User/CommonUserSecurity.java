package com.rumpus.common.User;
// package com.rumpus.common;

// import org.springframework.context.annotation.Bean;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

// public class CommonUserSecurity extends WebSecurityConfigurerAdapter {
    
//     // @Autowired
//     // private AccountService accountService;

//     @Bean
//     public TokenBasedRememberMeServices rememberMeServices() {
//         return new TokenBasedRememberMeServices("remember-me-key", accountService);
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Override
//     protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//         auth
//             .eraseCredentials(true)
//             .userDetailsService(accountService)
//             .passwordEncoder(passwordEncoder());
//     }

//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http
//             .authorizeRequests()
//                 .antMatchers("/", "/favicon.ico", "/resources/**", "/signup").permitAll()
//                 .anyRequest().authenticated()
//                 .and()
//             .formLogin()
//                 .loginPage("/signin")
//                 .permitAll()
//                 .failureUrl("/signin?error=1")
//                 .loginProcessingUrl("/authenticate")
//                 .and()
//             .logout()
//                 .logoutUrl("/logout")
//                 .permitAll()
//                 .logoutSuccessUrl("/signin?logout")
//                 .and()
//             .rememberMe()
//                 .rememberMeServices(rememberMeServices())
//                 .key("remember-me-key");
//     }

//     @Bean(name = "authenticationManager")
//     @Override
//     public AuthenticationManager authenticationManagerBean() throws Exception {
//         return super.authenticationManagerBean();
//     }
// }
