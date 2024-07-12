package com.grupogbs.bot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupogbs.bot.entities.dtos.delsoft.OrdemServicoDTO;
import com.grupogbs.bot.entities.dtos.sms.EnvioSmsDTO;
import com.grupogbs.bot.entities.dtos.sms.RespostaSmsDTO;
import com.grupogbs.bot.entities.enums.Servico;

@Service
public class SmsService {

	
	// Criando o Rest Template.
	@Autowired
	private RestTemplate restTemplate;

	// Verificar para mensagens já enviadas.
	@Autowired
	private MensagemService mensagemService;
	
	// Variáveis fixas
	private final String sender = "sender";
	private final String templateId = "template-id";
	
	public HttpStatusCode enviarMensagemAtualizacaoOS(OrdemServicoDTO os) throws JsonProcessingException {
		
		// Verificando se a mensagem já não foi enviada.
		if(mensagemService.verificarMensagemEnviada(os)) return HttpStatusCode.valueOf(500);
		
		// Criando os headers.
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("accept", "application/json");
		headers.add("X-API-TOKEN", "token");
		
		// Criando o body
		
		EnvioSmsDTO body = new EnvioSmsDTO(os, sender, templateId);
		String jsonBody = new ObjectMapper().writeValueAsString(body);
		
		// Passando os headers.
		HttpEntity<String> entity = new HttpEntity<String>(jsonBody,headers);
		
		// Base URL.
		String baseUrl = "https://api.zenvia.com/v2/channels/sms/messages";
		
		try {
			
			// Fazendo o request e aguardando a resposta.
			ResponseEntity<RespostaSmsDTO> response = restTemplate
					.exchange(
							(baseUrl),
							HttpMethod.POST,
							entity, 
							RespostaSmsDTO.class
							);
			
			
			// Transformando os dados e devolvendo os resultados.
			RespostaSmsDTO r = response.getBody();
			System.err.println("\n\n Resposta da API : \n\n");
			System.out.println(r);
			
			if(response.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
				mensagemService.registrarEnvioMensagem(os, Servico.SMS);
			}
			
			return response.getStatusCode();
			
		} catch (HttpClientErrorException | HttpServerErrorException | ResourceAccessException e) {
			
			System.out.println("Erro ao enviar mensagem para o número = " + os.getCelularRequerente());
			return HttpStatusCode.valueOf(500);
			
		}
		
	}
	
}
