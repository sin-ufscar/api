package br.ufscar.sin.api.autenticacao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioPermissaoRepository extends JpaRepository<UsuarioPermissao, Long> {

    List<UsuarioPermissao> findAllByUsuario(Usuario usuario);
}
