package com.medra.springboot.reactor.app;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.medra.springboot.reactor.app.models.Usuario;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(SpringBootReactorApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	/**
	 * .doOnNext(System.out::println); 
	 * llega el elemento por parametro como una funcion de flecha y lo manda a println
	 * Tambien se puede hacer con funcion de flecha:
	 * 
	 * Flux.just("Andres", "Gustavo", "Oscar", "Diego")
		.doOnNext(elemento -> System.out.println(elemento)); 
	 * 
	 * 
	 */
	@Override
	public void run(String... args) throws Exception {
		
		List<String> listUsuarios = new ArrayList<>();	
		listUsuarios.add("Andres Brusutti");
		listUsuarios.add("Gustavo Espinosa");
		listUsuarios.add("Pedro Gomez");
		listUsuarios.add("Oscar Perez");
		listUsuarios.add("Diego Alarcon");
		listUsuarios.add("Bruce Lee");
		listUsuarios.add("Bruce Willy");
		
		
		Flux<String> nombres = Flux.fromIterable(listUsuarios); 
				//Flux.just("Andres Brusutti", "Gustavo Espinosa", "Pedro Gomez","Oscar Perez", 
				//			"Diego Alarcon", "Bruce Lee", "Bruce Willy");
		
		Flux<Usuario> usuarios = nombres.map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()) )
					.filter(usuario ->{
						return usuario.getNombre().equalsIgnoreCase("bruce");
					})
					.doOnNext(usuario -> {
						if(usuario == null) {
							throw new RuntimeException("Nombre en blanco");
						}
						System.out.println(usuario.getNombre().concat(" ").concat(usuario.getApellido()));																					
					})
					.map(usuario -> {
						String nombre = usuario.getNombre().toLowerCase();
						usuario.setNombre(nombre);
						return usuario;
					}); 		
		
		usuarios
			.subscribe(usuario -> {
				logger.info(usuario.toString());			
			},
			error -> {
				logger.error(error.getMessage());
			},
			new Runnable() {
				
				@Override
				public void run() {
					logger.info("Finalizo la ejecucion del observable flux");
				}
			}
			
			);
		
	}
	
	public void ejemploIterable(String... args) throws Exception {
		
		List<String> listUsuarios = new ArrayList<>();	
		listUsuarios.add("Andres Brusutti");
		listUsuarios.add("Gustavo Espinosa");
		listUsuarios.add("Pedro Gomez");
		listUsuarios.add("Oscar Perez");
		listUsuarios.add("Diego Alarcon");
		listUsuarios.add("Bruce Lee");
		listUsuarios.add("Bruce Willy");
		
		
		Flux<String> nombres = Flux.fromIterable(listUsuarios); 
				//Flux.just("Andres Brusutti", "Gustavo Espinosa", "Pedro Gomez","Oscar Perez", 
				//			"Diego Alarcon", "Bruce Lee", "Bruce Willy");
		
		Flux<Usuario> usuarios = nombres.map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()) )
					.filter(usuario ->{
						return usuario.getNombre().equalsIgnoreCase("bruce");
					})
					.doOnNext(usuario -> {
						if(usuario == null) {
							throw new RuntimeException("Nombre en blanco");
						}
						System.out.println(usuario.getNombre().concat(" ").concat(usuario.getApellido()));																					
					})
					.map(usuario -> {
						String nombre = usuario.getNombre().toLowerCase();
						usuario.setNombre(nombre);
						return usuario;
					}); 		
		
		usuarios
			.subscribe(usuario -> {
				logger.info(usuario.toString());			
			},
			error -> {
				logger.error(error.getMessage());
			},
			new Runnable() {
				
				@Override
				public void run() {
					logger.info("Finalizo la ejecucion del observable flux");
				}
			}
			
			);
		
	}



}
