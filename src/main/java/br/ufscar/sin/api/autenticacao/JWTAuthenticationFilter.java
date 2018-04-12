package br.ufscar.sin.api.autenticacao;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filtro que intercepta todas as chamadas da api, exceto a chamada do login
 */
public class JWTAuthenticationFilter extends GenericFilterBean {

    /**
     * Seta a autenticação com base no token enviado
     * @param request Requisição
     * @param response Resposta
     * @param filterChain Cadeia de filtros
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        Authentication authentication = TokenAuthenticationService
                .getAuthentication((HttpServletRequest) request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

}