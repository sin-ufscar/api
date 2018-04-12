package br.ufscar.sin.api.autenticacao;


import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

public class TokenAuthenticationService {

    // EXPIRATION_TIME = 10 dias
    static final long EXPIRATION_TIME = 860_000_000;
    static final String SECRET = "MySecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";




    static String addAuthentication(HttpServletResponse response, String username, Collection<? extends GrantedAuthority> authorities) {
        String permissoes = authorities.stream().map(authority -> {
            return authority.getAuthority();
        }).collect(Collectors.joining(","));

        HashMap<String, Object> claimAuthorities = new HashMap<>();
        claimAuthorities.put("roles", permissoes);
        String JWT = Jwts.builder()
                .setSubject(username)
                .addClaims(claimAuthorities)
//                .setPayload(permissoes)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))

                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();


        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
        return JWT;
    }

    static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            // faz parse do token
            Claims body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .require("hasMotorcycle", true)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))

                    .getBody();


            String user = body
                    .getSubject();

            String permissoes = body.get("roles", String.class);
            List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(permissoes);
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, authorityList);
            }
        }
        return null;
    }

}
