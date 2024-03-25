// Importe les packages nécessaires, y compris ModelMapper pour le mappage d'objets et les annotations Spring Boot.
package net.m2i.todo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// L'annotation @SpringBootApplication est une annotation de commodité qui ajoute:
// @Configuration : Marque la classe comme source de définitions de bean pour le contexte d'application.
// @EnableAutoConfiguration : Indique à Spring Boot de commencer à ajouter des beans en fonction des paramètres du chemin de classe, d'autres beans, et des diverses propriétés de configuration.
// @ComponentScan : Indique à Spring de scanner les autres composants, configurations, et services dans le package courant, permettant la détection automatique des beans.
@SpringBootApplication
public class TodoManagementApplication {

	// Déclare un bean ModelMapper à l'aide de l'annotation @Bean. Ceci est fait pour le rendre disponible dans le contexte de l'application Spring,
	// permettant ainsi l'injection automatique là où il est nécessaire.
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	// Le point d'entrée principal de l'application Spring Boot.
	public static void main(String[] args) {
		// Lance l'application Spring Boot, ce qui démarre automatiquement le serveur embarqué (par défaut Tomcat),
		// initialise Spring, effectue le balayage de composants, et plus encore.
		SpringApplication.run(TodoManagementApplication.class, args);
	}
}
