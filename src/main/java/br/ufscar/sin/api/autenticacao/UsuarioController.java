package br.ufscar.sin.api.autenticacao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
public class UsuarioController {

    @GetMapping("/usuario")
    public HashMap<String, String> autenticar(HttpServletResponse response) {
        System.out.println("Autenticando");


        HashMap<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", "sucesso");
        response.setStatus(HttpStatus.CONFLICT.value());
//        apiDAOAuthenticationProvider.
//        Authentication authentication = new UsernamePasswordAuthenticationToken("Bruno")
//        apiDAOAuthenticationProvider.authenticate()
        return resposta;

    }
}
