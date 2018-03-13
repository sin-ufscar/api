package br.ufscar.sin.api.autenticacao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);

    default Usuario findByUsernameOrCreate(String username) {
        return null;
    }

}
