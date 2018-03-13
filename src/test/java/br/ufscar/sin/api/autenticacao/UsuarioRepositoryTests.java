package br.ufscar.sin.api.autenticacao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@AutoConfigureTestEntityManager
/**
 * Testes referentes ao UsuarioRepository
 */
public class UsuarioRepositoryTests {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Casos de teste

    @Test
    @DisplayName(value = "Quando busca um usuário existente na base pelo username, retorna o usuário cadastrado")
    public void whenFindByUsername_thenReturnUser() {
        // given
        Usuario usuarioBruno = new Usuario("BrunoPPP", "1234");
        entityManager.persist(usuarioBruno);
        entityManager.flush();

        // when
        Usuario encontrado = usuarioRepository.findByUsername(usuarioBruno.getUsername());

        // then
        assertThat(encontrado.getUsername())
                .isEqualTo(encontrado.getUsername());
    }

}
