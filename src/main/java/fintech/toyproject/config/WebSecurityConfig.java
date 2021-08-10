package fintech.toyproject.config;

import fintech.toyproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final MemberService memberService;

    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/css/**", "/js/**", "img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                    .antMatchers("/login", "/signup", "/user").permitAll()
                    .antMatchers("/main").permitAll()
                    .antMatchers("/admin").hasRole("admin")
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .defaultSuccessUrl("/main")
                .and()
                    .logout()
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .userDetailsService(memberService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
