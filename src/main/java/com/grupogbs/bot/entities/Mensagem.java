package com.grupogbs.bot.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.grupogbs.bot.entities.dtos.delsoft.OrdemServicoDTO;
import com.grupogbs.bot.entities.enums.Servico;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "T_MENSAGEM")
public class Mensagem {

	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_MENSAGEM")
	@Id
	private Long id;
	
	@Column(name = "SERVICO")
	@Enumerated(EnumType.STRING)
	private Servico servico;
	
	@Column(name = "DESTINATARIO")
	private String destinatario;
	
	@Column(name = "NUMERO_OS")
	private Long numeroOS;
	
	@Column(name = "DATA_HORA_ENVIO")
	@CreationTimestamp
	private LocalDateTime dataHoraEnvio;
	
	public Mensagem(OrdemServicoDTO ordemServico, Servico servico) {
		this.servico = servico;
		if(servico.equals(Servico.EMAIL)) this.destinatario = ordemServico.getEmailRequerente();
		else this.destinatario = ordemServico.getCelularRequerente();
		this.numeroOS = ordemServico.getNumeroOS();
	}
	
}
