package br.ufscar.sin.api.config;

import br.ufscar.sin.api.autenticacao.ApiUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ApiDAOAuthenticationProvider extends DaoAuthenticationProvider{

    @Autowired
    private ApiUserDetailService userDetailsService;

    public void setUserDetailsService(ApiUserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
        super.setUserDetailsService(this.userDetailsService);
    }

    public PasswordEncoder getPasswordEncoder() {
        return super.getPasswordEncoder();
    }
}
