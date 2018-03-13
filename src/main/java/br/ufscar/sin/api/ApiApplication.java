package br.ufscar.sin.api;

import br.ufscar.sin.api.autenticacao.Usuario;
import br.ufscar.sin.api.autenticacao.UsuarioRepository;
import br.ufscar.sin.api.autenticacao.ApiDAOAuthenticationProvider;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "br.ufscar.sin.api")
@SpringBootApplication
public class ApiApplication {


	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ApiApplication.class, args);
		criarUsuarioTeste(context);
	}

	/**
	 * Cria o usuário principal da aplicação para testes
	 * @param context Contexto da aplicação utilizado para recuperar os beans necessários
	 */
	private static void criarUsuarioTeste(ConfigurableApplicationContext context) {
		String password = "1234";
		ApiDAOAuthenticationProvider apiDAOAuthenticationProvider = context.getBean(ApiDAOAuthenticationProvider.class);
		String encodedPassword = apiDAOAuthenticationProvider.getPasswordEncoder().encode(password);
		Usuario usuario = new Usuario("Bruno", encodedPassword);
		UsuarioRepository usuarioRepository = context.getBean(UsuarioRepository.class);

		try {
			usuarioRepository.save(usuario);
		}
		catch (ConstraintViolationException e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getClass());
		}
	}
}
