package hcmute.vn.springonetomany.Config;

import hcmute.vn.springonetomany.Custom.CustomAuthenticationSuccessHandler;
import hcmute.vn.springonetomany.Custom.CustomUserDetailService;
import hcmute.vn.springonetomany.Custom.Oauth2.CustomOAuth2UserService;
import hcmute.vn.springonetomany.Custom.Oauth2.OAuthLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.HeaderWriter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
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

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/lib/**", "/mail/**", "/scss/**", "/product_photos/**", "/product_images/**", "/category_photos/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/home", "/products", "/login", "/logout", "/register", "/error", "/process_register").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/process_login")
                .successHandler(new CustomAuthenticationSuccessHandler())
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .oauth2Login().loginPage("/login").userInfoEndpoint().userService(oauth2UserService).and().successHandler(oauthLoginSuccessHandler).and()
                .logout().logoutSuccessUrl("/?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
        ;
    }

    @Bean
    public FilterRegistrationBean<XContentTypeOptionsHeaderFilter> xContentTypeOptionsHeaderFilter() {
        XContentTypeOptionsHeaderFilter filter = new XContentTypeOptionsHeaderFilter();

        FilterRegistrationBean<XContentTypeOptionsHeaderFilter> registration = new FilterRegistrationBean<>(filter);

        registration.addUrlPatterns("/*"); // Add the URL patterns you want to apply the filter

        return registration;
    }

    @Autowired
    private CustomOAuth2UserService oauth2UserService;

    @Autowired
    private OAuthLoginSuccessHandler oauthLoginSuccessHandler;
}
