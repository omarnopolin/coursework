package coursework.cashflow.configuration;//package world.ucode.cashflow.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//import java.security.AuthProvider;
//
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .cors().disable()
//                .csrf().disable();
//        http.authorizeRequests().antMatchers("/", "/main", "/authorization").permitAll();
//        http.authorizeRequests().antMatchers("/profile/**").hasAnyAuthority("BIDDER", "SELLER");
//        http.authorizeRequests().antMatchers("/addLot/**").hasAnyAuthority("SELLER");
//        http.authorizeRequests().antMatchers("/addFeedback/**").hasAnyAuthority("BIDDER");
//        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/accessDenied").accessDeniedHandler(new AccessDeniedExceptions());
//        http.authorizeRequests().and().formLogin()//
//                // Submit URL of login page.
//                .loginProcessingUrl("/authorization").permitAll()
//                .loginPage("/authorization")
//                .failureUrl("/accessDenied")
//                .defaultSuccessUrl("/main")
//                .usernameParameter("login")
//                .passwordParameter("password");
//        http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                // Config for Logout Page
//                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/main");
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    };
////    @Autowired
////    public void configureGlobal(AuthenticationManagerBuilder auth) {
////        auth.authenticationProvider(new AuthProvider());
////    }
//@Bean
//@Override
//public UserDetailsService userDetailsService() {
//    UserDetails user =
//            User.withDefaultPasswordEncoder()
//                    .username("user")
//                    .password("password")
////                    .roles("USER")
////                    .build();
////
////    return new InMemoryUserDetailsManager(user);
////}
////}
import coursework.cashflow.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// TODO закрыть все

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable();
        http.authorizeRequests().antMatchers("/login", "/sign_up").anonymous();
        http.authorizeRequests().antMatchers("/api/**").authenticated();
        http.authorizeRequests().antMatchers("/",
                                                "/exportCSV/**",
                                                "/wallets/**",
                                                "/transactions/**",
                                                "/categories/**",
                                                "/statistics/**",
                                                "/swagger-ui/**"
                                            ).authenticated();
        http.addFilterBefore(new JsonAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        http    .authorizeRequests()

                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .failureHandler(new AuthExceptions());
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                // Config for Logout Page
                .and().logout().logoutUrl("/logout").logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")).logoutSuccessUrl("/");
    }
    @Bean
    public org.springframework.security.core.userdetails.UserDetailsService userDetailsService() {
        return new UserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
    @Autowired
    private DaoAuthenticationProvider authenticationProvider;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
}