package com.todaysoft.ghealth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.todaysoft.ghealth.support.AccountAuthenticationProvider;

@EnableWebSecurity
public class SpringSecurityContext extends WebSecurityConfigurerAdapter
{
    @Autowired
    private AccessDecisionManager accessDecisionManager;
    
    @Autowired
    private AccountAuthenticationProvider authenticationProvider;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
        throws Exception
    {
        auth.authenticationProvider(authenticationProvider);
    }
    
    @Override
    public void configure(WebSecurity web)
        throws Exception
    {
        web.ignoring().antMatchers("/static/**");
        web.ignoring().antMatchers("/login.jsp");
    }
    
    @Override
    protected void configure(HttpSecurity http)
        throws Exception
    {
        http.authorizeRequests().antMatchers("/").authenticated();
        http.authorizeRequests().antMatchers("/**/*.jsp").authenticated().accessDecisionManager(accessDecisionManager);
        
        http.csrf().disable();
        
        http.formLogin().loginPage("/login.jsp");
        http.formLogin().loginProcessingUrl("/login_process.jsp");
        http.formLogin().defaultSuccessUrl("/", true);
        
        http.logout().logoutUrl("/logout.jsp");
        http.logout().logoutSuccessUrl("/login.jsp");
        
        http.headers().frameOptions().sameOrigin();
        http.headers().cacheControl().disable();
    }
}
