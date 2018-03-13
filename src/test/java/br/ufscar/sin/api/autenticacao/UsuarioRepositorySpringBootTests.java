package br.ufscar.sin.api.autenticacao;

import br.ufscar.sin.api.ApiApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest (classes = {ApiApplication.class})
@AutoConfigureTestEntityManager
/**
 * Testes referentes ao UsuarioRepository
 */
@Transactional
public class UsuarioRepositorySpringBootTests {


    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Casos de teste

    @Test
    @DisplayName(value = "Quando busca um usuário existente na base pelo username, retorna o usuário cadastrado")
    public void whenFindByUsername_thenReturnUser() {
        // given
        Usuario usuarioBruno = new Usuario("BrunoXXXX", "1234");
        testEntityManager.persist(usuarioBruno);
        testEntityManager.flush();

        // when
        Usuario encontrado = usuarioRepository.findByUsername(usuarioBruno.getUsername());

        // then
        assertThat(encontrado.getUsername())
                .isEqualTo(encontrado.getUsername());

        //cleanup
        usuarioRepository.delete(usuarioBruno);
    }

}
