package br.ufscar.sin.api.autenticacao;

import br.ufscar.sin.api.ApiApplication;
import br.ufscar.sin.api.config.database.ProducaoPostgresDatabaseConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
//@TestPropertySource(
//        locations = {"classpath:test-application.properties","classpath:test-hibernate.properties"})
@SpringBootTest (classes = {ApiApplication.class, ProducaoPostgresDatabaseConfiguration.class})
//@ContextConfiguration(classes = {ProducaoPostgresDatabaseConfiguration.class})
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@AutoConfigureTestEntityManager
/**
 * Testes referentes ao UsuarioRepository
 */
public class UsuarioRepositorySpringBootTests {


    @Autowired
    private EntityManager integracaoPostgresEntityManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Casos de teste

    @Test
    @DisplayName(value = "Quando busca um usuário existente na base pelo username, retorna o usuário cadastrado")
    public void whenFindByUsername_thenReturnUser() {
        // given
        Usuario usuarioBruno = new Usuario("BrunoXXXX", "1234");
//        integracaoPostgresEntityManager.persist(usuarioBruno);
//        integracaoPostgresEntityManager.flush();
        usuarioRepository.save(usuarioBruno);

        // when
        Usuario encontrado = usuarioRepository.findByUsername(usuarioBruno.getUsername());

        // then
        assertThat(encontrado.getUsername())
                .isEqualTo(encontrado.getUsername());

        //cleanup
//        usuarioRepository.delete(usuarioBruno);
    }

}
