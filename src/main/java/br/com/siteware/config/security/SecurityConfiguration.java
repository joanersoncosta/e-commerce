package br.com.siteware.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.siteware.config.security.enuns.AuthUrlMapping;
import br.com.siteware.config.security.service.AutenticacaoSecurityService;
import br.com.siteware.config.security.service.TokenService;
import br.com.siteware.credencial.application.service.CredencialService;
import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	private final TokenService tokenService;
	private final CredencialService credencialService;
	private final AutenticacaoSecurityService autenticacaoSecurityService;

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoSecurityService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	protected void configure (HttpSecurity http) throws Exception {
	        http.csrf(csrf -> csrf.disable())
	                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	                .authorizeHttpRequests(
	                        authorize -> authorize
//                            		.antMatchers(AuthUrlMapping.ADMIN.getUrls()).hasAnyAuthority(RoleAuth.ROLE_ADMIN.name())
	                                .antMatchers(AuthUrlMapping.PERMIT_ALL.getUrls()).permitAll()
	                                .anyRequest().authenticated()
	                )/*.exceptionHandling(exceptionHandling -> exceptionHandling
	                        .authenticationEntryPoint((request, response, authException) ->
	                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
	                        )
	                        .accessDeniedHandler((request, response, accessDeniedException) ->
	                                response.setStatus(HttpServletResponse.SC_FORBIDDEN)
	                        )
	                )*/.addFilterBefore(new FiltroToken(tokenService, credencialService), UsernamePasswordAuthenticationFilter.class);
	    }
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/**.html", "/v3/api-docs/**", "/webjars/**", "/configuration/**", "/swagger-ui/**",
				"/swagger-ui.html");
	}

}
