package com.grupogbs.bot.entities.dtos.delsoft;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "numeroOS")
public class ConsultaOrdemServicoDTO {
	
	private Integer empresa;
	private Long numeroOS;
	private String prefixoVeiculo;
	private LocalDateTime aberturaOS;
	private LocalDateTime encerramentoOS;
	private String matriculaUsuario;
	private String motivoAbertura;
	private String status;
	private String resolucao;
	private String observacaoResolucao;
	private String usuarioExecucao;
	private String nomeUsuarioExecucao;
	private String celularRequerente;
	private String emailRequerente;
	
	@Override
	public String toString() {
		return "OrdemServicoDTO \n"
				+ "[ \n"
				+ " empresa= " + empresa + "\n"
				+ " numeroOS= " + numeroOS + "\n"
				+ " prefixoVeiculo= " + prefixoVeiculo + "\n"
				+ " aberturaOS= " + aberturaOS + "\n"
				+ " encerramentoOS= " + encerramentoOS + "\n"
				+ " matriculaUsuario= " + matriculaUsuario + "\n"
				+ " motivoAbertura= " + motivoAbertura + "\n"
				+ " status= " + status + "\n"
				+ " resolucao= " + resolucao + "\n" 
				+ " observacaoResolucao= " + observacaoResolucao + "\n" 
				+ " usuarioExecucao= " + usuarioExecucao + "\n" 
				+ " nomeUsuarioExecucao= " + nomeUsuarioExecucao + "\n" 
				+ " celularRequerente= " + celularRequerente + "\n" 
				+ " emailRequerente= " + emailRequerente + "\n" 
				+ "]";
	}
	
	

}