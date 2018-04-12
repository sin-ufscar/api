package br.ufscar.sin.api.autenticacao;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Filtro que intercepta o login de modo a verificar se o usuário tem ou não acesso
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    protected JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        Usuario credentials = new ObjectMapper()
                .readValue(request.getInputStream(), Usuario.class);

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    /**
     * Trata autenticação com sucesso, retornando os dados de acesso no formato esperado
     * @param request Requisição do usuário
     * @param response Resposta que será gerada
     * @param filterChain Cadeia de filtros a serem aplicados
     * @param auth Autenticação válida
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            Authentication auth) throws IOException, ServletException {

        String JWT = TokenAuthenticationService.addAuthentication(response, auth.getName(), auth.getAuthorities());
        response.setContentType("application/json");
        List<String> permissoes = (List<String>)auth.getAuthorities().stream().map(authority -> {
            return authority.getAuthority();
        }).collect(Collectors.toList());
        HashMap<String, Object> resposta = new HashMap();
        resposta.put("roles", permissoes);
        resposta.put("username", auth.getName());
        resposta.put("token_type", "Bearer");
        resposta.put("access_token", JWT);

        response.getWriter().write(new ObjectMapper().writeValueAsString(resposta));
    }

    /**
     * Trata o caso de autenticação que falhou
     * @param request Requisição do usuário
     * @param response Resposta que será gerada
     * @param failed Autenticação que falhou
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {

        HashMap<String, Object> resposta = new HashMap();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        resposta.put("mensagem", "Usuário e/ou senha incorretos");

        response.getWriter().write(new ObjectMapper().writeValueAsString(resposta));

    }



}