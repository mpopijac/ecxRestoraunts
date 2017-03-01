package ecx.mpopijac.restaurants.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
    private UserDetailsService userDetailsService;
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
		
	@Override
	protected void configure(HttpSecurity http)throws Exception{
		http
			.authorizeRequests()
				.antMatchers("/","/index","/css/*","/js/*","/upload/*","/published-article","/logout").permitAll()
				.antMatchers("/admin/crud-role").hasRole("ADMINISTRATOR")
				.anyRequest().authenticated()
				.and()
				.exceptionHandling().accessDeniedPage("/")
				.and()
			.formLogin()
				.loginPage("/login")
				.failureUrl("/login-error")
				.permitAll()
				.and()
			.logout()
				.logoutSuccessUrl("/index")
				.permitAll();
	}
	
	@Autowired
	public void configureGlobal (AuthenticationManagerBuilder auth)throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
}
