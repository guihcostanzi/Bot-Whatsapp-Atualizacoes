package com.grupogbs.bot.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.grupogbs.bot.entities.dtos.senior.RespostaConsultaColabotadorDTO;

@Service
public class ConsultaColaboradorService {
	
	// Criando o Rest Template.

	private RestTemplate restTemplate;
	
	// Buscando o Rest Template do Configuration.
	
	@Autowired
	public ConsultaColaboradorService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public RespostaConsultaColabotadorDTO consultar(String matricula, Integer empresa) {
		
		// Criando os headers.
		HttpHeaders headers = new HttpHeaders();
		headers.add("accept", "application/json");
		
		// Passando os headers.
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		// Base URL.
		String baseUrl = "http://localhost:8082/seniorConsultaAPI/colaborador/consultar?matricula=" + matricula + "&empresa="
				+ empresa;
		
		try {
			
			// Fazendo o request e aguardando a resposta.
			ResponseEntity<RespostaConsultaColabotadorDTO> response = restTemplate
					.exchange(
							(baseUrl),
							HttpMethod.GET,
							entity, 
							RespostaConsultaColabotadorDTO.class
							);
			
			
			// Transformando os dados e devolvendo os resultados.
			RespostaConsultaColabotadorDTO r = response.getBody();
			
			return r;
			
		} catch (HttpClientErrorException | HttpServerErrorException | ResourceAccessException e) {
			
			RespostaConsultaColabotadorDTO r = new RespostaConsultaColabotadorDTO();
			
			return r;
			
		}
		
	}

}
