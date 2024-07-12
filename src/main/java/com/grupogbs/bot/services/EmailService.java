package com.grupogbs.bot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.grupogbs.bot.entities.dtos.delsoft.OrdemServicoDTO;
import com.grupogbs.bot.entities.enums.Servico;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private MensagemService mensagemService;
	
	@Async
	public void enviarMensagemAtualizacaoOS(OrdemServicoDTO os) {
		
		// Verificando se a mensagem já não foi enviada.
		if(mensagemService.verificarMensagemEnviada(os)) return;
		
		StringBuilder corpoEmail = new StringBuilder();
		
		corpoEmail.append("Olá, Colaborador.\n\n");
		corpoEmail.append("O status da sua OS foi atualizado.\n\n");
		corpoEmail.append("OS " + os.getNumeroOS() + "\n\n");
		corpoEmail.append("Veículo: " + os.getPrefixoVeiculo() + "\n");
		corpoEmail.append("Abertura: " + os.getAberturaOS() + "\n");
		corpoEmail.append("Encerramento: " + os.getEncerramentoOS() + "\n");
		corpoEmail.append("Status: " + os.getStatus() + "\n\n");
		corpoEmail.append("Itens Relatados :\n\n");
		corpoEmail.append(os.getItensTexto());
		corpoEmail.append("Para mais informações, acesse o sistema e verifique a ordem de serviço aberta.\n\n");
		corpoEmail.append("Essa é uma mensagem automática, favor não responder.");
		
		try {
  
			SimpleMailMessage email = new SimpleMailMessage();
			
			email.setFrom("exemplo@email.com");
			email.setTo(os.getEmailRequerente());
			email.setSubject("ATUALIZAÇÃO OS#" + os.getNumeroOS() + " - GRUPO GBS");
			email.setText(corpoEmail.toString());
			
			javaMailSender.send(email);
			
			
			mensagemService.registrarEnvioMensagem(os, Servico.EMAIL);
			
		}catch (MailException e) {
			System.err.println("Erro ao enviar e-mail para o endereço = " + os.getEmailRequerente());
		}
		
	}
	
}
