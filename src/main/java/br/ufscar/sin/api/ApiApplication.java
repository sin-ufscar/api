package br.ufscar.sin.api;

import br.ufscar.sin.api.autenticacao.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

@ComponentScan(basePackages = "br.ufscar.sin.api")
@SpringBootApplication
public class ApiApplication {


	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ApiApplication.class, args);
		Usuario usuario = criarUsuarioTeste(context);
		List<Permissao> permissoes = criarPermissoes(context);
		atribuirPermissoes(context, usuario, permissoes);

	}

	private static void atribuirPermissoes(ConfigurableApplicationContext context, Usuario usuario, List<Permissao> permissoes) {
		UsuarioPermissaoRepository usuarioPermissaoRepository = context.getBean(UsuarioPermissaoRepository.class);

		permissoes.stream().forEach(permissao -> {

			usuarioPermissaoRepository.save(new UsuarioPermissao(usuario, (Permissao)permissao));
		});
	}

	private static List<Permissao> criarPermissoes(ConfigurableApplicationContext context) {
		PermissaoRepository permissaoRepository = context.getBean(PermissaoRepository.class);
//		Permissao permissao =

		List<Permissao> permissoes = new ArrayList<Permissao>();
		permissoes.add(new Permissao("ROLE_USUARIO", "Permissão para acessar dados de usuários"));
		permissoes.add(new Permissao("ROLE_DASHBOARD", "Permissão para acessar a dashboard"));


		return permissaoRepository.saveAll(permissoes);
	}

	/**
	 * Cria o usuário principal da aplicação para testes
	 * @param context Contexto da aplicação utilizado para recuperar os beans necessários
	 */
	private static Usuario criarUsuarioTeste(ConfigurableApplicationContext context) {
		String password = "1234";
		ApiDAOAuthenticationProvider apiDAOAuthenticationProvider = context.getBean(ApiDAOAuthenticationProvider.class);
		String encodedPassword = apiDAOAuthenticationProvider.getPasswordEncoder().encode(password);
		String username = "Bruno";
		Usuario usuario = null;
		//new Usuario("Bruno", encodedPassword);
		UsuarioRepository usuarioRepository = context.getBean(UsuarioRepository.class);

		try {
			usuario = usuarioRepository.findByUsername(username);
			if (usuario==null) {
				usuario = new Usuario(username, encodedPassword);
				usuarioRepository.save(usuario);
			}
		}
		catch (ConstraintViolationException e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getClass());
		}
		return usuario;
	}
}
