package com.javee.attendance.config;

import com.javee.attendance.security.JWTAuthenticationExceptionEntryPoint;
import com.javee.attendance.security.JWTAuthenticationProvider;
import com.javee.attendance.security.JWTAuthenticationTokenFilter;
import com.javee.attendance.security.JWTSuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.util.Collections;

@EnableGlobalMethodSecurity( prePostEnabled = true )
@EnableWebSecurity
@Configuration
public class JWTSecurityConfig extends WebSecurityConfigurerAdapter
{
	private static final Logger LOGGER = LoggerFactory.getLogger( JWTSecurityConfig.class );

	@Autowired
	private JWTAuthenticationProvider authenticationProvider;

	@Autowired
	private JWTAuthenticationExceptionEntryPoint exceptionEntryPoint;

	@Bean
	public AuthenticationManager authenticationManager()
	{
		return new ProviderManager( Collections.singletonList( authenticationProvider ) );
	}

	@Bean
	public JWTAuthenticationTokenFilter authenticationTokenFilter()
	{

		JWTAuthenticationTokenFilter filter = new JWTAuthenticationTokenFilter();

		filter.setAuthenticationManager( authenticationManager() );

		filter.setAuthenticationSuccessHandler( new JWTSuccessHandler() );

		return filter;
	}

	@Override
	protected void configure( HttpSecurity http ) throws Exception
	{
		http.csrf().disable().authorizeRequests()
				.antMatchers( HttpMethod.OPTIONS, "**" ).permitAll()
				.antMatchers( "**/api/**" ).authenticated()
				.and()
				.exceptionHandling().authenticationEntryPoint( exceptionEntryPoint )
				.and()
				.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS );
		http.addFilterBefore( authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class );
		http.headers().cacheControl();
	}
}

