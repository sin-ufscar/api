package br.ufscar.sin.api.autenticacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
public class LoginController {


    @Autowired
    private ApiDAOAuthenticationProvider apiDAOAuthenticationProvider;

    @Autowired
    private ApiUserDetailService apiUserDetailService;


    @GetMapping("/login")
    public HashMap<String, String> autenticar(HttpServletResponse response) {
        System.out.println("Autenticando");

        UserDetails userDetails = apiUserDetailService.loadUserByUsername("Bruno");


        HashMap<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", "sucesso");
        response.setStatus(HttpStatus.CONFLICT.value());
//        apiDAOAuthenticationProvider.
//        Authentication authentication = new UsernamePasswordAuthenticationToken("Bruno")
//        apiDAOAuthenticationProvider.authenticate()
        return resposta;

    }


}
