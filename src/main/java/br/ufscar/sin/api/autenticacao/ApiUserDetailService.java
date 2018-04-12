package br.ufscar.sin.api.autenticacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//TODO: Permitir que o usuário seja buscado em bases diferentes
/**
 * Classe responsável por buscar o usuário por memio do seu username
 */
@Service
public class ApiUserDetailService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioPermissaoRepository usuarioPermissaoRepository;

    ApiUserDetailService() {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }
        return new ApiUserPrincipal(usuario, usuarioPermissaoRepository);
    }
}


