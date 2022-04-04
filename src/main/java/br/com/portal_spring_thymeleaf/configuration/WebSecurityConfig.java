package br.com.portal_spring_thymeleaf.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.portal_spring_thymeleaf.configuration.OAuth2.CustomOAuth2UserService;
import br.com.portal_spring_thymeleaf.configuration.OAuth2.OAuthLoginSuccessHandler;
import br.com.portal_spring_thymeleaf.services.UsuarioService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioService userService;
	
	@Autowired
	private OAuthLoginSuccessHandler oauthLoginSuccessHandler;
	
	@Autowired
	private CustomOAuth2UserService oauthUserService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/bootstrap/**",
						"/usuario/cadastro**",
						"/usuario/novo",
						"/login**",
						"/oauth2/**",
						"/js/**", 
						"/css/**", 
						"/img/**").permitAll().anyRequest().authenticated()
						.and()
						.formLogin().loginPage("/login").permitAll()
						.and()
						.logout().invalidateHttpSession(true).clearAuthentication(true)
							.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
							.logoutSuccessUrl("/login?logout").permitAll()
						.and()
						.oauth2Login().loginPage("/login")
							.userInfoEndpoint().userService(oauthUserService)
						.and().successHandler(oauthLoginSuccessHandler).defaultSuccessUrl("/home");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

}
