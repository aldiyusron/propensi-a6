package mosing;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//			.authorizeRequests()
//				.antMatchers("/", "/home").permitAll()
//				.antMatchers("/helloAdmin").hasRole("ADMIN").anyRequest().authenticated()
//				//.anyRequest().authenticated()
//				.and()
//			.formLogin()
//				.loginPage("/login").permitAll()
//				.and()
//			.logout()
//				.permitAll();
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/index").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/img/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/fonts/**").permitAll()
				.antMatchers("/font-awesome/**").permitAll()
				.antMatchers("/home").hasRole("PEN").anyRequest().authenticated()
				.antMatchers("/kpmb").hasRole("KPM").anyRequest().authenticated()
				
				.anyRequest().authenticated().and().formLogin()
				.loginPage("/login").permitAll().and().logout().permitAll();
	}
	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//			.inMemoryAuthentication()
//				.withUser("KPMB1").password("password1").roles("KPM")
//				.and()
//				.withUser("admin").password("admin123").roles("ADMIN");
//	}
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				//.passwordEncoder(passwordEncoder())
				.usersByUsernameQuery("select username, password, enabled from user where username=?")
				.authoritiesByUsernameQuery("select username, role from user where username=?");
	}
	
//	@Bean
//	public PasswordEncoder passwordEncoder(){
//		PasswordEncoder encoder = new BCryptPasswordEncoder();
//		return encoder;
//	}


}