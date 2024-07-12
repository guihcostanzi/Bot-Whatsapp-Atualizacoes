package com.grupogbs.bot.components;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grupogbs.bot.entities.dtos.delsoft.OrdemServicoDTO;
import com.grupogbs.bot.services.EmailService;
import com.grupogbs.bot.services.OrdemServicoService;
import com.grupogbs.bot.services.SmsService;
import com.grupogbs.bot.services.WhatsappService;

import jakarta.transaction.Transactional;

@Component
public class TarefasAgendadas {
	
	@Autowired
	private OrdemServicoService ordemServicoService;
	
	@Autowired
	private WhatsappService whatsappService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired 
	private SmsService smsService;
	
	@Transactional
	@Scheduled(fixedDelay = 3600000, initialDelay = 10000)
	public void buscarAtualizacoesOrdensServico() throws JsonProcessingException {
		
		System.out.println("Buscando dados. - " + LocalDateTime.now());
		
		List<OrdemServicoDTO> l = ordemServicoService.buscarDados();
		
		System.out.println("Número de OS encontrada = " + l.size());
		
		if(l.size() != 0) {
			for(OrdemServicoDTO os : l) {
				if(os.getCelularRequerente() != null) {
					
					HttpStatusCode statusRequest = whatsappService.enviarMensagemAtualizacaoOS(os);
					if(statusRequest.equals(HttpStatusCode.valueOf(200))) {
						System.out.println("Enviado whatsapp para OS : " + os.getNumeroOS());
					}
					else {
						System.out.println("Falha ao enviar whatsapp para OS : " + os.getNumeroOS());
						
						statusRequest = smsService.enviarMensagemAtualizacaoOS(os);
						System.out.println("Enviado sms para OS : " + os.getNumeroOS());
					}
					
				}
				else if(os.getEmailRequerente() != null) {
					emailService.enviarMensagemAtualizacaoOS(os);
					System.out.println("Enviado e-mail para OS : " + os.getNumeroOS());
				}
			}
		}
		
		
	}
	
}
