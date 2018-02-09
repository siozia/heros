package com.example.hero;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@RestController
@RequestMapping("/api")
public class Application {
	@Autowired
	private RestTemplate restTemplate;
	

	@Bean
	public ClientHttpRequestInterceptor clientHttpRequestInterceptor() {
		return (HttpRequest arg0, byte[] arg1, ClientHttpRequestExecution arg2) -> {
			//Added to avoid 403 error page. I used this header because it works by browser and curl invocation
			// so i guessed i needed to emulate one of the two and i were right :)
			arg0.getHeaders().add("User-Agent", "curl/7.47.0");
			//These staff is here to debug before i found User-Agent trick :)
			log.info("Headers {}",arg0.getHeaders());
			log.info("Method {}",arg0.getMethod());
			return arg2.execute(arg0, arg1);
		};
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder, ClientHttpRequestInterceptor clientHttpRequestInterceptor) {
		return restTemplateBuilder.additionalInterceptors(clientHttpRequestInterceptor).build();
	}

	@Bean
	public CommandLineRunner commandLineRunner(RestTemplate restTemplate) {
		//Used only to test everything is working
		return (args) -> {			
			//fetching all heros list
			Page res = restTemplate.getForObject("https://overwatch-api.net/api/v1/hero", Page.class);
			log.info("#### Hero page {}",res);
			// fetching single hero infos
			Hero hero = restTemplate.getForObject("https://overwatch-api.net/api/v1/hero/2", Hero.class);
			log.info("#### Hero id 2 {}",hero);
			// fetching all abilities list
			Page res2 = restTemplate.getForObject("https://overwatch-api.net/api/v1/ability", Page.class);
			log.info("#### Ability page {}", res2);
			// Fetching an ability
			Ability ability = restTemplate.getForObject("https://overwatch-api.net/api/v1/ability/5", Ability.class);
			log.info("#### Ability id 5 {}", ability);
		};
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("heros")
	public List<Hero> getHerosList() {
		//Fetching heros' page from overwatch api
		Page herosPage = restTemplate.getForObject("https://overwatch-api.net/api/v1/hero", Page.class);
		// returning only requested data
		return (List<Hero>) herosPage.getData();
	}
	
	@GetMapping("heros/{id}")
	public Hero getHeroById(@PathVariable("id") Integer id) {
		//Returning requested hero
		return restTemplate.getForObject(String.format("https://overwatch-api.net/api/v1/hero/%d", id), Hero.class);
	}
	
	@GetMapping("heros/{id}/abilities")
	public List<Ability> getHerosAbilitiesById(@PathVariable("id") Integer id) {
		//Fetching hero by id
		Hero hero= restTemplate.getForObject(String.format("https://overwatch-api.net/api/v1/hero/%d", id), Hero.class);
		//Avoiding null pointer exception. Application without data check validity
		if(hero!=null) return hero.getAbilities();
		else return null;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("abilities/")
	public List<Ability> getHerosAbility() {
		//Fetching abilities from overwatch api
		Page abilitiesPage = restTemplate.getForObject("https://overwatch-api.net/api/v1/ability", Page.class);
		// returning only requested data
		return (List<Ability>) abilitiesPage.getData();
	}
	
	
	@GetMapping("abilities/{id}")
	public Ability getAbilityById(@PathVariable("id") Integer id) {
		//Returning ability data fetched by id from overwatch api
		return restTemplate.getForObject(String.format("https://overwatch-api.net/api/v1/ability/%d", id) , Ability.class);
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
