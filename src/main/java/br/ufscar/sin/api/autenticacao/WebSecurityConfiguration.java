package br.ufscar.sin.api.autenticacao;


import br.ufscar.sin.api.autenticacao.JWTAuthenticationFilter;
import br.ufscar.sin.api.autenticacao.JWTLoginFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
/**
 * Classe utilizada para configurar o acesso aos end points da aplicação
 */
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DaoAuthenticationProvider authProvider;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/usuario").hasRole("USUARIO")
                .anyRequest().authenticated()
                .and()
                // filtra requisições de login
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)


                .addFilterBefore(new JWTAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class);
//                .antMatchers("/dashboard").hasRole("DASHBOARD")
        //        and()
//        .exceptionHandling()
//                .accessDeniedPage("/acesso-negado")
////                .formLogin()
////                .usernameParameter("login")
////                .passwordParameter("senha")
//                .failureUrl("/login?error=1")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout")
//                .invalidateHttpSession(true)
//                .permitAll();
    }
}
