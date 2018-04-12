package br.ufscar.sin.api.autenticacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ApiUserPrincipal implements UserDetails {
    private Usuario usuario;

    public ApiUserPrincipal(Usuario usuario, UsuarioPermissaoRepository usuarioPermissaoRepository) {
        this.usuario = usuario;
        this.usuarioPermissaoRepository = usuarioPermissaoRepository;
    }


    @Autowired
    UsuarioPermissaoRepository usuarioPermissaoRepository;

    //TODO: Criar método para obter permissões
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<Permissao> permissoes = usuarioPermissaoRepository.findAllByUsuario(this.usuario).stream().
                collect(ArrayList<Permissao>::new, (listaPermissoes, usuarioPermissao) -> {
                    listaPermissoes.add(usuarioPermissao.permissao);
                        }, (c,d) -> {}
                );

        return permissoes;
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
