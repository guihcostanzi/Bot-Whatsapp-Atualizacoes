package com.grupogbs.bot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupogbs.bot.entities.Mensagem;
import com.grupogbs.bot.entities.dtos.delsoft.OrdemServicoDTO;
import com.grupogbs.bot.entities.enums.Servico;
import com.grupogbs.bot.repository.MensagemRepository;

@Service
public class MensagemService {

	@Autowired
	private MensagemRepository mensagemRepository;
	
	public void registrarEnvioMensagem(OrdemServicoDTO ordemServico, Servico servico) {
		
		Mensagem msg = new Mensagem(ordemServico, servico);
		mensagemRepository.save(msg);
		
	}
	
	public Boolean verificarMensagemEnviada(OrdemServicoDTO ordemServico) {
		
		Mensagem msgCelular = null;
		if(ordemServico.getCelularRequerente() != null && !ordemServico.getCelularRequerente().isBlank()) msgCelular = mensagemRepository.findByNumeroOSAndDestinatario(ordemServico.getNumeroOS(), ordemServico.getCelularRequerente()).orElse(null);
		
		Mensagem msgEmail = null; 
		if(ordemServico.getEmailRequerente() != null && !ordemServico.getEmailRequerente().isBlank()) msgEmail = mensagemRepository.findByNumeroOSAndDestinatario(ordemServico.getNumeroOS(), ordemServico.getEmailRequerente()).orElse(null);
	
		// Não enviada
		if(msgCelular == null && msgEmail == null) return false;
		
		// Enviada
		else return true;
	}
	
}
